#!/bin/bash

STEAM_USER=${STEAM_USER:?STEAM_USER is required}
STEAM_PASS=${STEAM_PASS:?STEAM_PASS is required}
STEAM_GUARD_CODE=${STEAM_GUARD_CODE:-}
GAME_APPID=${GAME_APPID:-99910}
BOT_PORT=${BOT_PORT:-9001}
MODE=${MODE:-blacksmithing}

export HOME=/steam-data/home
mkdir -p "$HOME"

status() { echo "[status] $1"; }

# ── Virtual display ────────────────────────────────────────────────────────────

status "Starting virtual display..."
Xvfb :1 -screen 0 1920x1080x24 -nolisten tcp &
export DISPLAY=:1
sleep 2

# ── Steam login ────────────────────────────────────────────────────────────────

status "Starting Steam..."
export STEAM_RUNTIME=0
steam -no-cef-sandbox -login "$STEAM_USER" "$STEAM_PASS" \
    > /steam-data/steam.log 2>&1 &
STEAM_PID=$!

LOGINUSERS="$HOME/.steam/steam/config/loginusers.vdf"
LOGIN_TIMEOUT=120
WAITED=0
LOGGED_IN=false

while [ $WAITED -lt $LOGIN_TIMEOUT ]; do
    sleep 3
    WAITED=$((WAITED + 3))

    # Steam Guard dialog (native X11 window Steam shows on first login)
    GUARD_WIN=$(xdotool search --name "Steam Guard" 2>/dev/null || true)
    if [ -n "$GUARD_WIN" ]; then
        if [ -n "$STEAM_GUARD_CODE" ]; then
            status "Entering Steam Guard code..."
            xdotool windowfocus "$GUARD_WIN"
            xdotool type --delay 150 "$STEAM_GUARD_CODE"
            xdotool key Return
            STEAM_GUARD_CODE=""
        else
            status "STEAM_GUARD_REQUIRED"
            kill "$STEAM_PID" 2>/dev/null || true
            exit 1
        fi
    fi

    # Login success: loginusers.vdf is written once Steam has an active session
    if [ -f "$LOGINUSERS" ] && grep -qi "\"$STEAM_USER\"" "$LOGINUSERS" 2>/dev/null; then
        LOGGED_IN=true
        break
    fi

    if ! kill -0 "$STEAM_PID" 2>/dev/null; then
        status "Steam exited unexpectedly"
        cat /steam-data/steam.log
        exit 1
    fi
done

if [ "$LOGGED_IN" = "false" ]; then
    status "Steam login timed out after ${LOGIN_TIMEOUT}s"
    cat /steam-data/steam.log
    exit 1
fi

status "Steam logged in"

# ── Game ───────────────────────────────────────────────────────────────────────

APPMANIFEST="$HOME/.steam/steam/steamapps/appmanifest_${GAME_APPID}.acf"
if [ ! -f "$APPMANIFEST" ]; then
    status "Installing game $GAME_APPID (first run, may take a while)..."
fi

status "Launching game $GAME_APPID..."
steam -applaunch "$GAME_APPID"

# Give the game time to start before the bot tries to find its window
status "Waiting for game to start..."
sleep 20

# ── Bot ────────────────────────────────────────────────────────────────────────

status "Starting bot..."
exec java -jar /bot/yppbot.jar \
    --window "Puzzle Pirates" \
    --mode   "$MODE" \
    --port   "$BOT_PORT"
