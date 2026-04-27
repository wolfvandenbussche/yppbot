package utility;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class PuzzlePirates {

    private static String windowTitle = "Puzzle Pirates";

    public static void configure(String title) {
        windowTitle = title;
    }

    public static void takeScreenshot(BufferedImage image, String name) {
        // disabled in headless mode
    }

    public static BufferedImage getImage(Rectangle rectangle) {
        BufferedImage image = null;
        try {
            Robot robot = new Robot();
            image = robot.createScreenCapture(rectangle);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static void toTop() {
        CrossPlatformWindowManager.focusWindow();
    }

    public static Rectangle getPuzzlePiratesBounds() {
        Rectangle bounds = CrossPlatformWindowManager.getWindowBounds();
        if (bounds == null) {
            return null;
        }
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
