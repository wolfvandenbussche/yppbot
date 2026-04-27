package bot;

import utility.PuzzlePirates;
import utility.Mouse;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Detects the current game screen and takes the appropriate action to advance
 * toward the blacksmithing puzzle.
 *
 * Detection is purely pixel-based — no template matching. Each state looks for
 * a distinctive color signature at a known region of the screen.
 */
public class GameStateManager {

    public enum State {
        UNKNOWN,
        LOGIN_SCREEN,
        CHARACTER_SELECT,
        IN_GAME,
        BLACKSMITHING
    }

    private static final Random RNG = new Random();

    private final Mouse mouse;
    private State lastState = State.UNKNOWN;

    public GameStateManager(Mouse mouse) {
        this.mouse = mouse;
    }

    // -------------------------------------------------------------------------
    // Public API

    public State detect() {
        BufferedImage screen = PuzzlePirates.getFullScreen();
        if (screen == null) return State.UNKNOWN;

        if (isLoginScreen(screen))      return lastState = State.LOGIN_SCREEN;
        if (isCharacterSelect(screen))  return lastState = State.CHARACTER_SELECT;
        if (isBlacksmithing(screen))    return lastState = State.BLACKSMITHING;
        if (isInGame(screen))           return lastState = State.IN_GAME;
        return lastState = State.UNKNOWN;
    }

    /** Perform the default action for the current state. Returns the new state. */
    public State act(State state, BotServer server) throws InterruptedException {
        switch (state) {
            case LOGIN_SCREEN:     handleLogin(server);     break;
            case CHARACTER_SELECT: handleCharSelect(server); break;
            case IN_GAME:          handleInGame(server);    break;
            default: break;
        }
        Thread.sleep(1500);
        return detect();
    }

    // -------------------------------------------------------------------------
    // State detection

    private boolean isLoginScreen(BufferedImage screen) {
        // Login screen: pure black background, blue oval in the centre.
        // Background corners are #000000.
        // The oval interior is a medium steel-blue (~R60-90, G100-140, B160-200).
        int cx = screen.getWidth()  / 2;
        int cy = screen.getHeight() / 2;

        Color bg = avgColor(screen, 10, 10, 80, 80);
        boolean hasDarkBg = bg.getRed() < 30 && bg.getGreen() < 30 && bg.getBlue() < 30;

        // Sample the blue oval interior just above centre
        Color oval = avgColor(screen, cx - 60, cy - 40, 120, 40);
        boolean hasBlueOval = oval.getRed()   > 40  && oval.getRed()   < 130
                           && oval.getGreen() > 80  && oval.getGreen() < 180
                           && oval.getBlue()  > 140 && oval.getBlue()  < 230
                           && oval.getBlue() > oval.getRed() + 40;

        return hasDarkBg && hasBlueOval;
    }

    private boolean isCharacterSelect(BufferedImage screen) {
        // TODO: implement once we have a screenshot of this screen
        return false;
    }

    private boolean isInGame(BufferedImage screen) {
        // TODO: implement once we have a screenshot of the in-game lobby
        return false;
    }

    private boolean isBlacksmithing(BufferedImage screen) {
        // Delegate to the existing BlacksmithingReader bounds detection
        return logic.blacksmithing.BlacksmithingReader.getPuzzleBounds() != null;
    }

    // -------------------------------------------------------------------------
    // Actions

    private void handleLogin(BotServer server) throws InterruptedException {
        if (server != null) server.setStatus("clicking Logon");
        BufferedImage screen = PuzzlePirates.getFullScreen();
        if (screen == null) return;

        int cx = screen.getWidth()  / 2;
        int cy = screen.getHeight() / 2;

        // The Logon button is a lighter blue rectangle below the logo (~cy+30 to cy+60).
        // Search for it; fall back to the proportional position if not found.
        Point btn = findColorRegion(screen,
                cx - 80, cy + 20, 160, 60,
                new Color(100, 150, 210), 50);

        humanClick(btn != null ? btn : new Point(cx, cy + 35));
    }

    private void handleCharSelect(BotServer server) throws InterruptedException {
        if (server != null) server.setStatus("on character select — not yet implemented");
        // Will be implemented once we have a screenshot
    }

    private void handleInGame(BotServer server) throws InterruptedException {
        if (server != null) server.setStatus("in game — navigation not yet implemented");
        // Will be implemented once we can see the in-game screen
    }

    // -------------------------------------------------------------------------
    // Helpers

    /**
     * Scans a region for the first pixel cluster matching the target color within
     * the given tolerance. Returns the centre of the match, or null.
     */
    private Point findColorRegion(BufferedImage img, int rx, int ry, int rw, int rh,
                                  Color target, int tolerance) {
        int sumX = 0, sumY = 0, count = 0;
        for (int y = ry; y < ry + rh && y < img.getHeight(); y++) {
            for (int x = rx; x < rx + rw && x < img.getWidth(); x++) {
                Color c = new Color(img.getRGB(x, y), true);
                if (colorDistance(c, target) < tolerance) {
                    sumX += x; sumY += y; count++;
                }
            }
        }
        return count > 20 ? new Point(sumX / count, sumY / count) : null;
    }

    private double colorDistance(Color a, Color b) {
        int dr = a.getRed()   - b.getRed();
        int dg = a.getGreen() - b.getGreen();
        int db = a.getBlue()  - b.getBlue();
        return Math.sqrt(dr * dr + dg * dg + db * db);
    }

    private Color avgColor(BufferedImage img, int x, int y, int w, int h) {
        long r = 0, g = 0, b = 0, n = 0;
        for (int py = y; py < y + h && py < img.getHeight(); py++) {
            for (int px = x; px < x + w && px < img.getWidth(); px++) {
                Color c = new Color(img.getRGB(px, py));
                r += c.getRed(); g += c.getGreen(); b += c.getBlue(); n++;
            }
        }
        if (n == 0) return Color.BLACK;
        return new Color((int)(r/n), (int)(g/n), (int)(b/n));
    }

    /** Click with a small random human-like offset and a short pre-click pause. */
    private void humanClick(Point p) throws InterruptedException {
        Thread.sleep(50 + RNG.nextInt(120));
        int ox = RNG.nextInt(9) - 4;
        int oy = RNG.nextInt(9) - 4;
        mouse.clickAt(new Point(p.x + ox, p.y + oy));
    }
}
