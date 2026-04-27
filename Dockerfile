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
    dpkg-deb --info /tmp/steam.deb > /dev/null && \
    apt-get install -y /tmp/steam.deb && \
    rm /tmp/steam.deb && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

VOLUME /steam-data
EXPOSE 9001

COPY target/yppbot.jar /bot/yppbot.jar
COPY docker/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
