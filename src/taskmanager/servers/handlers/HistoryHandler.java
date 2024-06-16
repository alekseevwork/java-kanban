package taskmanager.servers.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import taskmanager.managers.HistoryManager;
import taskmanager.managers.Managers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HistoryHandler implements HttpHandler {
    HistoryManager manager = Managers.getDefaultHistory();
    Gson gson = Managers.getGson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            String method = exchange.getRequestMethod();

            if (method.equals("GET")) {
                String response = gson.toJson(manager.getHistory());

                if (response.equals("null")) {
                    exchange.getResponseHeaders().add("Content-Type", "text/plain;charset=utf-8");
                    response = "History list is empty";
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
