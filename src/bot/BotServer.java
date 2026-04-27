package bot;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class BotServer {

    private final AtomicReference<String> status = new AtomicReference<>("starting");

    public BotServer(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/status",     this::handleStatus);
        server.createContext("/screenshot", this::handleScreenshot);
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        System.out.println("[server] listening on :" + port);
    }

    public void setStatus(String s) {
        System.out.println("[bot] " + s);
        status.set(s);
    }

    private void handleStatus(HttpExchange ex) throws IOException {
        byte[] body = status.get().getBytes("UTF-8");
        ex.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
        ex.sendResponseHeaders(200, body.length);
        ex.getResponseBody().write(body);
        ex.close();
    }

    private void handleScreenshot(HttpExchange ex) throws IOException {
        File tmp = null;
        try {
            tmp = File.createTempFile("yppbot-screen", ".png");
            tmp.delete(); // scrot won't overwrite an existing file
            String display = System.getenv("DISPLAY");
            if (display == null) display = ":1";
            ProcessBuilder pb = new ProcessBuilder("scrot", "-z", tmp.getAbsolutePath());
            pb.environment().put("DISPLAY", display);
            pb.redirectErrorStream(true);
            Process p = pb.start();
            boolean done = p.waitFor(5, TimeUnit.SECONDS);
            if (!done || p.exitValue() != 0) throw new IOException("scrot failed");
            byte[] body = Files.readAllBytes(tmp.toPath());
            ex.getResponseHeaders().set("Content-Type", "image/png");
            ex.sendResponseHeaders(200, body.length);
            ex.getResponseBody().write(body);
        } catch (Exception e) {
            byte[] err = ("screenshot error: " + e.getMessage()).getBytes("UTF-8");
            ex.sendResponseHeaders(500, err.length);
            ex.getResponseBody().write(err);
        } finally {
            if (tmp != null) tmp.delete();
            ex.close();
        }
    }
}
