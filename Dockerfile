FROM --platform=linux/amd64 ubuntu:22.04
ENV DEBIAN_FRONTEND=noninteractive

RUN dpkg --add-architecture i386 && \
    apt-get update && \
    apt-get install -y \
        wget curl ca-certificates unzip \
        xvfb xdotool scrot \
        libxi6 libxrender1 libxtst6 libxext6 libx11-6 libxrandr2 \
        openjdk-17-jre-headless && \
    wget --retry-connrefused --tries=5 --waitretry=5 \
        "https://cdn.akamai.steamstatic.com/client/installer/steam_latest.deb" \
        -O /tmp/steam.deb && \
    apt-get install -y /tmp/steam.deb && \
    rm /tmp/steam.deb && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Run the Steam bootstrapper now (during build on x86_64) so the 32-bit installer
# never needs to run at container startup. Steam downloads itself to /opt/steam-client.
RUN mkdir -p /opt/steam-client && \
    Xvfb :99 -screen 0 1024x768x16 -nolisten tcp & \
    HOME=/opt/steam-client DISPLAY=:99 STEAM_RUNTIME=0 \
    /usr/games/steam -no-cef-sandbox & STEAM_PID=$! && \
    echo "Waiting for Steam to finish bootstrapping..." && \
    for i in $(seq 60); do \
        sleep 5; \
        [ -f /opt/steam-client/.steam/debian-installation/steam.sh ] && echo "Bootstrap done" && break; \
        echo "  waiting... ($i/60)"; \
    done && \
    kill $STEAM_PID 2>/dev/null; pkill Xvfb 2>/dev/null; \
    ls /opt/steam-client/.steam/debian-installation/steam.sh && echo "Steam pre-installed OK"

VOLUME /steam-data
EXPOSE 9001

COPY target/yppbot.jar /bot/yppbot.jar
COPY docker/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
