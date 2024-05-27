package taskmanager.managers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.exceptions.ManagerSaveException;
import taskmanager.tasks.Epic;
import taskmanager.tasks.StatusTask;
import taskmanager.tasks.Subtask;
import taskmanager.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static taskmanager.managers.FileBackedTaskManager.*;

class FileBackedTaskManagerTest {

    static TaskManager manager;
    static File taskFile;

    static LocalDateTime startTime;
    static Duration duration;

    @BeforeAll
    static void beforeAll() throws IOException {
        taskFile = File.createTempFile("resources\\tasksFile.csv", null);
        manager = Managers.getBackedManager(taskFile.toPath());
        tasks = manager.getTasks();
        epics = manager.getEpics();
        subtasks = manager.getSubtasks();
    }

    @BeforeEach
    void beforeEach() throws IOException {
        tasks.clear();
        epics.clear();
        subtasks.clear();
        Files.writeString(taskFile.toPath(), "");

        LocalTime start = LocalTime.of(0, 0);
        LocalTime finish = LocalTime.of(0, 3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm");
        startTime = LocalDateTime.parse("26.05.2024 - 16:02", formatter);
        duration = Duration.between(start, finish);
    }

    @Test
    void testLoadFromEmptyFile() {
        Task task = new Task("Title", "Desc", StatusTask.NEW, startTime, duration);

        loadFromFile(taskFile.toPath());
        assertEquals(tasks, new HashMap<>(), "Список задач не пустой.");

        manager.setTasks(task.getTaskId(), task);

        assertEquals(
                tasks.get(task.getTaskId()).toStringToFile(),
                task.toStringToFile(),
                "Файл не пустой.");
    }

    @Test
    void testLoadFromNotEmptyFile() {
        Task task = new Task("Title", "Desc", StatusTask.NEW, startTime, duration);
        Epic epic = new Epic("Title", "Desc", startTime.plusMinutes(10), Duration.ZERO);
        Subtask subtask = new Subtask(
                "Title", "Desc", StatusTask.NEW, epic.getTaskId(), startTime.plusMinutes(20), duration);
        manager.setTasks(task.getTaskId(), task);
        manager.setEpics(epic.getTaskId(), epic);
        manager.setSubtasks(subtask.getTaskId(), subtask);

        loadFromFile(taskFile.toPath());
        assertNotEquals(tasks, new HashMap<>(), "Файл не пустой.");
        assertNotEquals(epics, new HashMap<>(), "Файл не пустой.");
        assertNotEquals(subtasks, new HashMap<>(), "Файл не пустой.");

        assertEquals(
                tasks.get(task.getTaskId()).toStringToFile(),
                task.toStringToFile(),
                "Задача не записалась в файл.");
    }

    @Test
    void checkingSaveTasksFromString() {
        Task task = new Task("Title", "Desc", StatusTask.NEW, startTime, duration);

        Epic epic = new Epic("Title", "Desc", startTime.plusMinutes(10), Duration.ZERO);
        Subtask subtask = new Subtask(
                "Title", "Desc", StatusTask.NEW, epic.getTaskId(), startTime.plusMinutes(20), duration);

        String stringTask = String.format("TASK,%d,Title,Desc,NEW,26.05.2024 - 16:02,PT3M", task.getTaskId());
        String stringEpic = String.format("EPIC,%d,Title,Desc,NEW,26.05.2024 - 16:12,PT3M", epic.getTaskId());
        String stringSubtask = String.format("SUBTASK,%d,Title,Desc,NEW,26.05.2024 - 16:22,PT3M, %d", subtask.getTaskId(), subtask.getEpicId());

        saveTaskFromString(stringTask);
        saveTaskFromString(stringEpic);
        saveTaskFromString(stringSubtask);

        assertEquals(tasks.get(task.getTaskId()), task, "Задачи не совпадают.");
        assertEquals(epics.get(epic.getTaskId()), epic, "Задачи не совпадают.");
        assertEquals(subtasks.get(subtask.getTaskId()), subtask, "Задачи не совпадают.");
    }

    @Test
    void addingTaskInTasksListAndGettingTaskById() {
        Task task = new Task("Title", "Desc", StatusTask.NEW, startTime, duration);

        manager.setTasks(task.getTaskId(), task);
        assertEquals(1, tasks.size(), "Неверное количество задач.");

        final Task savedTask = tasks.get(task.getTaskId());

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");
    }

    @Test
    void addExceptionsLoadFromFile() {
        Task task = new Task("Title", "Desc", StatusTask.NEW, startTime, duration);
        manager.setTasks(task.getTaskId(), task);

        assertThrows(ManagerSaveException.class, () -> loadFromFile(Path.of("taskFile")),
                "Несуществующий путь должен вызывать исключение.");
    }

    @Test
    void addExceptionsSaveTaskFromString() {
        String stringTask = "TASK,id,Title,Desc,NEW,StartTime,Duration";

        assertThrows(DateTimeParseException.class, () -> saveTaskFromString(stringTask),
                "Несуществующий путь должен вызывать исключение.");
    }

    @Test
    void checkingDeleteTasks() {
        Task task = new Task("Title", "Desc", StatusTask.NEW, startTime, duration);

        Epic epic = new Epic("Title", "Desc", startTime.plusMinutes(10), Duration.ZERO);
        Subtask subtask = new Subtask(
                "Title", "Desc", StatusTask.NEW, epic.getTaskId(), startTime.plusMinutes(20), duration);

        manager.setTasks(task.getTaskId(), task);
        manager.setEpics(epic.getTaskId(), epic);
        manager.setSubtasks(subtask.getTaskId(), subtask);

        loadFromFile(taskFile.toPath());

        manager.deleteTasksForID(task.getTaskId());
        manager.deleteSubTasksForID(subtask.getTaskId());
        manager.deleteEpicForID(epic.getTaskId());

        loadFromFile(taskFile.toPath());

        assertEquals(tasks, new HashMap<>(), "Файл не пустой.");
        assertEquals(epics, new HashMap<>(), "Файл не пустой.");
        assertEquals(subtasks, new HashMap<>(), "Файл не пустой.");
    }
}
