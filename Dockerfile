# hadolint ignore=DL3029
FROM --platform=linux/amd64 ubuntu:22.04
ENV DEBIAN_FRONTEND=noninteractive

RUN dpkg --add-architecture i386 && \
    apt-get update && \
    apt-get install -y software-properties-common && \
    add-apt-repository -y multiverse && \
    apt-get update && \
    echo steam steam/question select "I AGREE" | debconf-set-selections && \
    echo steam steam/license note '' | debconf-set-selections && \
    apt-get install -y \
        xvfb xdotool scrot \
        libxi6 libxrender1 libxtst6 libxext6 libx11-6 libxrandr2 \
        openjdk-17-jre-headless \
        steam && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Pre-bootstrap Steam during build (requires real x86_64 — runs fine on GitHub Actions).
# The 32-bit bootstrapper downloads the full 64-bit Steam client to /opt/steam-client.
# At container startup the entrypoint symlinks ~/.steam here, skipping the bootstrapper.
RUN mkdir -p /opt/steam-client && \
    Xvfb :99 -screen 0 1024x768x16 -nolisten tcp & \
    HOME=/opt/steam-client DISPLAY=:99 STEAM_RUNTIME=0 \
    /usr/games/steam -no-cef-sandbox & STEAM_PID=$! && \
    for i in $(seq 60); do \
        sleep 5; \
        [ -f /opt/steam-client/.steam/debian-installation/steam.sh ] && echo "Bootstrap done" && break; \
        echo "  waiting... ($i/60)"; \
    done; \
    kill $STEAM_PID 2>/dev/null || true; \
    pkill Xvfb 2>/dev/null || true; \
    ls /opt/steam-client/.steam/debian-installation/steam.sh

VOLUME /steam-data
EXPOSE 9001

COPY target/yppbot.jar /bot/yppbot.jar
COPY docker/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
