package taskmanager.managers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.tasks.Epic;
import taskmanager.tasks.StatusTask;
import taskmanager.tasks.Subtask;
import taskmanager.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static taskmanager.managers.FileBackedTaskManager.*;

class FileBackedTaskManagerTest {

    private static Map<Integer, Task> tasks;
    private static Map<Integer, Epic> epics;
    private static Map<Integer, Subtask> subtasks;

    static TaskManager manager;

    static Task task;
    static Epic epic;
    static Subtask subtask;

    static File taskFile;

    @BeforeAll
    static void beforeAll() throws IOException {
        manager = Managers.getBackedManager();
        tasks = manager.getTasks();
        epics = manager.getEpics();
        subtasks = manager.getSubtasks();
        taskFile = File.createTempFile("tasksFile.csv", null);
    }

    @BeforeEach
    void beforeEach() throws IOException {
        task = new Task("Title", "Desc", StatusTask.NEW);
        epic = new Epic("Title", "Disc");
        subtask = new Subtask("Title", "Desc", StatusTask.NEW, epic.getTaskId());

        manager = Managers.getBackedManager();
        tasks.clear();
        epics.clear();
        subtasks.clear();
        Files.writeString(taskFile.toPath(), "");
    }

    @Test
    void testLoadFromEmptyFile() throws IOException {
        loadFromFile(taskFile.toPath());
        assertEquals(tasks, new HashMap<>(), "Список задач не пустой.");

        manager.setTasks(task.getTaskId(), task);

        assertEquals(
                tasks.get(task.getTaskId()).toStringToFile(),
                task.toStringToFile(),
                "Файл не пустой.");
    }

    @Test
    void testLoadFromNotEmptyFile() throws IOException {
        manager.setTasks(task.getTaskId(), task);
        loadFromFile(taskFile.toPath());
        assertNotEquals(tasks, new HashMap<>(), "Файл не пустой.");

        assertEquals(
                tasks.get(task.getTaskId()).toStringToFile(),
                task.toStringToFile(),
                "Задача не записалась в файл.");
    }

    @Test
    void checkingSaveTaskFromString() {
        String stringTask = "TASK,-2015828827,Title,Desc,NEW";
        String stringEpic = "EPIC,-1381782107,Title,Disc,NEW";
        String stringSubtask = "SUBTASK,610058305,Title,Desc,NEW,-1381782107";

        saveTaskFromString(stringTask);
        saveTaskFromString(stringEpic);
        saveTaskFromString(stringSubtask);

        assertEquals(tasks.get(-2015828827), task, "Задачи не совпадают.");
        assertEquals(epics.get(-1381782107), epic, "Задачи не совпадают.");
        assertEquals(subtasks.get(610058305), subtask, "Задачи не совпадают.");
    }

    @Test
    void addingTaskInTasksListAndGettingTaskById() {
        manager.setTasks(task.getTaskId(), task);
        assertEquals(1, tasks.size(), "Неверное количество задач.");

        final Task savedTask = tasks.get(task.getTaskId());

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");
    }
}
