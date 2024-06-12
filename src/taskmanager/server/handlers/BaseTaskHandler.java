package taskmanager.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import taskmanager.exceptions.ErrorSavingTasksException;
import taskmanager.exceptions.TaskCrossingTimeException;
import taskmanager.managers.Managers;
import taskmanager.managers.TaskManager;
import taskmanager.tasks.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class BaseTaskHandler implements HttpHandler {

    protected final Gson gson = Managers.getGson();
    protected final TaskManager manager = Managers.getDefault();
    String path;

    public BaseTaskHandler(String path) {
        this.path = path;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String pathUri = exchange.getRequestURI().getPath();

            switch (exchange.getRequestMethod()) {
                case "GET": {
                    if (Pattern.matches("^" + path + "/[-\\d]+$", pathUri)) {
                        int taskId = parseId(pathUri);
                        if (taskId != -1) {
                            sendText(exchange, responseGetTasksById(taskId));
                        } else {
                            exchange.sendResponseHeaders(405, 0);
                        }
                        break;
                    }
                    if (Pattern.matches("^" + path + "$", pathUri)) {
                        sendText(exchange, responseGetTasks());
                        break;
                    }
                    break;
                }
                case "POST": {
                    try {
                        String responseBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                        responsePostTask(responseBody);
                        break;
                    } catch (ErrorSavingTasksException e) {
                        exchange.sendResponseHeaders(405, 0);
                    } catch (TaskCrossingTimeException e) {
                        sendHasInteractions(exchange);
                    }
                    break;

                }
                case "DELETE": {
                    if (Pattern.matches("^" + path + "/[-\\d]+$", pathUri)) {
                        int taskId = parseId(pathUri);
                        if (taskId != -1) {
                            responseDeleteTask(taskId);
                            exchange.sendResponseHeaders(202, 0);
                        } else {
                            exchange.sendResponseHeaders(405, 0);
                        }
                        break;
                    }
                    break;
                }
                default:
                    sendNotFound(exchange);
            }
        } catch (Exception e) {
            sendNotFound(exchange);
        } finally {
            exchange.close();
        }
    }

    int parseId(String path) {
        String id = path.substring(path.lastIndexOf("/") + 1);
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected void sendNotFound(HttpExchange h) throws IOException {
        byte[] resp = "Page not found".getBytes(StandardCharsets.UTF_8);
        h.sendResponseHeaders(404, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected void sendHasInteractions(HttpExchange h) throws IOException {
        byte[] resp = "Task intersects with existing ones".getBytes(StandardCharsets.UTF_8);
        h.sendResponseHeaders(400, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    String responseGetTasks() {
        return gson.toJson(manager.getTasks());
    }

    String responseGetTasksById(int taskId) {
        if (manager.getTasksForId(taskId) == null) {
            return gson.toJson(null);
        }
        return gson.toJson(manager.getTasksForId(taskId));
    }

    void responsePostTask(String responseBody) {
        Task task = gson.fromJson(responseBody, Task.class);
        manager.setTasks(task.getTaskId(), task);
    }

    void responseDeleteTask(int taskId) {
        manager.deleteTasksForID(taskId);
    }
}
