#!/bin/bash

STEAM_USER=${STEAM_USER:?STEAM_USER is required}
STEAM_PASS=${STEAM_PASS:?STEAM_PASS is required}
STEAM_GUARD_CODE=${STEAM_GUARD_CODE:-}
GAME_APPID=${GAME_APPID:-99910}
BOT_PORT=${BOT_PORT:-9001}
MODE=${MODE:-blacksmithing}
GAME_DIR=/steam-data/game
CRED_DIR=/steam-data/credentials

mkdir -p "$GAME_DIR" "$CRED_DIR"

# Status file that the monitor can read from docker logs
status() { echo "[status] $1"; }

status "Starting virtual display..."
Xvfb :1 -screen 0 1920x1080x24 -nolisten tcp &
export DISPLAY=:1
sleep 2

# Build DepotDownloader args
DD_ARGS=(
    -app "$GAME_APPID"
    -username "$STEAM_USER"
    -password "$STEAM_PASS"
    -dir "$GAME_DIR"
    -remember-password
)

status "Downloading game (app $GAME_APPID)..."

# Point HOME at the persistent volume so DepotDownloader saves its login key
# to ~/.config/DepotDownloader/account.config inside the volume (survives container restarts)
export HOME="$CRED_DIR"
mkdir -p "$CRED_DIR"
cd "$CRED_DIR"
if [ -n "$STEAM_GUARD_CODE" ]; then
    echo "$STEAM_GUARD_CODE" | /opt/depotdownloader/DepotDownloader "${DD_ARGS[@]}"
else
    /opt/depotdownloader/DepotDownloader "${DD_ARGS[@]}"
fi
DD_EXIT=$?

if [ $DD_EXIT -ne 0 ]; then
    status "STEAM_GUARD_REQUIRED"
    echo "[setup] Check your email and restart with STEAM_GUARD_CODE env var set."
    exit $DD_EXIT
fi

status "Game ready, launching..."

JAVA="$GAME_DIR/java_vm/bin/java"
GETDOWN="$GAME_DIR/getdown-dop.jar"

if [ -f "$GETDOWN" ] && [ -f "$JAVA" ]; then
    status "Launching via getdown (will download and start client)..."
    chmod +x "$JAVA"
    "$JAVA" -jar "$GETDOWN" "$GAME_DIR" &
else
    # Fallback: look for a shell launcher
    LAUNCHER=""
    for candidate in \
        "$GAME_DIR/PuzzlePirates.sh" \
        "$GAME_DIR/puzzlepirates.sh" \
        "$GAME_DIR/start.sh" \
        "$GAME_DIR/run.sh" \
        "$GAME_DIR/launch.sh"; do
        [ -f "$candidate" ] && LAUNCHER="$candidate" && break
    done
    [ -z "$LAUNCHER" ] && LAUNCHER=$(find "$GAME_DIR" -maxdepth 2 -name "*.sh" -type f | head -1)

    if [ -n "$LAUNCHER" ]; then
        status "Launching $LAUNCHER"
        chmod +x "$LAUNCHER"
        bash "$LAUNCHER" &
    else
        status "ERROR: no launcher found in $GAME_DIR"
        find "$GAME_DIR" -maxdepth 3 -type f | head -20
        exit 1
    fi
fi

status "starting bot"
exec java -jar /bot/yppbot.jar \
    --window "Puzzle Pirates" \
    --mode   "$MODE" \
    --port   "$BOT_PORT"
