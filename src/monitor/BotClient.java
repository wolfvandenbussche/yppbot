package monitor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class BotClient {

    public final String name;
    public final String host;
    public final int    port;
    public final String containerName; // null if not Docker-managed

    // Credentials stored for Steam Guard restart — null if not a Docker-managed bot
    public String steamUser;
    public String steamPass;
    public String mode;

    private static final int TIMEOUT_MS = 3000;

    public BotClient(String name, String host, int port, String containerName) {
        this(name, host, port, containerName, null, null, null);
    }

    public BotClient(String name, String host, int port, String containerName,
                     String steamUser, String steamPass, String mode) {
        this.name          = name;
        this.host          = host;
        this.port          = port;
        this.containerName = containerName;
        this.steamUser     = steamUser;
        this.steamPass     = steamPass;
        this.mode          = mode;
    }

    public boolean isDockerManaged() {
        return containerName != null;
    }

    /** Returns the bot's current status string, or null if unreachable. */
    public String fetchStatus() {
        try {
            HttpURLConnection conn = open("/status");
            if (conn.getResponseCode() != 200) return null;
            try (InputStream in = conn.getInputStream()) {
                return new String(in.readAllBytes(), "UTF-8").trim();
            }
        } catch (Exception e) {
            return null;
        }
    }

    /** Returns a screenshot from the bot's display, or null if unreachable. */
    public BufferedImage fetchScreenshot() {
        try {
            HttpURLConnection conn = open("/screenshot");
            if (conn.getResponseCode() != 200) return null;
            try (InputStream in = conn.getInputStream()) {
                byte[] bytes = in.readAllBytes();
                BufferedImage img = ImageIO.read(new java.io.ByteArrayInputStream(bytes));
                if (img != null) saveScreenshot(bytes);
                return img;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private void saveScreenshot(byte[] png) {
        try {
            java.io.File dir = new java.io.File("screenshots");
            dir.mkdirs();
            java.nio.file.Files.write(
                    new java.io.File(dir, name + "-latest.png").toPath(), png);
        } catch (Exception ignored) {}
    }

    private HttpURLConnection open(String path) throws Exception {
        URL url = new URL("http", host, port, path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(TIMEOUT_MS);
        conn.setReadTimeout(TIMEOUT_MS);
        return conn;
    }

    /**
     * Serialise to bots.conf line:
     *   name@host:port
     *   name@host:port:container
     *   name@host:port:container:steamUser:base64(steamPass):mode
     */
    public String toConfigLine() {
        String base = name + "@" + host + ":" + port;
        if (containerName == null) return base;
        if (steamUser == null)     return base + ":" + containerName;
        String enc = Base64.getEncoder().encodeToString(steamPass.getBytes());
        return base + ":" + containerName + ":" + steamUser + ":" + enc + ":" + mode;
    }

    /**
     * Parse a bots.conf line:
     *   name@host:port
     *   name@host:port:container
     *   name@host:port:container:steamUser:base64(steamPass):mode
     */
    public static BotClient parse(String spec) {
        int at = spec.lastIndexOf('@');
        String[] right = spec.substring(at + 1).split(":", 6);
        String name      = spec.substring(0, at);
        String host      = right[0];
        int    port      = Integer.parseInt(right[1]);
        String container = right.length > 2 ? right[2] : null;
        String steamUser = right.length > 3 ? right[3] : null;
        String steamPass = right.length > 4
                ? new String(Base64.getDecoder().decode(right[4])) : null;
        String mode      = right.length > 5 ? right[5] : null;
        return new BotClient(name, host, port, container, steamUser, steamPass, mode);
    }
}
