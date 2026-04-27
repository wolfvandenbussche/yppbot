package monitor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BotMonitor {

    private static final String CONFIG_FILE  = "bots.conf";
    private static final int    POLL_SECONDS = 2;
    private static final int    CARD_W       = 240;
    private static final int    CARD_H       = 100;
    private static final int    SCREENSHOT_W = 1280;
    private static final int    SCREENSHOT_H = 720;

    private static final Color BG           = new Color(30, 30, 30);
    private static final Color CARD_BG      = new Color(45, 45, 48);
    private static final Color TEXT_PRIMARY  = new Color(230, 230, 230);
    private static final Color TEXT_MUTED    = new Color(140, 140, 140);
    private static final Color OK            = new Color(80, 200, 100);
    private static final Color WARN          = new Color(255, 200, 0);
    private static final Color DEAD          = new Color(200, 60, 60);
    private static final Color ACCENT        = new Color(0, 120, 215);

    // -------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(() -> new BotMonitor().show());
    }

    // -------------------------------------------------------------------------

    private final List<BotClient>    bots  = new ArrayList<>();
    private final List<BotCardPanel> cards = new ArrayList<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

    private JPanel    grid;
    private JFrame    frame;

    public void show() {
        frame = new JFrame("YPP Bot Monitor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BG);
        frame.setLayout(new BorderLayout());

        frame.add(buildToolbar(), BorderLayout.NORTH);
        frame.add(buildGrid(),    BorderLayout.CENTER);

        frame.setVisible(true);

        loadConfig();
        scheduler.scheduleAtFixedRate(this::pollAll, 0, POLL_SECONDS, TimeUnit.SECONDS);
    }

    private JPanel buildToolbar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        bar.setBackground(new Color(40, 40, 42));
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(60, 60, 64)));

        JLabel title = new JLabel("YPP Bot Monitor");
        title.setForeground(TEXT_PRIMARY);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 14f));
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        left.setOpaque(false);
        left.add(title);

        JButton addBtn = styledButton("+ Add Bot", ACCENT);
        addBtn.addActionListener(e -> showAddDialog());

        JPanel bar2 = new JPanel(new BorderLayout());
        bar2.setBackground(new Color(40, 40, 42));
        bar2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(60, 60, 64)));
        bar2.add(left, BorderLayout.WEST);
        bar2.add(addBtn, BorderLayout.EAST);
        ((FlowLayout) bar.getLayout()).setAlignment(FlowLayout.RIGHT);

        return bar2;
    }

    private JScrollPane buildGrid() {
        grid = new JPanel(new WrapLayout(FlowLayout.LEFT, 12, 12));
        grid.setBackground(BG);
        grid.setBorder(new EmptyBorder(12, 12, 12, 12));

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG);
        return scroll;
    }

    // -------------------------------------------------------------------------
    // Add / remove bots

    private void showAddDialog() {
        if (!DockerManager.isAvailable()) {
            JOptionPane.showMessageDialog(frame,
                    "Docker is not running.\nPlease install Docker Desktop and start it first.",
                    "Docker required", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField nameField  = new JTextField("Account1", 18);
        JTextField userField  = new JTextField("", 18);
        JPasswordField passField = new JPasswordField("", 18);
        JComboBox<String> modeBox = new JComboBox<>(new String[]{"blacksmithing", "bilging"});

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints l = new GridBagConstraints();
        l.anchor = GridBagConstraints.WEST; l.insets = new Insets(5, 4, 5, 10);
        GridBagConstraints f = new GridBagConstraints();
        f.fill = GridBagConstraints.HORIZONTAL; f.weightx = 1; f.insets = new Insets(5, 0, 5, 4);

        l.gridx = 0; l.gridy = 0; form.add(new JLabel("Account name:"), l);
        f.gridx = 1; f.gridy = 0; form.add(nameField, f);
        l.gridy = 1; form.add(new JLabel("Steam username:"), l);
        f.gridy = 1; form.add(userField, f);
        l.gridy = 2; form.add(new JLabel("Steam password:"), l);
        f.gridy = 2; form.add(passField, f);
        l.gridy = 3; form.add(new JLabel("Game mode:"), l);
        f.gridy = 3; form.add(modeBox, f);

        int result = JOptionPane.showConfirmDialog(
                frame, form, "Add Bot", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        String name = nameField.getText().trim();
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword());
        String mode = (String) modeBox.getSelectedItem();

        if (name.isEmpty() || user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int port = nextAvailablePort();
        launchDockerBot(name, user, pass, mode, port);
    }

    private void launchDockerBot(String name, String steamUser, String steamPass, String mode, int port) {
        JDialog progress = new JDialog(frame, "Setting up " + name, false);
        JTextArea log    = new JTextArea(12, 50);
        log.setEditable(false);
        log.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        log.setBackground(new Color(20, 20, 20));
        log.setForeground(new Color(180, 255, 180));
        JScrollPane scroll = new JScrollPane(log);
        progress.add(scroll);
        progress.pack();
        progress.setLocationRelativeTo(frame);
        progress.setVisible(true);

        new Thread(() -> {
            try {
                appendLog(log, "Checking Docker image...");
                if (!DockerManager.imageExists()) {
                    appendLog(log, "Building image (first time only, may take a few minutes)...");
                    DockerManager.buildImage(line -> appendLog(log, line));
                }

                appendLog(log, "Creating container for " + name + "...");
                String containerName = DockerManager.createAndStart(name, steamUser, steamPass, port, mode);

                appendLog(log, "Container started: " + containerName);
                appendLog(log, "Downloading game on first run — may take 10–30 minutes.");
                appendLog(log, "If Steam Guard is required, the card will prompt for the email code.");

                BotClient bot = new BotClient(name, "localhost", port, containerName,
                        steamUser, steamPass, mode);
                SwingUtilities.invokeLater(() -> {
                    addBot(bot);
                    saveConfig();
                    progress.dispose();
                });

            } catch (Exception e) {
                appendLog(log, "ERROR: " + e.getMessage());
            }
        }).start();
    }

    private void appendLog(JTextArea log, String line) {
        SwingUtilities.invokeLater(() -> {
            log.append(line + "\n");
            log.setCaretPosition(log.getDocument().getLength());
        });
    }

    private int nextAvailablePort() {
        int port = 9001;
        for (BotClient b : bots) port = Math.max(port, b.port + 1);
        return port;
    }

    private void addBot(BotClient bot) {
        bots.add(bot);
        BotCardPanel card = new BotCardPanel(bot);
        cards.add(card);
        grid.add(card);
        grid.revalidate();
        grid.repaint();
    }

    private void removeBot(BotClient bot) {
        int idx = bots.indexOf(bot);
        if (idx < 0) return;
        bots.remove(idx);
        BotCardPanel card = cards.remove(idx);
        grid.remove(card);
        grid.revalidate();
        grid.repaint();
        saveConfig();

        if (bot.isDockerManaged()) {
            new Thread(() -> {
                try { DockerManager.stop(bot.containerName); }
                catch (Exception e) { System.err.println("Could not stop container: " + e.getMessage()); }
            }).start();
        }
    }

    // -------------------------------------------------------------------------
    // Config persistence

    private void loadConfig() {
        File f = new File(CONFIG_FILE);
        if (!f.exists()) return;
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = r.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("#")) {
                    try { addBot(BotClient.parse(line)); } catch (Exception ignored) {}
                }
            }
        } catch (IOException e) {
            System.err.println("Could not read " + CONFIG_FILE + ": " + e.getMessage());
        }
    }

    private void saveConfig() {
        try (PrintWriter w = new PrintWriter(new FileWriter(CONFIG_FILE))) {
            for (BotClient bot : bots) w.println(bot.toConfigLine());
        } catch (IOException e) {
            System.err.println("Could not save " + CONFIG_FILE + ": " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // Polling

    private void pollAll() {
        for (BotCardPanel card : new ArrayList<>(cards)) {
            BotClient bot = card.bot;
            String  status      = null;
            boolean guardNeeded = false;

            if (bot.isDockerManaged() && !DockerManager.isRunning(bot.containerName)) {
                // Container stopped — check why
                if (DockerManager.hasGuardRequired(bot.containerName)) {
                    guardNeeded = true;
                }
            } else {
                status = bot.fetchStatus();
            }

            final String  s  = status;
            final boolean gn = guardNeeded;
            SwingUtilities.invokeLater(() -> card.update(s, gn));
        }
    }

    // -------------------------------------------------------------------------
    // Bot card

    private class BotCardPanel extends JPanel {

        final BotClient bot;
        private final JLabel   statusLabel;
        private final JLabel   ageLabel;
        private final JPanel   dot;
        private final JButton  guardBtn;
        private Instant lastSeen;
        private JFrame screenshotFrame;

        BotCardPanel(BotClient bot) {
            this.bot = bot;
            setPreferredSize(new Dimension(CARD_W, CARD_H));
            setBackground(CARD_BG);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(70, 70, 74), 1),
                    new EmptyBorder(10, 12, 10, 12)));
            setLayout(new BorderLayout(4, 4));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            dot = new JPanel();
            dot.setPreferredSize(new Dimension(10, 10));
            dot.setBackground(DEAD);

            JLabel nameLabel = new JLabel(bot.name);
            nameLabel.setForeground(TEXT_PRIMARY);
            nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 13f));

            JButton removeBtn = new JButton("×");
            removeBtn.setForeground(TEXT_MUTED);
            removeBtn.setBackground(CARD_BG);
            removeBtn.setBorder(new EmptyBorder(0, 6, 0, 0));
            removeBtn.setContentAreaFilled(false);
            removeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            removeBtn.setFont(removeBtn.getFont().deriveFont(14f));
            removeBtn.addActionListener(e -> removeBot(bot));
            removeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) { removeBtn.setForeground(DEAD); }
                public void mouseExited(java.awt.event.MouseEvent e)  { removeBtn.setForeground(TEXT_MUTED); }
            });

            JPanel header = new JPanel(new BorderLayout(6, 0));
            header.setOpaque(false);
            header.add(dot,       BorderLayout.WEST);
            header.add(nameLabel, BorderLayout.CENTER);
            header.add(removeBtn, BorderLayout.EAST);

            statusLabel = new JLabel("connecting...");
            statusLabel.setForeground(TEXT_MUTED);
            statusLabel.setFont(statusLabel.getFont().deriveFont(11f));

            ageLabel = new JLabel(" ");
            ageLabel.setForeground(new Color(90, 90, 90));
            ageLabel.setFont(ageLabel.getFont().deriveFont(10f));

            JLabel hostLabel = new JLabel(bot.host + ":" + bot.port);
            hostLabel.setForeground(new Color(90, 90, 90));
            hostLabel.setFont(hostLabel.getFont().deriveFont(10f));

            JPanel statusRow = new JPanel(new BorderLayout());
            statusRow.setOpaque(false);
            statusRow.add(statusLabel, BorderLayout.CENTER);
            statusRow.add(ageLabel,    BorderLayout.EAST);

            guardBtn = styledButton("Enter Steam Guard Code", WARN);
            guardBtn.setFont(guardBtn.getFont().deriveFont(10f));
            guardBtn.setVisible(false);
            guardBtn.addActionListener(e -> showGuardCodeDialog());

            JPanel south = new JPanel();
            south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
            south.setOpaque(false);
            south.add(statusRow);
            south.add(guardBtn);

            add(header,    BorderLayout.NORTH);
            add(hostLabel, BorderLayout.CENTER);
            add(south,     BorderLayout.SOUTH);

            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    if (e.getSource() == BotCardPanel.this) openScreenshot();
                }
                public void mouseEntered(java.awt.event.MouseEvent e) { setBackground(CARD_BG.brighter()); }
                public void mouseExited(java.awt.event.MouseEvent e)  { setBackground(CARD_BG); }
            });
        }

        void update(String status, boolean guardNeeded) {
            if (guardNeeded) {
                statusLabel.setText("Steam Guard required");
                dot.setBackground(WARN);
                if (!guardBtn.isVisible()) {
                    guardBtn.setVisible(true);
                    setPreferredSize(new Dimension(CARD_W, CARD_H + 34));
                    revalidate();
                    grid.revalidate();
                    grid.repaint();
                }
                return;
            }

            if (guardBtn.isVisible()) {
                guardBtn.setVisible(false);
                setPreferredSize(new Dimension(CARD_W, CARD_H));
                revalidate();
                grid.revalidate();
                grid.repaint();
            }

            if (status != null) {
                lastSeen = Instant.now();
                statusLabel.setText(status);
                dot.setBackground(OK);
            } else {
                if (lastSeen == null) {
                    statusLabel.setText("unreachable");
                    dot.setBackground(DEAD);
                } else {
                    long ago = Instant.now().getEpochSecond() - lastSeen.getEpochSecond();
                    dot.setBackground(ago < 15 ? WARN : DEAD);
                }
            }
            if (lastSeen != null) {
                long ago = Instant.now().getEpochSecond() - lastSeen.getEpochSecond();
                ageLabel.setText(ago + "s");
            }
        }

        private void showGuardCodeDialog() {
            JTextField     codeField = new JTextField(8);
            JTextField     userField = new JTextField(bot.steamUser != null ? bot.steamUser : "", 18);
            JPasswordField passField = new JPasswordField(bot.steamPass != null ? bot.steamPass : "", 18);
            String effectiveMode = bot.mode != null ? bot.mode : "blacksmithing";

            JPanel form = new JPanel(new GridBagLayout());
            form.setOpaque(false);
            GridBagConstraints l = new GridBagConstraints();
            l.anchor = GridBagConstraints.WEST; l.insets = new Insets(4, 4, 4, 10);
            GridBagConstraints f = new GridBagConstraints();
            f.fill = GridBagConstraints.HORIZONTAL; f.weightx = 1; f.insets = new Insets(4, 0, 4, 4);

            l.gridx = 0; l.gridy = 0; form.add(new JLabel("Code from email:"), l);
            f.gridx = 1; f.gridy = 0; form.add(codeField, f);
            l.gridy = 1; form.add(new JLabel("Steam username:"), l);
            f.gridy = 1; form.add(userField, f);
            l.gridy = 2; form.add(new JLabel("Steam password:"), l);
            f.gridy = 2; form.add(passField, f);

            int result = JOptionPane.showConfirmDialog(
                    frame, form, "Steam Guard — " + bot.name,
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION) return;

            String code = codeField.getText().trim().toUpperCase();
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword());
            if (code.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            bot.steamUser = user;
            bot.steamPass = pass;
            saveConfig();

            new Thread(() -> {
                try {
                    DockerManager.restartWithGuardCode(bot.name, user, pass, bot.port, effectiveMode, code);
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(frame, "Failed to restart: " + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE));
                }
            }).start();
        }

        void openScreenshot() {
            if (screenshotFrame != null && screenshotFrame.isVisible()) {
                screenshotFrame.toFront();
                return;
            }
            screenshotFrame = new ScreenshotFrame(bot);
            screenshotFrame.setVisible(true);
        }
    }

    // -------------------------------------------------------------------------
    // Screenshot window

    private static class ScreenshotFrame extends JFrame {

        private final JLabel imageLabel;
        private final JLabel statusBar;
        private final ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();

        ScreenshotFrame(BotClient bot) {
            super("Screenshot — " + bot.name);
            setSize(SCREENSHOT_W, SCREENSHOT_H + 30);
            setLocationRelativeTo(null);
            getContentPane().setBackground(BG);
            setLayout(new BorderLayout());

            imageLabel = new JLabel("Loading...", SwingConstants.CENTER);
            imageLabel.setForeground(TEXT_MUTED);
            imageLabel.setBackground(BG);
            imageLabel.setOpaque(true);
            add(imageLabel, BorderLayout.CENTER);

            statusBar = new JLabel(" ", SwingConstants.CENTER);
            statusBar.setForeground(TEXT_MUTED);
            statusBar.setBackground(new Color(40, 40, 40));
            statusBar.setOpaque(true);
            statusBar.setFont(statusBar.getFont().deriveFont(11f));
            statusBar.setBorder(new EmptyBorder(4, 8, 4, 8));
            add(statusBar, BorderLayout.SOUTH);

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) { timer.shutdownNow(); }
            });

            timer.scheduleAtFixedRate(() -> {
                String  status = bot.fetchStatus();
                BufferedImage img = bot.fetchScreenshot();
                SwingUtilities.invokeLater(() -> {
                    if (img != null) {
                        imageLabel.setIcon(new ImageIcon(scaleToFit(img, SCREENSHOT_W, SCREENSHOT_H)));
                        imageLabel.setText(null);
                    } else {
                        imageLabel.setIcon(null);
                        imageLabel.setText("Could not reach bot");
                    }
                    statusBar.setText(status != null ? status : "unreachable");
                });
            }, 0, 2, TimeUnit.SECONDS);
        }
    }

    // -------------------------------------------------------------------------

    private static BufferedImage scaleToFit(BufferedImage src, int maxW, int maxH) {
        double scale = Math.min((double) maxW / src.getWidth(), (double) maxH / src.getHeight());
        int w = (int) (src.getWidth()  * scale);
        int h = (int) (src.getHeight() * scale);
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = out.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(src, 0, 0, w, h, null);
        g.dispose();
        return out;
    }

    private static JButton styledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(6, 14, 6, 14));
        btn.setFont(btn.getFont().deriveFont(Font.BOLD, 12f));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // -------------------------------------------------------------------------
    // Wrapping FlowLayout

    private static class WrapLayout extends FlowLayout {
        WrapLayout(int align, int hgap, int vgap) { super(align, hgap, vgap); }

        @Override public Dimension preferredLayoutSize(Container t) { return layoutSize(t, true);  }
        @Override public Dimension minimumLayoutSize(Container t)   { return layoutSize(t, false); }

        private Dimension layoutSize(Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                int maxW = target.getWidth() - target.getInsets().left - target.getInsets().right - getHgap() * 2;
                if (maxW <= 0) maxW = Integer.MAX_VALUE;
                int rowW = 0, rowH = 0, totalH = target.getInsets().top + getVgap();
                for (Component c : target.getComponents()) {
                    if (!c.isVisible()) continue;
                    Dimension d = preferred ? c.getPreferredSize() : c.getMinimumSize();
                    if (rowW > 0 && rowW + getHgap() + d.width > maxW) {
                        totalH += rowH + getVgap(); rowW = 0; rowH = 0;
                    }
                    rowW += d.width + (rowW > 0 ? getHgap() : 0);
                    rowH = Math.max(rowH, d.height);
                }
                return new Dimension(target.getWidth(), totalH + rowH + target.getInsets().bottom + getVgap());
            }
        }
    }
}
