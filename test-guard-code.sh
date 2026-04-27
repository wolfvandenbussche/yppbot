#!/bin/bash
cd "$(dirname "$0")"

CONTAINER="yppbot-elasticroidster"
VOLUME="yppbot-elasticroidster-data"
PORT=9001

echo "=== Removing old container ==="
docker rm -f "$CONTAINER" 2>/dev/null || true

echo "=== Starting with Steam Guard code ==="
docker run -d \
    --name "$CONTAINER" \
    --platform linux/amd64 \
    -v "$VOLUME:/steam-data" \
    -p "$PORT:9001" \
    -e STEAM_USER="ElasticRoidster" \
    -e "STEAM_PASS=xKmM*%97ALbcsD" \
    -e STEAM_GUARD_CODE="KQN3T" \
    -e BOT_PORT=9001 \
    -e MODE=blacksmithing \
    yppbot

echo "=== Streaming logs ==="
docker logs -f "$CONTAINER" 2>&1
