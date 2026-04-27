FROM --platform=linux/amd64 ubuntu:22.04
ENV DEBIAN_FRONTEND=noninteractive

RUN dpkg --add-architecture i386 && \
    apt-get update && \
    apt-get install -y \
        wget curl ca-certificates unzip \
        software-properties-common && \
    add-apt-repository -y multiverse && \
    apt-get update && \
    echo steam steam/question select "I AGREE" | debconf-set-selections && \
    echo steam steam/license note '' | debconf-set-selections && \
    apt-get install -y \
        xvfb xdotool scrot \
        libxi6 libxrender1 libxtst6 libxext6 libx11-6 libxrandr2 \
        openjdk-17-jre-headless \
        steamcmd \
        steam && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

VOLUME /steam-data
EXPOSE 9001

COPY target/yppbot.jar /bot/yppbot.jar
COPY docker/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
