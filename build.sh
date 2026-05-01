#!/bin/bash
cd "$(dirname "$0")"

docker build \
    --build-arg STEAM_USER="Clerical4370" \
    --build-arg "STEAM_PASS=J9Tp%CkRF5MT9!" \
    -t yppbot .
