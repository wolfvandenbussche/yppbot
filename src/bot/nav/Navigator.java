package bot.nav;

import utility.PuzzlePirates;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

/** Fast navigation helper using xdotool. All coordinates are game-window-relative. */
public class Navigator {

    private static final String DISPLAY =
            System.getenv("DISPLAY") != null ? System.getenv("DISPLAY") : ":1";

    public static void click(int gameX, int gameY) {
        Rectangle b = bounds();
        if (b == null) return;
        xdotool("mousemove", s(b.x + gameX), s(b.y + gameY));
        sleep(80);
        xdotool("click", "1");
        sleep(180);
    }

    public static void rightClick(int gameX, int gameY) {
        Rectangle b = bounds();
        if (b == null) return;
        xdotool("mousemove", s(b.x + gameX), s(b.y + gameY));
        sleep(80);
        xdotool("click", "3");
        sleep(180);
    }

    /** Hold right mouse and drag horizontally — used for camera rotation. */
    public static void rotateCameraRight(int gameX, int gameY, int pixels) {
        Rectangle b = bounds();
        if (b == null) return;
        int ax = b.x + gameX, ay = b.y + gameY;
        xdotool("mousemove", s(ax), s(ay));
        sleep(60);
        xdotool("mousedown", "3");
        sleep(80);
        xdotool("mousemove", s(ax + pixels), s(ay));
        sleep(100);
        xdotool("mouseup", "3");
        sleep(150);
    }

    public static void type(String text) {
        xdotool("type", "--delay", "80", "--", text);
    }

    public static void key(String keyName) {
        xdotool("key", "--", keyName);
        sleep(80);
    }

    /** Pixel color at game-window-relative coords from a full screenshot. */
    public static Color pixelAt(BufferedImage screen, int gameX, int gameY) {
        Rectangle b = bounds();
        if (b == null || screen == null) return Color.BLACK;
        int ax = b.x + gameX, ay = b.y + gameY;
        if (ax < 0 || ay < 0 || ax >= screen.getWidth() || ay >= screen.getHeight())
            return Color.BLACK;
        return new Color(screen.getRGB(ax, ay), false);
    }

    /** Average color in a game-window-relative region. */
    public static Color avgPixel(BufferedImage screen, int gameX, int gameY, int w, int h) {
        Rectangle b = bounds();
        if (b == null || screen == null) return Color.BLACK;
        long r = 0, g = 0, bl = 0, n = 0;
        for (int dy = 0; dy < h; dy++) {
            for (int dx = 0; dx < w; dx++) {
                int ax = b.x + gameX + dx, ay = b.y + gameY + dy;
                if (ax >= 0 && ay >= 0 && ax < screen.getWidth() && ay < screen.getHeight()) {
                    Color c = new Color(screen.getRGB(ax, ay), false);
                    r += c.getRed(); g += c.getGreen(); bl += c.getBlue(); n++;
                }
            }
        }
        return n == 0 ? Color.BLACK : new Color((int)(r/n), (int)(g/n), (int)(bl/n));
    }

    public static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    private static Rectangle bounds() {
        return PuzzlePirates.getPuzzlePiratesBounds();
    }

    private static String s(int v) { return String.valueOf(v); }

    private static void xdotool(String... args) {
        try {
            String[] cmd = new String[args.length + 1];
            cmd[0] = "xdotool";
            System.arraycopy(args, 0, cmd, 1, args.length);
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.environment().put("DISPLAY", DISPLAY);
            pb.redirectErrorStream(true);
            Process p = pb.start();
            p.waitFor(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.err.println("[nav] xdotool: " + e.getMessage());
        }
    }
}
