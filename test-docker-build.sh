#!/bin/bash
set -e
cd "$(dirname "$0")"

echo "=== Building JAR ==="
mvn package -q

echo "=== Building Docker image ==="
docker build --platform linux/amd64 -t yppbot .

echo "=== Done ==="
docker images yppbot
