#!/bin/bash

STEAM_USER=${STEAM_USER:?STEAM_USER is required}
STEAM_PASS=${STEAM_PASS:?STEAM_PASS is required}
BOT_PORT=${BOT_PORT:-9001}
MODE=${MODE:-blacksmithing}

export DISPLAY=:1
export HOME=/root

status() { echo "[status] $1"; }

# Clean up stale lock/pipe files left by docker commit so Xvfb and Steam start fresh
rm -f /tmp/.X1-lock /tmp/.X11-unix/X1 \
      /root/.steam/steam.pipe /root/.steam/steam.pid 2>/dev/null || true

# ── Virtual display ────────────────────────────────────────────────────────────

status "Starting virtual display..."
Xvfb :1 -screen 0 1920x1080x24 -nolisten tcp &
sleep 2

status "Starting VNC server on port 5900..."
x11vnc -display :1 -nopw -forever -shared -rfbport 5900 -bg -o /tmp/x11vnc.log

# ── Steam ──────────────────────────────────────────────────────────────────────

status "Starting Steam ($STEAM_USER)..."
steam -login "$STEAM_USER" "$STEAM_PASS" -silent -no-browser &

# Wait for Steam to fully initialise — it self-updates on first run which takes a while.
# A reliable ready signal is the Steam IPC pipe or the steamapps directory appearing.
status "Waiting for Steam to be ready (first run downloads ~500 MB, may take several minutes)..."
STEAM_WAITED=0
while [ $STEAM_WAITED -lt 600 ]; do
    sleep 5
    STEAM_WAITED=$((STEAM_WAITED + 5))
    # Pipe must exist AND steam process must still be alive (stale pipe = crashed steam)
    if [ -e "/root/.steam/steam.pipe" ] && [ -f "/root/.steam/steam.pid" ]; then
        STEAM_PID=$(cat /root/.steam/steam.pid 2>/dev/null)
        if kill -0 "$STEAM_PID" 2>/dev/null; then
            status "Steam running (PID $STEAM_PID) after ${STEAM_WAITED}s"
            break
        fi
    fi
done
# Let Steam log in to the Steam Network before launching the game
sleep 20

# ── Launch game ────────────────────────────────────────────────────────────────

GAMEDIR="/root/.local/share/Steam/steamapps/common/Puzzle Pirates"
REAPER="/root/.local/share/Steam/ubuntu12_32/reaper"
JAVA="$GAMEDIR/java_vm/bin/java"
CP="$GAMEDIR/code/config.jar:$GAMEDIR/code/yohoho-boot.jar:$GAMEDIR/code/yoclient-dop.jar:$GAMEDIR/code/lwjgl-natives-linux.jar:$GAMEDIR/code/lwjgl-openal-natives-linux.jar:$GAMEDIR/code/lwjgl-opengl-natives-linux.jar:$GAMEDIR/code/lwjgl-glfw-natives-linux.jar"
export SteamAppId=99910 XDG_RUNTIME_DIR=/tmp/runtime-root
mkdir -p "$XDG_RUNTIME_DIR"

# Write steam_appid.txt so SteamAPI_Init() can find the app ID
echo "99910" > "$GAMEDIR/steam_appid.txt"

# Download game code if not yet present (getdown fetches code/*.jar from PP servers).
# We wait for getdown to EXIT — it exits naturally after download + fork of YoApp.
if [ ! -f "$GAMEDIR/code/yohoho-boot.jar" ] || [ ! -s "$GAMEDIR/code/yohoho-boot.jar" ]; then
    status "Game code missing — running getdown to download it (one-time, ~300 MB)..."
    cd "$GAMEDIR"
    "$JAVA" -jar "$GAMEDIR/getdown-dop.jar" "$GAMEDIR" >/dev/null 2>&1 &
    GETDOWN_PID=$!
    WAITED=0
    while [ $WAITED -lt 600 ] && kill -0 $GETDOWN_PID 2>/dev/null; do
        sleep 10; WAITED=$((WAITED + 10))
        status "  getdown: ${WAITED}s elapsed..."
    done
    status "Getdown finished after ${WAITED}s — killing any YoApp it launched"
    pkill -f "YoApp\|yohoho-boot" 2>/dev/null || true
    sleep 3
fi

if [ -f "$REAPER" ] && [ -f "$GAMEDIR/code/yohoho-boot.jar" ]; then
    status "Launching Puzzle Pirates via reaper..."
    "$REAPER" SteamLaunch AppId=99910 -- \
        "$JAVA" \
            -classpath "$CP" \
            -Dcom.threerings.getdown=true -Xmx512M \
            -Djava.library.path="$GAMEDIR/native21" \
            -Dresource_dir="$GAMEDIR/rsrc" \
            -Ddevclient=false -Dappdir="$GAMEDIR" \
            -Dswing.aatext=true -XX:+EnableDynamicAgentLoading -Dsun.java2d.xrender=true \
            com.threerings.yohoho.client.YoApp &
else
    status "Falling back to steam -applaunch"
    steam -applaunch 99910 &
fi

status "Waiting for game window..."
WAITED=0
# First run: Steam needs to download the game (~500 MB) before the window appears
LOGIN_TIMEOUT=900

while [ $WAITED -lt $LOGIN_TIMEOUT ]; do
    sleep 3
    WAITED=$((WAITED + 3))

    WIN=$(xdotool search --name "Puzzle Pirates" 2>/dev/null | head -1 || true)
    if [ -n "$WIN" ]; then
        break
    fi
done

if [ -z "$WIN" ]; then
    status "Game did not open within ${LOGIN_TIMEOUT}s"
    exit 1
fi

status "Game window found. Starting bot..."
exec java -jar /bot/yppbot.jar \
    --window "Puzzle Pirates" \
    --mode   "$MODE" \
    --port   "$BOT_PORT"
