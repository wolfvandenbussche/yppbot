package utility;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class PuzzlePirates {

    private static String windowTitle = "Puzzle Pirates";

    public static void configure(String title) {
        windowTitle = title;
    }

    public static void takeScreenshot(BufferedImage image, String name) {
        // disabled in headless mode
    }

    /** Captures the entire virtual display. */
    public static BufferedImage getFullScreen() {
        File tmp = null;
        try {
            tmp = File.createTempFile("yppbot-full", ".png");
            tmp.delete();
            String display = System.getenv("DISPLAY");
            if (display == null) display = ":1";
            ProcessBuilder pb = new ProcessBuilder("scrot", "-z", tmp.getAbsolutePath());
            pb.environment().put("DISPLAY", display);
            pb.redirectErrorStream(true);
            Process p = pb.start();
            p.waitFor(5, TimeUnit.SECONDS);
            return ImageIO.read(tmp);
        } catch (Exception e) {
            return null;
        } finally {
            if (tmp != null) tmp.delete();
        }
    }

    public static BufferedImage getImage(Rectangle rectangle) {
        File tmp = null;
        try {
            tmp = File.createTempFile("yppbot", ".png");
            tmp.delete(); // scrot won't overwrite an existing file
            String display = System.getenv("DISPLAY");
            if (display == null) display = ":1";
            ProcessBuilder pb = new ProcessBuilder("scrot", "-z", tmp.getAbsolutePath());
            pb.environment().put("DISPLAY", display);
            pb.redirectErrorStream(true);
            Process p = pb.start();
            p.waitFor(5, TimeUnit.SECONDS);
            BufferedImage full = ImageIO.read(tmp);
            if (full == null) return null;
            int x = Math.max(0, rectangle.x);
            int y = Math.max(0, rectangle.y);
            int w = Math.min(rectangle.width,  full.getWidth()  - x);
            int h = Math.min(rectangle.height, full.getHeight() - y);
            if (w <= 0 || h <= 0) return null;
            return full.getSubimage(x, y, w, h);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (tmp != null) tmp.delete();
        }
    }

    public static void toTop() {
        CrossPlatformWindowManager.focusWindow();
    }

    public static Rectangle getPuzzlePiratesBounds() {
        Rectangle bounds = CrossPlatformWindowManager.getWindowBounds();
        if (bounds == null) return null;
        bounds.x += 3;
        bounds.width -= 6;
        bounds.y += 26;
        bounds.height -= 29;
        return bounds;
    }

    public static boolean isRunning() {
        return CrossPlatformWindowManager.isWindowAvailable(windowTitle);
    }
}
