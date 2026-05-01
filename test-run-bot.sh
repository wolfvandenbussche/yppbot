#!/bin/bash
cd "$(dirname "$0")"

CONTAINER="yppbot-clerical4370"
PORT=9001

docker rm -f "$CONTAINER" 2>/dev/null || true

echo "=== Starting container ==="
docker run -d \
    --name "$CONTAINER" \
    --platform linux/amd64 \
    --privileged \
    -p "$PORT:9001" \
    -e STEAM_USER="Clerical4370" \
    -e "STEAM_PASS=J9Tp%CkRF5MT9!" \
    -e BOT_PORT=9001 \
    -e MODE=blacksmithing \
    yppbot

echo "=== Streaming logs (Ctrl-C to detach) ==="
docker logs -f "$CONTAINER" 2>&1
