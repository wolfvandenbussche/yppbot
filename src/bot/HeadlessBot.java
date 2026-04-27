package bot;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import logic.bilging.Bilging;
import logic.bilging.BilgingReader;
import logic.bilging.pieces.BilgePiece;
import logic.blacksmithing.Blacksmithing;
import logic.blacksmithing.BlacksmithingReader;
import logic.blacksmithing.pieces.BlacksmithingPiece;
import utility.Mouse;
import utility.PuzzlePirates;

public class HeadlessBot {

    public static void main(String[] args) throws Exception {
        String windowTitle = "Puzzle Pirates";
        String mode        = "blacksmithing";
        int    port        = 0;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--window": windowTitle = args[++i]; break;
                case "--mode":   mode        = args[++i]; break;
                case "--port":   port        = Integer.parseInt(args[++i]); break;
            }
        }

        PuzzlePirates.configure(windowTitle);

        BotServer server = null;
        if (port > 0) {
            server = new BotServer(port);
        }

        status(server, "waiting for game window: " + windowTitle);
        while (!PuzzlePirates.isRunning()) {
            Thread.sleep(5000);
        }

        status(server, "game found, starting in 3s");
        Thread.sleep(3000);

        switch (mode) {
            case "blacksmithing": runBlacksmithing(server); break;
            case "bilging":       runBilging(server);       break;
            default:
                System.err.println("unknown mode: " + mode);
                System.exit(1);
        }
    }

    private static void status(BotServer server, String msg) {
        if (server != null) server.setStatus(msg);
        else System.out.println("[bot] " + msg);
    }

    // -------------------------------------------------------------------------

    private static void runBlacksmithing(BotServer server) throws InterruptedException {
        Blacksmithing blacksmithing = new Blacksmithing();
        Mouse mouse = new Mouse();
        int boardCount = 0;

        while (true) {
            status(server, "reading board");
            BlacksmithingPiece[][] board = BlacksmithingReader.readScreen();
            if (board == null) {
                status(server, "board not visible, retrying");
                Thread.sleep(1000);
                continue;
            }

            boardCount++;
            status(server, "computing moves (board " + boardCount + "/3)");
            ArrayList<Point> moves = blacksmithing.bestOrder(board);

            Rectangle puzzleBounds = PuzzlePirates.getPuzzlePiratesBounds();
            if (puzzleBounds == null) {
                status(server, "lost game window");
                Thread.sleep(1000);
                continue;
            }

            int boardX = puzzleBounds.x + BlacksmithingReader.X;
            int boardY = puzzleBounds.y + BlacksmithingReader.Y;

            status(server, "clicking " + moves.size() + " moves (board " + boardCount + "/3)");
            for (Point move : moves) {
                mouse.clickAt(new Point(boardX + move.y * 60 + 5, boardY + move.x * 60 + 5));
            }

            if (boardCount == 3) {
                boardCount = 0;
                status(server, "resetting table");
                Thread.sleep(4000);
                mouse.clickAt(new Point(boardX + 120, boardY + 348));
                mouse.clickAt(new Point(boardX + 120, boardY + 302));
            }

            Thread.sleep(2000);
        }
    }

    // -------------------------------------------------------------------------

    private static void runBilging(BotServer server) throws InterruptedException {
        Bilging bilging = new Bilging();
        Mouse mouse = new Mouse();
        Random random = new Random();

        while (true) {
            status(server, "reading board");
            BilgePiece[][] board = BilgingReader.readScreen();
            if (board == null) {
                status(server, "board not visible, retrying");
                Thread.sleep(500);
                continue;
            }

            Point bestMove = bilging.bestOneMove(board);
            if (bestMove == null) {
                status(server, "no moves found, retrying");
                Thread.sleep(500);
                continue;
            }

            Rectangle puzzleBounds = BilgingReader.getPuzzleBounds();
            if (puzzleBounds == null) {
                status(server, "lost game window");
                Thread.sleep(500);
                continue;
            }

            status(server, "clicking move (" + bestMove.x + "," + bestMove.y + ")");
            int x = puzzleBounds.x + 28 + (random.nextInt(41) + 2) + (bestMove.y * 45);
            int y = puzzleBounds.y + (random.nextInt(40) + 2) + (bestMove.x * 45);
            mouse.clickAt(new Point(x, y));
        }
    }
}
