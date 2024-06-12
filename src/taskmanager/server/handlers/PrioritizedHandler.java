package taskmanager.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import taskmanager.managers.Managers;
import taskmanager.managers.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PrioritizedHandler implements HttpHandler {

    TaskManager manager = Managers.getDefault();
    Gson gson = Managers.getGson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();

            if (method.equals("GET")) {
                String response = gson.toJson(manager.getPrioritizedTasks());

                if (response.equals("[]")) {
                    exchange.getResponseHeaders().add("Content-Type", "text/plain;charset=utf-8");
                    response = "Prioritized list is empty";
                } else {
                    exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
                }
                byte[] resp = response.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, resp.length);
                exchange.getResponseBody().write(resp);
            }
        } catch (Exception e) {
            byte[] resp = "Page not found".getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(404, resp.length);
            exchange.getResponseBody().write(resp);
        } finally {
            exchange.close();
        }
    }
}
