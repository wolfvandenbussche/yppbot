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

# ── Resource bundle download (first run only) ──────────────────────────────────
# rsrc bundles are large (~500 MB); downloaded once to a volume and reused.

RSRC_MARKER=/game-data/rsrc/.downloaded
if [ ! -f "$RSRC_MARKER" ]; then
    status "First run: downloading game resources via Getdown (this may take a while)..."
    mkdir -p /game-data/rsrc-tmp
    cp /game/getdown.txt /game-data/rsrc-tmp/getdown.txt
    java -jar /game/getdown.jar /game-data/rsrc-tmp 2>&1 | grep -v "^$" || true
    mkdir -p /game-data/rsrc
    cp -r /game-data/rsrc-tmp/rsrc/. /game-data/rsrc/ 2>/dev/null || true
    touch "$RSRC_MARKER"
    rm -rf /game-data/rsrc-tmp
    status "Resources downloaded."
fi

# ── Launch game ────────────────────────────────────────────────────────────────

status "Launching Puzzle Pirates (ocean: $YPP_OCEAN)..."
java \
    -Xmx512M \
    -Djava.library.path=/game/native21 \
    -Dresource_dir=/game-data/rsrc \
    -Dappdir=/game \
    -Dswing.aatext=true \
    -Dsun.java2d.xrender=true \
    -cp "/game/code/*" \
    com.threerings.yohoho.client.YoApp \
    > /game-data/game.log 2>&1 &
GAME_PID=$!

status "Waiting for game login screen..."
WAITED=0
LOGIN_TIMEOUT=120
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
        cat /game-data/game.log
        exit 1
    fi
done

if [ "$LOGGED_IN" = "false" ]; then
    status "Game did not open within ${LOGIN_TIMEOUT}s"
    cat /game-data/game.log
    exit 1
fi

status "Game window found"

# TODO: automate login with YPP_USER / YPP_PASS via xdotool
# For now, log the credentials so we can verify the window appeared
status "YPP_USER=$YPP_USER ocean=$YPP_OCEAN"

# ── Bot ────────────────────────────────────────────────────────────────────────

status "Starting bot..."
exec java -jar /bot/yppbot.jar \
    --window "Puzzle Pirates" \
    --mode   "$MODE" \
    --port   "$BOT_PORT"
