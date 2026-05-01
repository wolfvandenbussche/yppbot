package bot.flow;

import bot.flow.steps.LoginSteps;
import bot.nav.Navigator;
import bot.state.GameState;
import bot.state.ScreenDetector;
import logic.bilging.Bilging;
import logic.bilging.BilgingReader;
import logic.bilging.pieces.BilgePiece;
import utility.Mouse;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Full end-to-end flow for "bilging-mission":
 *
 *   login / create pirate
 *     → navigate to Expedition Notice Board
 *     → accept a bilging mission
 *     → board mission ship
 *     → navigate to bilge station
 *     → play bilging puzzle until mission ends
 *     → return to house
 *
 * Navigation coordinates marked TODO are filled in iteratively through
 * live-game exploration (screenshots + pixel logging).
 */
public class BilgingMissionFlow implements Flow {

    @Override
    public String name() { return "bilging-mission"; }

    @Override
    public void run(FlowContext ctx) throws Exception {

        // ── 1. Ensure logged in ───────────────────────────────────────────────
        LoginSteps.ensureInGame(ctx);

        // ── 2. Navigate to notice board ───────────────────────────────────────
        ctx.status("navigating to notice board");
        goToNoticeBoard(ctx);

        // ── 3. Accept bilging mission ─────────────────────────────────────────
        ctx.status("accepting bilging mission");
        acceptBilgingMission(ctx);

        // ── 4. Board assigned ship ────────────────────────────────────────────
        ctx.status("boarding ship");
        boardMissionShip(ctx);

        // ── 5. Find bilge station ─────────────────────────────────────────────
        ctx.status("finding bilge station");
        findBilgeStation(ctx);

        // ── 6. Play bilging until mission ends ────────────────────────────────
        ctx.status("bilging");
        playBilging(ctx);

        // ── 7. Return to house ────────────────────────────────────────────────
        ctx.status("returning to house");
        returnToHouse(ctx);

        ctx.status("bilging-mission complete");
    }

    // ── Navigation steps ──────────────────────────────────────────────────────

    private void goToNoticeBoard(FlowContext ctx) throws Exception {
        // Exploration required: discover town layout for starting island on Emerald ocean.
        // Steps (approximate, to be corrected):
        //   1. Click towards the notice board on the island map to walk there
        //   2. Wait for the board dialog to open or click the board sign
        //   3. Detect NOTICE_BOARD state
        //
        // TODO: discover exact coordinates through exploration
        BufferedImage screen = ctx.screenshot();
        ScreenDetector.logPixels(screen, "in_town");
        ScreenDetector.saveDebugScreenshot(screen, "in_town");

        ctx.status("TODO: navigate to notice board — logging screen for coord discovery");
        ctx.sleep(2000);
    }

    private void acceptBilgingMission(FlowContext ctx) throws Exception {
        // At the notice board, find and click a bilging expedition mission.
        // TODO: discover notice board UI coordinates
        BufferedImage screen = ctx.screenshot();
        ScreenDetector.logPixels(screen, "notice_board");
        ScreenDetector.saveDebugScreenshot(screen, "notice_board");

        ctx.status("TODO: accept bilging mission — logging screen for coord discovery");
        ctx.sleep(2000);
    }

    private void boardMissionShip(FlowContext ctx) throws Exception {
        // Navigate from docks to the assigned ship and board it.
        // TODO: discover ship boarding flow
        ctx.status("TODO: board mission ship");
        ctx.sleep(2000);
    }

    private void findBilgeStation(FlowContext ctx) throws Exception {
        // On the ship deck, navigate to the bilge station and click it.
        // In Puzzle Pirates, the bilge station is typically below deck.
        // Navigation: click to walk, camera rotation with right-drag.
        // TODO: discover ship layout and bilge station coordinates
        BufferedImage screen = ctx.screenshot();
        ScreenDetector.logPixels(screen, "on_ship");
        ScreenDetector.saveDebugScreenshot(screen, "on_ship");

        ctx.status("TODO: find bilge station");
        ctx.sleep(2000);
    }

    // ── Bilging puzzle loop ────────────────────────────────────────────────────

    private void playBilging(FlowContext ctx) throws Exception {
        ctx.status("bilging puzzle running");
        Bilging bilging = new Bilging();
        Mouse mouse = new Mouse();
        Random rng = new Random();
        int idleStreak = 0;

        while (true) {
            if (!ScreenDetector.isBilging()) {
                idleStreak++;
                if (idleStreak > 30) {
                    ctx.status("bilge station lost — mission probably ended");
                    break;
                }
                ctx.sleep(500);
                continue;
            }
            idleStreak = 0;

            BilgePiece[][] board = BilgingReader.readScreen();
            if (board == null) { ctx.sleep(400); continue; }

            Point move = bilging.bestOneMove(board);
            if (move == null) { ctx.sleep(400); continue; }

            Rectangle pb = BilgingReader.getPuzzleBounds();
            if (pb == null) { ctx.sleep(400); continue; }

            int x = pb.x + 28 + rng.nextInt(41) + (move.y * 45);
            int y = pb.y + rng.nextInt(40) + (move.x * 45);
            mouse.clickAt(new Point(x, y));
        }
    }

    // ── Return ────────────────────────────────────────────────────────────────

    private void returnToHouse(FlowContext ctx) throws Exception {
        // After the mission: leave ship → navigate to house on island.
        // TODO: discover return flow
        ctx.status("TODO: return to house");
        ctx.sleep(2000);
    }
}
