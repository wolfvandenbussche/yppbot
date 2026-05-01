package bot.flow;

import bot.BotServer;
import bot.state.GameState;
import bot.state.ScreenDetector;
import utility.PuzzlePirates;

import java.awt.image.BufferedImage;

public class FlowContext {

    public final BotServer server;

    public FlowContext(BotServer server) {
        this.server = server;
    }

    public void status(String msg) {
        System.out.println("[bot] " + msg);
        if (server != null) server.setStatus(msg);
    }

    public BufferedImage screenshot() {
        return PuzzlePirates.getFullScreen();
    }

    public GameState currentState() {
        return ScreenDetector.detect(screenshot());
    }

    public GameState currentState(BufferedImage screen) {
        return ScreenDetector.detect(screen);
    }

    /**
     * Block until the game reaches one of the target states, or timeout.
     * Logs pixel info whenever UNKNOWN is encountered for debugging.
     */
    public GameState waitFor(long timeoutMs, GameState... targets) throws InterruptedException {
        long deadline = System.currentTimeMillis() + timeoutMs;
        int unknownCount = 0;
        while (System.currentTimeMillis() < deadline) {
            BufferedImage screen = screenshot();
            GameState s = ScreenDetector.detect(screen);
            for (GameState t : targets) {
                if (s == t) return s;
            }
            if (s == GameState.UNKNOWN) {
                unknownCount++;
                if (unknownCount % 10 == 1) {
                    // Log pixel info every ~5s to help build detection
                    ScreenDetector.logPixels(screen, "unknown");
                }
            } else {
                unknownCount = 0;
            }
            Thread.sleep(500);
        }
        return GameState.UNKNOWN;
    }

    public void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}
