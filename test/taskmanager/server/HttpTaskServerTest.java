package taskmanager.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import taskmanager.managers.Managers;
import taskmanager.managers.TaskManager;
import taskmanager.tasks.Epic;
import taskmanager.tasks.StatusTask;
import taskmanager.tasks.Subtask;
import taskmanager.tasks.Task;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskServerTest {

    TaskManager manager = Managers.getDefault();
    HttpTaskServer taskServer = new HttpTaskServer();
    Gson gson = Managers.getGson();

    public HttpTaskServerTest() throws IOException {
    }

    @BeforeEach
    public void setUp() {
        manager.deleteTasks();
        manager.deleteEpics();
        manager.deleteSubtasks();
        taskServer.start();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }

    @Test
    public void testAddTaskMethodPost() throws IOException, InterruptedException {
        Task task = new Task("Test 2", "Testing task 2", StatusTask.NEW, LocalDateTime.now(), Duration.ofMinutes(5));
        String taskJson = gson.toJson(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<Task> tasksFromManager = manager.getTasks().values().stream().toList();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Test 2", tasksFromManager.getFirst().getTitle(), "Некорректное имя задачи");
    }

    @Test
    public void testAddEpicMethodPost() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic 1", "Testing epic 1", LocalDateTime.now());
        String epicJson = gson.toJson(epic);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(epicJson)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<Epic> tasksFromManager = manager.getEpics().values().stream().toList();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Epic 1", tasksFromManager.getFirst().getTitle(), "Некорректное имя задачи");
    }

    @Test
    public void testAddSubtaskMethodPost() throws IOException, InterruptedException {
        LocalDateTime time = LocalDateTime.now();
        Epic epic = new Epic("Epic 1", "Testing epic 1", time);
        Subtask subtask = new Subtask("Subtask 1", "Testing subtask 1", StatusTask.NEW, epic.getTaskId(), time.plusMinutes(1), Duration.ofMinutes(5));
        String subtaskJson = gson.toJson(subtask);
        manager.setEpics(epic.getTaskId(), epic);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(subtaskJson)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<Subtask> tasksFromManager = manager.getSubtasks().values().stream().toList();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Subtask 1", tasksFromManager.getFirst().getTitle(), "Некорректное имя задачи");
    }

    @Test
    public void testAllTasksMethodGet() throws IOException, InterruptedException {
        Task task1 = new Task("Test 1", "Testing task 1", StatusTask.NEW, LocalDateTime.now(), Duration.ofMinutes(5));
        Task task2 = new Task("Test 2", "Testing task 2", StatusTask.NEW, LocalDateTime.now().plusMinutes(5), Duration.ofMinutes(5));
        manager.setTasks(task1.getTaskId(), task1);
        manager.setTasks(task2.getTaskId(), task2);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).header("Content-Type", "application/json;charset=utf-8").GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        Type intTaskType = new TypeToken<Map<Integer, Task>>() {
        }.getType();
        Map<Integer, Task> mapTasks = gson.fromJson(body, intTaskType);

        Task firstGetTask = mapTasks.values().stream().toList().getFirst();

        assertEquals(200, response.statusCode());
        assertEquals(2, mapTasks.size(), "Некорректное количество задач");
        assertEquals(task1, firstGetTask, "Некорректно сохранились задачи");
    }

    @Test
    public void testTaskByIdMethodGet() throws IOException, InterruptedException {
        Task task1 = new Task("Test 1", "Testing task 1",
                StatusTask.NEW, LocalDateTime.now(), Duration.ofMinutes(5));
        manager.setTasks(task1.getTaskId(), task1);
        String id = String.valueOf(task1.getTaskId());
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/" + id);
        HttpRequest request = HttpRequest.newBuilder().uri(url)
                .header("Content-Type", "application/json;charset=utf-8")
                .GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Task responseTask = gson.fromJson(response.body(), Task.class);

        assertEquals(200, response.statusCode());
        assertEquals(task1, responseTask, "Некорректно сохранились задачи");

        URI urlBad = URI.create("http://localhost:8080/tasks/q");
        HttpRequest requestBad = HttpRequest.newBuilder().uri(urlBad)
                .header("Content-Type", "application/json;charset=utf-8")
                .GET().build();

        HttpResponse<String> responseBad = client.send(requestBad, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, responseBad.statusCode());
    }

    @Test
    public void testSubtasksInEpicMethodGet() throws IOException, InterruptedException {
        LocalDateTime time = LocalDateTime.now();
        Epic epic = new Epic("Epic 1", "Testing epic 1", time);
        Subtask subtask = new Subtask("Subtask 1", "Testing subtask 1",
                StatusTask.NEW, epic.getTaskId(), time.plusMinutes(1), Duration.ofMinutes(5));
        manager.setEpics(epic.getTaskId(), epic);
        manager.setSubtasks(subtask.getTaskId(), subtask);

        String epicId = String.valueOf(epic.getTaskId());

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics/" + epicId + "/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        Subtask tasksFromManager = manager.getSubtasksInEpic(epic.getTaskId()).getFirst();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(tasksFromManager, subtask, "Задачи возвращаютя не корректно");
    }

    @Test
    public void testMethodDeleteTaskById() throws IOException, InterruptedException {
        Task task1 = new Task("Test 1", "Testing task 1",
                StatusTask.NEW, LocalDateTime.now(), Duration.ofMinutes(5));
        manager.setTasks(task1.getTaskId(), task1);
        String id = String.valueOf(task1.getTaskId());
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/" + id);
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(manager.getTasks().isEmpty(), "Задачи не удаляются");
    }

    @Test
    public void testMethodGetHistoryTask() throws IOException, InterruptedException {
        LocalDateTime startTime = LocalDateTime.now();
        Task task = new Task("title Task1", "desc", StatusTask.NEW, startTime.plusMinutes(5), Duration.ofMinutes(2));
        Task task2 = new Task("title Task2", "desc", StatusTask.NEW, startTime, Duration.ofMinutes(2));
        Epic epic = new Epic("title Epic", "desc", startTime.plusMinutes(10));
        Subtask subtask = new Subtask("title Subtask", "desc",
                StatusTask.NEW, epic.getTaskId(), startTime.plusMinutes(20), Duration.ofMinutes(3)
        );

        manager.setTasks(task.getTaskId(), task);
        manager.setTasks(task2.getTaskId(), task2);
        manager.setEpics(epic.getTaskId(), epic);
        manager.setSubtasks(subtask.getTaskId(), subtask);

        manager.getTasksForId(task.getTaskId());
        manager.getTasksForId(task2.getTaskId());
        manager.getEpicForId(epic.getTaskId());
        manager.getSubTaskForId(subtask.getTaskId());

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/history");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        assertEquals(200, response.statusCode());
    }
}
