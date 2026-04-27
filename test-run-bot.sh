#!/bin/bash
cd "$(dirname "$0")"

CONTAINER="yppbot-elasticroidster"
VOLUME="yppbot-elasticroidster-data"
PORT=9001

docker rm -f "$CONTAINER" 2>/dev/null || true
docker volume create "$VOLUME" 2>/dev/null || true

echo "=== Starting container ==="
docker run -d \
    --name "$CONTAINER" \
    --platform linux/amd64 \
    -v "$VOLUME:/steam-data" \
    -p "$PORT:9001" \
    -e STEAM_USER="ElasticRoidster" \
    -e "STEAM_PASS=xKmM*%97ALbcsD" \
    -e BOT_PORT=9001 \
    -e MODE=blacksmithing \
    yppbot

echo "=== Streaming logs (will stop after 3 minutes or when bot is ready) ==="
timeout 180 docker logs -f "$CONTAINER" 2>&1 || true

echo ""
echo "=== Final container status ==="
docker inspect -f '{{.State.Status}}' "$CONTAINER"
echo ""
echo "=== Last 20 lines ==="
docker logs --tail 20 "$CONTAINER" 2>&1
