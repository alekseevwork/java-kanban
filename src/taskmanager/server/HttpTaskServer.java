package taskmanager.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import taskmanager.managers.Managers;
import taskmanager.managers.TaskManager;
import taskmanager.server.handlers.TaskHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpTaskServer {

    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private final HttpServer server;

    private final Gson gson;
    private final TaskManager manager;

    public HttpTaskServer() throws IOException {
        this(Managers.getDefault());
    }

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.manager = taskManager;
        gson = Managers.getGson();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", new TaskHandler());

    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer taskServer = new HttpTaskServer();
        taskServer.server.start();
        taskServer.server.stop(0);
    }

    public Gson getGson() {
        return gson;
    }

    public TaskManager getManager() {
        return manager;
    }
}
