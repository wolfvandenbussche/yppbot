# ── Build stage ────────────────────────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /build
COPY pom.xml .
COPY src/ src/
RUN mvn package -q

# ── Runtime stage ──────────────────────────────────────────────────────────────
# yppbot-steam-base has all OS packages, game files, and the full Steam client
# already downloaded. Rebuild it with build-base.sh when Steam or game files change.
FROM yppbot-steam-base

EXPOSE 9001

COPY --from=builder /build/target/yppbot.jar /bot/yppbot.jar
COPY docker/entrypoint.sh /entrypoint.sh
RUN sed -i 's/\r//' /entrypoint.sh && chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
