package bot.state;

import bot.nav.Navigator;
import logic.bilging.BilgingReader;
import logic.blacksmithing.BlacksmithingReader;
import utility.PuzzlePirates;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;

/**
 * Detects the current game screen state from a full-screen screenshot.
 * All coordinate arguments are game-window-relative (origin = top-left of game content area).
 *
 * Detection is pixel/color based. Each screen has distinctive color signatures sampled
 * at known positions. New signatures are discovered by exploring the live game and
 * logging pixel values via logPixels().
 */
public class ScreenDetector {

    /** Detect current state. Call with a fresh full-screen screenshot. */
    public static GameState detect(BufferedImage screen) {
        if (screen == null || PuzzlePirates.getPuzzlePiratesBounds() == null)
            return GameState.UNKNOWN;

        // Puzzle screens first — most specific
        if (isBilging())       return GameState.BILGING;
        if (isBlacksmithing()) return GameState.BLACKSMITHING;

        // Pre-game screens — CHARACTER_CREATE must come before LOGIN_SCREEN because the
        // create panel's blue dialog background passes the login oval colour check.
        if (isCharacterCreate(screen))  return GameState.CHARACTER_CREATE;
        if (isLoginScreen(screen))      return GameState.LOGIN_SCREEN;
        if (isCharacterSelect(screen))  return GameState.CHARACTER_SELECT;
        if (isLoading(screen))          return GameState.LOADING;

        // In-game screens
        if (isNoticeBoard(screen))      return GameState.NOTICE_BOARD;
        if (isOnShip(screen))           return GameState.ON_SHIP;
        if (isInTown(screen))           return GameState.IN_TOWN;
        if (isMissionComplete(screen))  return GameState.MISSION_COMPLETE;

        return GameState.UNKNOWN;
    }

    // ── Login screen ─────────────────────────────────────────────────────────
    // Black background everywhere outside the gold/blue oval frame.
    // Game window: 800x600. Oval frame centered around (397, 265).
    // Logon button: ~(330–465, 300–325).

    public static boolean isLoginScreen(BufferedImage screen) {
        // Corner is pure black
        Color corner = Navigator.pixelAt(screen, 10, 10);
        boolean darkBg = corner.getRed() < 40 && corner.getGreen() < 40 && corner.getBlue() < 40;

        // Sample the blue oval BELOW the "PUZZLE PIRATES" text logo (which is dark red).
        // The Logon button area at y=280-360 is clearly blue.
        Color oval = Navigator.avgPixel(screen, 280, 275, 240, 80);
        boolean blueOval = oval.getBlue() > 110
                && oval.getBlue() > oval.getRed() + 60
                && oval.getRed() < 80;

        return darkBg && blueOval;
    }

    // ── Character creation ────────────────────────────────────────────────────
    // CreatePiratePanel: white name-input rectangle at game ~(239-409, 214-232).
    // Sampled from live screenshot: abs (802,490) = game (239,224) = RGB(229,235,237).
    // This field is absent on every other screen, making it a reliable positive signal.

    public static boolean isCharacterCreate(BufferedImage screen) {
        // Sample interior of the name input field — white when present, dark/blue otherwise.
        Color nameField = Navigator.avgPixel(screen, 250, 216, 150, 14);
        return nameField.getRed() > 200 && nameField.getGreen() > 200 && nameField.getBlue() > 200;
    }

    // ── Character select ──────────────────────────────────────────────────────
    // TODO: sample once we reach this screen

    public static boolean isCharacterSelect(BufferedImage screen) {
        return false; // placeholder — fill in after exploration
    }

    // ── Loading screen ────────────────────────────────────────────────────────
    // Typically all-black or near-black during transition.

    public static boolean isLoading(BufferedImage screen) {
        Color c1 = Navigator.avgPixel(screen, 100, 100, 600, 400);
        return c1.getRed() < 15 && c1.getGreen() < 15 && c1.getBlue() < 15;
    }

    // ── In town ───────────────────────────────────────────────────────────────
    // TODO: sample once we're in town

    public static boolean isInTown(BufferedImage screen) {
        return false; // placeholder
    }

    // ── Notice board ──────────────────────────────────────────────────────────
    // TODO: sample once notice board is open

    public static boolean isNoticeBoard(BufferedImage screen) {
        return false; // placeholder
    }

    // ── On ship ───────────────────────────────────────────────────────────────
    // TODO: sample once we're on a ship

    public static boolean isOnShip(BufferedImage screen) {
        return false; // placeholder
    }

    // ── Mission complete ──────────────────────────────────────────────────────
    // TODO: sample once a mission completes

    public static boolean isMissionComplete(BufferedImage screen) {
        return false; // placeholder
    }

    // ── Puzzle delegates ──────────────────────────────────────────────────────

    public static boolean isBilging() {
        return BilgingReader.getPuzzleBounds() != null;
    }

    public static boolean isBlacksmithing() {
        return BlacksmithingReader.getPuzzleBounds() != null;
    }

    // ── Debug helpers ─────────────────────────────────────────────────────────

    /**
     * Log pixel colors at a grid of key positions across the game window.
     * Useful for building new detection methods when a new screen is encountered.
     */
    public static void logPixels(BufferedImage screen, String label) {
        System.out.println("[pixels] === " + label + " ===");
        int[][] spots = {
            {10, 10}, {397, 10}, {780, 10},          // top row
            {10, 285}, {200, 285}, {397, 285}, {590, 285}, {780, 285}, // middle row
            {10, 560}, {397, 560}, {780, 560},        // bottom row
            {397, 150}, {397, 200}, {397, 250},       // center column upper
            {397, 310}, {397, 350}, {397, 400},       // center column lower
        };
        for (int[] s : spots) {
            Color c = Navigator.pixelAt(screen, s[0], s[1]);
            System.out.printf("[pixels]   (%3d,%3d) = rgb(%3d,%3d,%3d) #%02x%02x%02x%n",
                s[0], s[1], c.getRed(), c.getGreen(), c.getBlue(),
                c.getRed(), c.getGreen(), c.getBlue());
        }
    }

    /** Save screenshot to /tmp with a timestamp for offline analysis. */
    public static void saveDebugScreenshot(BufferedImage screen, String tag) {
        try {
            String ts = new SimpleDateFormat("HHmmss").format(new Date());
            File f = new File("/tmp/yppbot_" + tag + "_" + ts + ".png");
            ImageIO.write(screen, "png", f);
            System.out.println("[debug] saved screenshot: " + f.getAbsolutePath());
        } catch (Exception ignored) {}
    }
}
