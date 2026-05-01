package bot.flow.steps;

import bot.flow.FlowContext;
import bot.nav.Navigator;
import bot.state.GameState;
import bot.state.ScreenDetector;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Reusable login / character-selection steps used by every flow.
 *
 * The goal of ensureInGame() is to reach IN_TOWN (or any in-game state)
 * starting from any state including LOGIN_SCREEN, CHARACTER_CREATE, etc.
 */
public class LoginSteps {

    private static final String[] PREFIXES = {
        "Barnacle", "Bilge", "Bitter", "Black", "Brazen", "Brine", "Burnt",
        "Crimson", "Cracked", "Crooked", "Cruel",
        "Dark", "Dead", "Dire", "Dread",
        "Foul", "Frothy",
        "Gilded", "Grim", "Grog", "Gruff",
        "Half", "Hollow", "Howling",
        "Iron", "Inky",
        "Jagged", "Jolly",
        "Knotted", "Leaky", "Lost",
        "Mad", "Murky", "Night", "Old",
        "Patched", "Plank", "Ragged", "Rotten", "Rum", "Rusty",
        "Salt", "Savage", "Scarred", "Sea", "Shiver", "Sly", "Soggy", "Sour", "Storm", "Sunken",
        "Tar", "Tattered", "Torn", "Twisted",
        "Vile", "Weathered", "Wild", "Wind"
    };
    private static final String[] SUFFIXES = {
        "anchor", "barnacle", "beard", "bilge", "blade", "bone", "brine",
        "cannon", "chain", "claw", "crow",
        "dagger", "deck",
        "fang", "fin", "flint", "fog",
        "gale", "gull",
        "hatch", "helm", "hook", "hull",
        "jaw", "keel", "knot",
        "lash", "loot",
        "mast", "maw",
        "nail", "net", "oar",
        "pike", "plank",
        "reef", "rope", "rudder",
        "sail", "shark", "shoal", "skull", "squall",
        "tide", "timber",
        "wake", "wave", "wreck"
    };

    /**
     * Drive from any pre-game state to an in-game state (IN_TOWN / ON_SHIP / etc.).
     * Handles: LOGIN_SCREEN → CHARACTER_CREATE or CHARACTER_SELECT → LOADING → in-game.
     */
    public static void ensureInGame(FlowContext ctx) throws Exception {
        for (int attempt = 0; attempt < 120; attempt++) {
            BufferedImage screen = ctx.screenshot();
            GameState state = ctx.currentState(screen);
            ctx.status("login: " + state);

            switch (state) {
                case LOGIN_SCREEN:
                    clickLogon(ctx);
                    ctx.sleep(8000);
                    break;

                case CHARACTER_SELECT:
                    selectFirstPirate(ctx, screen);
                    ctx.sleep(4000);
                    break;

                case CHARACTER_CREATE:
                    createPirate(ctx, generateName());
                    ctx.sleep(4000);
                    break;

                case LOADING:
                    ctx.sleep(2000);
                    break;

                case IN_TOWN:
                case ON_SHIP:
                case NOTICE_BOARD:
                case BILGING:
                case BLACKSMITHING:
                    return; // already in game

                default:
                    // Unknown screen — log pixels and wait
                    ScreenDetector.logPixels(screen, "login_unknown");
                    ScreenDetector.saveDebugScreenshot(screen, "login_unknown");
                    ctx.sleep(2000);
                    break;
            }
        }
        throw new Exception("ensureInGame: could not reach in-game state after 120 attempts");
    }

    // ── Logon button ──────────────────────────────────────────────────────────

    public static void clickLogon(FlowContext ctx) throws InterruptedException {
        // Logon button center verified at absolute (959, 580) = game-relative (396, 314).
        // Button interior spans x=914-1004, y=573-588 in absolute coords.
        // Button is gray while PP connects to servers; turns blue when active.
        ctx.status("waiting for Logon button to activate...");
        for (int i = 0; i < 30; i++) {
            java.awt.Color c = Navigator.pixelAt(ctx.screenshot(), 396, 314);
            if (c.getBlue() > c.getRed() + 60) break;
            ctx.sleep(1000);
        }
        ctx.status("clicking Logon");
        Navigator.click(396, 314);
        ctx.sleep(8000);
    }

    // ── Character select ──────────────────────────────────────────────────────

    public static void selectFirstPirate(FlowContext ctx, BufferedImage screen) {
        ctx.status("selecting pirate");
        ScreenDetector.logPixels(screen, "char_select");
        ScreenDetector.saveDebugScreenshot(screen, "char_select");
        // TODO: once we see the character select screen, find the pirate portrait location
        // Placeholder: click the first portrait (estimated position)
        Navigator.click(200, 250);
        ctx.sleep(500);
        // Click the confirm/select button if any
        Navigator.click(397, 470);
    }

    // ── Character creation ────────────────────────────────────────────────────

    public static void createPirate(FlowContext ctx, String name) throws Exception {
        ctx.status("creating pirate: " + name);

        // Name input field: game ~(239-409, 214-232), center ~(320, 222).
        // Pixel-verified from live screenshot: abs (802,490) = game (239,224) = white.
        Navigator.click(320, 222);
        Navigator.sleep(300);
        Navigator.key("ctrl+a");
        Navigator.sleep(80);
        Navigator.key("Delete");
        Navigator.sleep(80);
        Navigator.type(name);
        ctx.sleep(500);

        // Birth date — three JComboBox dropdowns at game y≈447.
        // Positions pixel-estimated from live screenshot: Year≈(448,447), Month≈(514,447), Day≈(582,447).
        // Swing JComboBox prefix-key-search: typing "1990" within ~1s jumps to that entry.
        ctx.status("setting birth year...");
        Navigator.click(448, 447);
        Navigator.sleep(500);
        Navigator.type("1990");
        Navigator.sleep(400);
        Navigator.key("Return");
        Navigator.sleep(300);

        ctx.status("setting birth month...");
        Navigator.click(514, 447);
        Navigator.sleep(500);
        // Months may be numbers (1-12) or names; "1" selects January either way
        Navigator.type("1");
        Navigator.sleep(400);
        Navigator.key("Return");
        Navigator.sleep(300);

        ctx.status("setting birth day...");
        Navigator.click(582, 447);
        Navigator.sleep(500);
        Navigator.type("1");
        Navigator.sleep(400);
        Navigator.key("Return");
        Navigator.sleep(300);

        // "Play Now!" button: game ~(437-577, 465-495), center ~(507, 480).
        ctx.status("clicking Play Now!");
        Navigator.click(507, 480);
        ctx.sleep(10000);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static String generateName() {
        Random rng = new Random();
        return PREFIXES[rng.nextInt(PREFIXES.length)] + SUFFIXES[rng.nextInt(SUFFIXES.length)];
    }
}
