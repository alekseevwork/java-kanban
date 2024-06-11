package taskmanager.server;

import com.sun.net.httpserver.HttpServer;
import taskmanager.server.handlers.TaskHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {

    private static final int PORT = 8080;
    private final HttpServer server;

    public HttpTaskServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", new TaskHandler("/tasks"));

    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer taskServer = new HttpTaskServer();
        taskServer.server.start();
//        taskServer.server.stop(0);
    }
}
