package bot;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
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
        try {
            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
            Robot robot = new Robot();
            BufferedImage shot = robot.createScreenCapture(new Rectangle(size));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(shot, "png", baos);
            byte[] body = baos.toByteArray();
            ex.getResponseHeaders().set("Content-Type", "image/png");
            ex.sendResponseHeaders(200, body.length);
            ex.getResponseBody().write(body);
        } catch (AWTException e) {
            byte[] err = e.getMessage().getBytes("UTF-8");
            ex.sendResponseHeaders(500, err.length);
            ex.getResponseBody().write(err);
        }
        ex.close();
    }
}
