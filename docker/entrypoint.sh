#!/bin/bash

EXPLORE=${EXPLORE:-false}

if [ "$EXPLORE" = "true" ]; then
    STEAM_USER=${STEAM_USER:-Clerical4370}
    STEAM_PASS=${STEAM_PASS:-J9Tp%CkRF5MT9!}
else
    STEAM_USER=${STEAM_USER:?STEAM_USER is required}
    STEAM_PASS=${STEAM_PASS:?STEAM_PASS is required}
fi

BOT_PORT=${BOT_PORT:-9001}
MODE=${MODE:-blacksmithing}

export DISPLAY=:1
export HOME=/root

STATUS_LOG=/tmp/status.log
> "$STATUS_LOG"
status() {
    echo "[status] $1"
    echo "[status] $1" >> "$STATUS_LOG"
}

rm -f /tmp/.X1-lock /tmp/.X11-unix/X1 \
      /root/.steam/steam.pipe /root/.steam/steam.pid 2>/dev/null || true

status "Starting virtual display..."
Xvfb :1 -screen 0 800x600x24 -nolisten tcp &
sleep 2

status "Starting VNC server on port 5900..."
x11vnc -display :1 -nopw -forever -shared -rfbport 5900 -bg -o /tmp/x11vnc.log

xterm -display :1 -bg black -fg lime -fa Monospace -fs 14 \
      -geometry 80x24+0+0 -title "YPPBot Status" \
      -e "tail -f $STATUS_LOG" &

status "Starting Steam ($STEAM_USER)..."
steam -login "$STEAM_USER" "$STEAM_PASS" -silent -no-browser &

status "Waiting for Steam to be ready..."
STEAM_WAITED=0
STEAM_PID=""
while [ $STEAM_WAITED -lt 600 ]; do
    sleep 5
    STEAM_WAITED=$((STEAM_WAITED + 5))
    if [ -e "/root/.steam/steam.pipe" ] && [ -f "/root/.steam/steam.pid" ]; then
        STEAM_PID=$(cat /root/.steam/steam.pid 2>/dev/null)
        if kill -0 "$STEAM_PID" 2>/dev/null; then
            status "Steam running (PID $STEAM_PID) after ${STEAM_WAITED}s"
            break
        fi
    fi
done
if [ -z "$STEAM_PID" ] || ! kill -0 "$STEAM_PID" 2>/dev/null; then
    status "ERROR: Steam failed to start after 600s — exiting"
    exit 1
fi

LOG=/root/.local/share/Steam/logs/connection_log.txt
status "Waiting for Steam CM login (up to 120s)..."
STEAM_CM_WAITED=0
START_SIZE=$(wc -c < "$LOG" 2>/dev/null || echo 0)
while [ $STEAM_CM_WAITED -lt 120 ]; do
    sleep 3
    STEAM_CM_WAITED=$((STEAM_CM_WAITED + 3))
    NEW=$(tail -c +$((START_SIZE + 1)) "$LOG" 2>/dev/null || true)
    if echo "$NEW" | grep -q "RecvMsgClientLogOnResponse.*OK"; then
        status "Steam CM login confirmed after ${STEAM_CM_WAITED}s"
        break
    fi
    FAIL=$(echo "$NEW" | grep -oE "Invalid Password|Invalid Login|Rate Limit Exceeded|Account Disabled|TwoFactor|Steam Guard|Do not reconnect" | head -1)
    if [ -n "$FAIL" ]; then
        status "ERROR: Steam login rejected: $FAIL — exiting"
        exit 1
    fi
done
if ! echo "$(tail -c +$((START_SIZE + 1)) "$LOG" 2>/dev/null)" | grep -q "RecvMsgClientLogOnResponse.*OK"; then
    status "ERROR: Steam CM login timed out after 120s — no response"
    exit 1
fi

GAMEDIR="/root/.local/share/Steam/steamapps/common/Puzzle Pirates"
REAPER="/root/.local/share/Steam/ubuntu12_32/reaper"
JAVA="$GAMEDIR/java_vm/bin/java"
CP="$GAMEDIR/code/config.jar:$GAMEDIR/code/yohoho-boot.jar:$GAMEDIR/code/yoclient-dop.jar:$GAMEDIR/code/lwjgl-natives-linux.jar:$GAMEDIR/code/lwjgl-openal-natives-linux.jar:$GAMEDIR/code/lwjgl-opengl-natives-linux.jar:$GAMEDIR/code/lwjgl-glfw-natives-linux.jar"
export SteamAppId=99910 XDG_RUNTIME_DIR=/tmp/runtime-root
mkdir -p "$XDG_RUNTIME_DIR"
echo "99910" > "$GAMEDIR/steam_appid.txt"

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
    status "Getdown finished after ${WAITED}s"
    pkill -f "YoApp\|yohoho-boot" 2>/dev/null || true
    sleep 3
fi

status "Launching Puzzle Pirates..."
SteamClientLaunch=1 SteamEnv=1 \
"$REAPER" SteamLaunch AppId=99910 -- \
    "$JAVA" \
        -classpath "$CP" \
        -Dcom.threerings.getdown=true -Xmx512M \
        -Djava.library.path="$GAMEDIR/native21" \
        -Dresource_dir="$GAMEDIR/rsrc" \
        -Ddevclient=false -Dappdir="$GAMEDIR" \
        -Dswing.aatext=true -XX:+EnableDynamicAgentLoading -Dsun.java2d.xrender=true \
        com.threerings.yohoho.client.YoApp &

status "Waiting for game window..."
WAITED=0
while [ $WAITED -lt 900 ]; do
    sleep 3
    WAITED=$((WAITED + 3))
    WIN=$(xdotool search --name "Puzzle Pirates" 2>/dev/null | head -1 || true)
    if [ -n "$WIN" ]; then
        break
    fi
done

if [ -z "$WIN" ]; then
    status "Game did not open within 900s"
    exit 1
fi

if [ "$EXPLORE" = "true" ]; then
    status "Game window found. Explore mode — bot not started. Use docker exec for manual testing."
    tail -f /dev/null
else
    status "Game window found. Starting bot..."
    exec java -jar /bot/yppbot.jar \
        --window "Puzzle Pirates" \
        --mode   "$MODE" \
        --port   "$BOT_PORT"
fi
