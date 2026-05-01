#!/bin/bash
# Rebuilds yppbot-steam-base — run this when Steam, game files, or OS packages change.
# Takes ~15 min total (steamcmd download + steam client download).
# Normal bot rebuilds use build.sh which skips this entirely.
set -e
cd "$(dirname "$0")"

STEAM_USER=${STEAM_USER:-Clerical4370}
STEAM_PASS=${STEAM_PASS:-"J9Tp%CkRF5MT9!"}

echo "=== Step 1: Building image with OS packages and game files ==="
docker build -f Dockerfile.base \
    --build-arg STEAM_USER="$STEAM_USER" \
    --build-arg "STEAM_PASS=$STEAM_PASS" \
    --target runtime-prereqs \
    -t yppbot-prereqs . 2>&1 || \
docker build -f Dockerfile.base \
    --build-arg STEAM_USER="$STEAM_USER" \
    --build-arg "STEAM_PASS=$STEAM_PASS" \
    -t yppbot-prereqs .

echo "=== Step 2: Running container so Steam downloads its client (~500 MB) ==="
docker rm -f yppbot-base-setup 2>/dev/null || true
docker run -d \
    --name yppbot-base-setup \
    --platform linux/amd64 \
    --privileged \
    -e STEAM_USER="$STEAM_USER" \
    -e "STEAM_PASS=$STEAM_PASS" \
    -e BOT_PORT=9001 \
    -e MODE=blacksmithing \
    yppbot-prereqs

echo "Waiting for Steam client download (steamui.so + reaper)..."
WAITED=0
while [ $WAITED -lt 600 ]; do
    sleep 10
    WAITED=$((WAITED + 10))
    if docker exec yppbot-base-setup \
            test -f /root/.local/share/Steam/ubuntu12_32/steamui.so 2>/dev/null && \
       docker exec yppbot-base-setup \
            test -f /root/.local/share/Steam/ubuntu12_32/reaper 2>/dev/null; then
        echo "Steam client ready after ${WAITED}s"
        break
    fi
    echo "  still waiting... (${WAITED}s)"
done

if ! docker exec yppbot-base-setup \
        test -f /root/.local/share/Steam/ubuntu12_32/steamui.so 2>/dev/null; then
    echo "ERROR: Steam client did not download within ${WAITED}s"
    docker rm -f yppbot-base-setup
    exit 1
fi

echo "Waiting for game code download via getdown (code/yohoho-boot.jar)..."
GAMEDIR="/root/.local/share/Steam/steamapps/common/Puzzle Pirates"
JAVA="$GAMEDIR/java_vm/bin/java"
docker exec yppbot-base-setup bash -c "
    export DISPLAY=:1 HOME=/root
    cd '$GAMEDIR'
    '$JAVA' -jar '$GAMEDIR/getdown-dop.jar' '$GAMEDIR' >/dev/null 2>&1 &
"
WAITED=0
while [ $WAITED -lt 600 ]; do
    sleep 10
    WAITED=$((WAITED + 10))
    if docker exec yppbot-base-setup \
            test -f "$GAMEDIR/code/yohoho-boot.jar" 2>/dev/null; then
        echo "Game code ready after ${WAITED}s"
        break
    fi
    echo "  still waiting for getdown... (${WAITED}s)"
done

if ! docker exec yppbot-base-setup \
        test -f "$GAMEDIR/code/yohoho-boot.jar" 2>/dev/null; then
    echo "ERROR: Game code not downloaded within ${WAITED}s"
    docker rm -f yppbot-base-setup
    exit 1
fi

echo "=== Step 3: Committing as yppbot-steam-base ==="
docker stop yppbot-base-setup
docker commit yppbot-base-setup yppbot-steam-base
docker rm yppbot-base-setup
docker rmi yppbot-prereqs 2>/dev/null || true

echo "=== yppbot-steam-base ready — run build.sh to build the bot ==="
