FROM --platform=linux/amd64 ubuntu:22.04
ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && \
    apt-get install -y \
        wget curl ca-certificates unzip \
        xvfb xdotool \
        openjdk-17-jre-headless && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# DepotDownloader — 64-bit, no 32-bit libs needed
RUN mkdir -p /opt/depotdownloader && \
    wget -qO /tmp/dd.zip \
        "https://github.com/SteamRE/DepotDownloader/releases/latest/download/DepotDownloader-linux-x64.zip" && \
    unzip /tmp/dd.zip -d /opt/depotdownloader && \
    chmod +x /opt/depotdownloader/DepotDownloader && \
    rm /tmp/dd.zip

VOLUME /steam-data
EXPOSE 9001

COPY target/yppbot.jar /bot/yppbot.jar
COPY docker/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
