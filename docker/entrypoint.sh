#!/bin/bash

YPP_USER=${YPP_USER:?YPP_USER is required}
YPP_PASS=${YPP_PASS:?YPP_PASS is required}
YPP_OCEAN=${YPP_OCEAN:-cerulean}
BOT_PORT=${BOT_PORT:-9001}
MODE=${MODE:-blacksmithing}

export DISPLAY=:1

status() { echo "[status] $1"; }

# ── Virtual display ────────────────────────────────────────────────────────────

status "Starting virtual display..."
Xvfb :1 -screen 0 1920x1080x24 -nolisten tcp &
sleep 2

# ── Set up game client directory ───────────────────────────────────────────────
# Getdown needs a single appdir containing getdown.txt, code/, native21/, rsrc/.
# We keep code/ and native21/ baked in the image; rsrc/ lives on the volume.

CLIENT_DIR=/game-data/client
mkdir -p "$CLIENT_DIR"

# Copy immutable files from image (fast, already present)
cp /game/getdown.txt "$CLIENT_DIR/getdown.txt"
cp /game/getdown.jar "$CLIENT_DIR/getdown.jar"

mkdir -p "$CLIENT_DIR/code"
cp /game/code/* "$CLIENT_DIR/code/"

mkdir -p "$CLIENT_DIR/native21"
cp /game/native21/* "$CLIENT_DIR/native21/"

# rsrc is large; download once and reuse from volume
if [ ! -f "$CLIENT_DIR/rsrc/.downloaded" ]; then
    status "First run: Getdown will download game resources (~500 MB)..."
fi

# ── Launch via Getdown ─────────────────────────────────────────────────────────
# Getdown verifies all files, downloads anything missing, then launches YoApp
# with the exact JVM args from getdown.txt (handles %APPDIR% substitution).

status "Launching game via Getdown..."
java -jar "$CLIENT_DIR/getdown.jar" "$CLIENT_DIR" \
    > /game-data/getdown.log 2>&1 &
GAME_PID=$!

# Mark rsrc as downloaded after first successful launch setup
touch "$CLIENT_DIR/rsrc/.downloaded" 2>/dev/null || true

status "Waiting for game window..."
WAITED=0
LOGIN_TIMEOUT=300
LOGGED_IN=false

while [ $WAITED -lt $LOGIN_TIMEOUT ]; do
    sleep 3
    WAITED=$((WAITED + 3))

    WIN=$(xdotool search --name "Puzzle Pirates" 2>/dev/null | head -1 || true)
    if [ -n "$WIN" ]; then
        LOGGED_IN=true
        break
    fi

    if ! kill -0 "$GAME_PID" 2>/dev/null; then
        status "Game exited unexpectedly"
        cat /game-data/getdown.log
        exit 1
    fi
done

if [ "$LOGGED_IN" = "false" ]; then
    status "Game did not open within ${LOGIN_TIMEOUT}s"
    cat /game-data/getdown.log
    exit 1
fi

status "Game window found. Logging in as $YPP_USER..."

# TODO: automate login with xdotool

# ── Bot ────────────────────────────────────────────────────────────────────────

status "Starting bot..."
exec java -jar /bot/yppbot.jar \
    --window "Puzzle Pirates" \
    --mode   "$MODE" \
    --port   "$BOT_PORT"
