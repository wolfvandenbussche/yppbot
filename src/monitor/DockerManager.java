package monitor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DockerManager {

    public static final String IMAGE = "yppbot";

    // -------------------------------------------------------------------------

    public static boolean isAvailable() {
        try {
            return run(null, "docker", "info") == 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean imageExists() {
        try {
            Process p = new ProcessBuilder("docker", "images", "-q", IMAGE)
                    .redirectErrorStream(true).start();
            String out = new BufferedReader(new InputStreamReader(p.getInputStream())).readLine();
            p.waitFor();
            return out != null && !out.trim().isEmpty();
        } catch (Exception e) { return false; }
    }

    /** Builds the Docker image, streaming output to logger. */
    public static void buildImage(Consumer<String> logger) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("docker", "build", "--platform", "linux/amd64", "-t", IMAGE, ".")
                .redirectErrorStream(true);
        Process p = pb.start();
        try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line;
            while ((line = r.readLine()) != null) logger.accept(line);
        }
        int code = p.waitFor();
        if (code != 0) throw new RuntimeException("docker build failed (exit " + code + ")");
    }

    /**
     * Creates and starts a new bot container.
     * Returns the container name.
     * Port on the host maps to 9001 inside the container.
     */
    public static String createAndStart(String displayName, String steamUser, String steamPass,
                                        int hostPort, String mode) throws Exception {
        String name   = containerName(displayName);
        String volume = name + "-data";

        // Create persistent volume for Steam data
        run(null, "docker", "volume", "create", volume);

        List<String> cmd = new ArrayList<>();
        cmd.add("docker"); cmd.add("run"); cmd.add("-d");
        cmd.add("--name");    cmd.add(name);
        cmd.add("-v");        cmd.add(volume + ":/steam-data");
        cmd.add("-p");        cmd.add(hostPort + ":9001");
        cmd.add("-e");        cmd.add("STEAM_USER=" + steamUser);
        cmd.add("-e");        cmd.add("STEAM_PASS=" + steamPass);
        cmd.add("-e");        cmd.add("BOT_PORT=9001");
        cmd.add("-e");        cmd.add("MODE=" + mode);
        // Linux/amd64 for Steam compatibility (needed on Apple Silicon)
        cmd.add("--platform"); cmd.add("linux/amd64");
        cmd.add(IMAGE);

        Process p = new ProcessBuilder(cmd).redirectErrorStream(true).start();
        String out = new BufferedReader(new InputStreamReader(p.getInputStream())).readLine();
        int code = p.waitFor();
        if (code != 0) throw new RuntimeException("docker run failed: " + out);

        return name;
    }

    public static void stop(String containerName) throws Exception {
        run(null, "docker", "stop", containerName);
    }

    public static void start(String containerName) throws Exception {
        run(null, "docker", "start", containerName);
    }

    public static void remove(String containerName) throws Exception {
        run(null, "docker", "rm", "-f", containerName);
    }

    public static boolean isRunning(String containerName) {
        try {
            Process p = new ProcessBuilder(
                    "docker", "inspect", "-f", "{{.State.Running}}", containerName)
                    .redirectErrorStream(true).start();
            String out = new BufferedReader(new InputStreamReader(p.getInputStream())).readLine();
            p.waitFor();
            return "true".equals(out != null ? out.trim() : "");
        } catch (Exception e) { return false; }
    }

    public static boolean containerExists(String containerName) {
        try {
            return run(null, "docker", "inspect", containerName) == 0;
        } catch (Exception e) { return false; }
    }

    /** Returns the last 100 lines of a container's logs (stdout+stderr). */
    public static String getLogs(String containerName) {
        try {
            Process p = new ProcessBuilder("docker", "logs", "--tail", "100", containerName)
                    .redirectErrorStream(true).start();
            String out = new String(p.getInputStream().readAllBytes());
            p.waitFor();
            return out;
        } catch (Exception e) { return ""; }
    }

    /** Returns true if the container's logs contain the Steam Guard sentinel. */
    public static boolean hasGuardRequired(String containerName) {
        return getLogs(containerName).contains("[status] STEAM_GUARD_REQUIRED");
    }

    /**
     * Removes the stopped container and starts a new one with the Steam Guard code.
     * The persistent volume is reused so no re-download is needed after auth succeeds.
     */
    public static String restartWithGuardCode(String displayName, String steamUser, String steamPass,
                                              int hostPort, String mode, String guardCode) throws Exception {
        String name   = containerName(displayName);
        String volume = name + "-data";

        run(null, "docker", "rm", "-f", name);

        List<String> cmd = new ArrayList<>();
        cmd.add("docker"); cmd.add("run"); cmd.add("-d");
        cmd.add("--name");     cmd.add(name);
        cmd.add("-v");         cmd.add(volume + ":/steam-data");
        cmd.add("-p");         cmd.add(hostPort + ":9001");
        cmd.add("-e");         cmd.add("STEAM_USER=" + steamUser);
        cmd.add("-e");         cmd.add("STEAM_PASS=" + steamPass);
        cmd.add("-e");         cmd.add("STEAM_GUARD_CODE=" + guardCode);
        cmd.add("-e");         cmd.add("BOT_PORT=9001");
        cmd.add("-e");         cmd.add("MODE=" + mode);
        cmd.add("--platform"); cmd.add("linux/amd64");
        cmd.add(IMAGE);

        Process p = new ProcessBuilder(cmd).redirectErrorStream(true).start();
        String out = new BufferedReader(new InputStreamReader(p.getInputStream())).readLine();
        int code = p.waitFor();
        if (code != 0) throw new RuntimeException("docker run failed: " + out);
        return name;
    }

    // -------------------------------------------------------------------------

    public static String containerName(String displayName) {
        return "yppbot-" + displayName.toLowerCase().replaceAll("[^a-z0-9]", "-");
    }

    private static int run(Consumer<String> logger, String... cmd) throws Exception {
        Process p = new ProcessBuilder(cmd).redirectErrorStream(true).start();
        if (logger != null) {
            try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                while ((line = r.readLine()) != null) logger.accept(line);
            }
        }
        return p.waitFor();
    }
}
