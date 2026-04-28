FROM ubuntu:22.04
ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && \
    apt-get install -y \
        xvfb xdotool scrot curl \
        openjdk-21-jre-headless \
        libxi6 libxrender1 libxtst6 libxext6 libx11-6 libxrandr2 \
        libopenal1 \
        libgl1 libglu1-mesa \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

# Download the Puzzle Pirates code jars and Linux natives (no Steam required)
ENV YPP_BASE=https://gamemedia.puzzlepirates.com/ec2/yoclient/20260419161103
RUN mkdir -p /game/code /game/native21 && \
    curl -sL "$YPP_BASE/code/config.jar"                    -o /game/code/config.jar && \
    curl -sL "$YPP_BASE/code/yohoho-boot.jar"               -o /game/code/yohoho-boot.jar && \
    curl -sL "$YPP_BASE/code/yoclient-dop.jar"              -o /game/code/yoclient-dop.jar && \
    curl -sL "$YPP_BASE/code/lwjgl-natives-linux.jar"       -o /game/code/lwjgl-natives-linux.jar && \
    curl -sL "$YPP_BASE/code/lwjgl-openal-natives-linux.jar" -o /game/code/lwjgl-openal-natives-linux.jar && \
    curl -sL "$YPP_BASE/code/lwjgl-opengl-natives-linux.jar" -o /game/code/lwjgl-opengl-natives-linux.jar && \
    curl -sL "$YPP_BASE/code/lwjgl-glfw-natives-linux.jar"  -o /game/code/lwjgl-glfw-natives-linux.jar && \
    curl -sL "$YPP_BASE/native21/libfroth.so"               -o /game/native21/libfroth.so

# Copy getdown launcher so we can fetch rsrc bundles on first run
COPY game/getdown.jar /game/getdown.jar
# Copy current getdown.txt so getdown knows which version/files to download
RUN curl -sL "$YPP_BASE/getdown.txt" -o /game/getdown.txt

VOLUME /game-data
EXPOSE 9001

COPY target/yppbot.jar /bot/yppbot.jar
COPY docker/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
