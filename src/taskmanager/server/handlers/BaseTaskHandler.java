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
                        sendText(exchange, responseGetTasksById(taskId));
                        break;
                    }
                    if (Pattern.matches("^" + path + "$", pathUri)) {
                        sendText(exchange, responseGetTasks());
                        break;
                    }
                    if (Pattern.matches("^" + path + "/[-\\d+]+/subtasks", pathUri)) {
                        pathUri = pathUri.substring(0, pathUri.lastIndexOf("/"));
                        int taskId = parseId(pathUri);
                        sendText(exchange, responseGetSubtasksInEpic(taskId));
                        break;
                    }
                    sendBadRequest(exchange);
                    break;
                }
                case "POST": {
                    try {
                        String responseBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                        responsePostTask(responseBody);
                        sendText(exchange, "task add");
                        break;
                    } catch (ErrorSavingTasksException e) {
                        sendBadRequest(exchange);
                    } catch (TaskCrossingTimeException e) {
                        sendHasInteractions(exchange);
                    }
                    sendBadRequest(exchange);
                    break;
                }
                case "DELETE": {
                    if (Pattern.matches("^" + path + "/[-\\d]+$", pathUri)) {
                        int taskId = parseId(pathUri);
                        responseDeleteTask(taskId);
                        sendText(exchange, "task delete");
                        exchange.sendResponseHeaders(202, 0);
                        break;
                    }
                    sendBadRequest(exchange);
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
        return Integer.parseInt(id);
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
        h.sendResponseHeaders(406, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected void sendBadRequest(HttpExchange h) throws IOException {
        byte[] resp = "Bad request".getBytes(StandardCharsets.UTF_8);
        h.sendResponseHeaders(404, resp.length);
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

    String responseGetSubtasksInEpic(int taskId) {
        if (manager.getSubtasksInEpic(taskId) == null) {
            return gson.toJson(null);
        }
        return gson.toJson(manager.getSubtasksInEpic(taskId));
    }
}
