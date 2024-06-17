package taskmanager.servers;

import com.sun.net.httpserver.HttpServer;
import taskmanager.servers.handlers.BaseTaskHandler;
import taskmanager.servers.handlers.EpicHandler;
import taskmanager.servers.handlers.SubtaskHandler;
import taskmanager.servers.handlers.HistoryHandler;
import taskmanager.servers.handlers.PrioritizedHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {

    private static final int PORT = 8080;
    private final HttpServer server;

    public HttpTaskServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", new BaseTaskHandler("/tasks"));
        server.createContext("/epics", new EpicHandler("/epics"));
        server.createContext("/subtasks", new SubtaskHandler("/subtasks"));
        server.createContext("/history", new HistoryHandler());
        server.createContext("/prioritized", new PrioritizedHandler());
    }

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop(0);
    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer taskServer = new HttpTaskServer();
        taskServer.start();
    }
}
