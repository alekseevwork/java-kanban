package taskmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.tasks.Epic;
import taskmanager.tasks.StatusTask;
import taskmanager.tasks.Subtask;
import taskmanager.tasks.Task;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    static HashMap<Integer, Task> tasks;
    static HashMap<Integer, Epic> epics;
    static HashMap<Integer, Subtask> subtasks;
    static TaskManager manager;

    static Task task;
    static Epic epic;
    static Subtask subtask;

    @BeforeEach
    void beforeEach() {
        task = new Task("Title", "Desc", StatusTask.NEW);
        epic = new Epic("Title", "Disc");
        subtask = new Subtask("Title", "Desc", StatusTask.NEW, epic.getTaskId());

        manager = Managers.getDefault();
        tasks = manager.getTasks();
        epics = manager.getEpics();
        subtasks = manager.getSubtasks();
    }

    @Test
    void AddingTaskInTasksListAndGettingTaskById() {
        manager.setTasks(task.getTaskId(), task);
        assertEquals(1, tasks.size(), "Неверное количество задач.");

        final Task savedTask = tasks.get(task.getTaskId());

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

    }

    @Test
    void AddingEpicInEpicsListAndGettingEpicById() {
        manager.setEpics(epic.getTaskId(), epic);

        assertEquals(1, epics.size(), "Неверное количество задач.");

        final Task savedEpic = epics.get(epic.getTaskId());

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");
    }

    @Test
    void AddingSubtaskInSubtasksListAndGettingSubtaskById() {
        manager.setSubtasks(subtask.getTaskId(), subtask);

        assertEquals(1, subtasks.size(), "Неверное количество задач.");

        final Task savedEpic = subtasks.get(subtask.getTaskId());

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(subtask, savedEpic, "Задачи не совпадают.");
    }

    @Test
    void ChangingEpicStatusWhenAddingSubtaskNEW() {
        int indexOfNewStatus = epic.toString().indexOf("NEW");
        assertTrue(indexOfNewStatus > 0, "Неверный статус.");
    }

    @Test
    void ChangingEpicStatusWhenAddingSubtaskInProgress() {
        manager.setEpics(epic.getTaskId(), epic);
        subtask = new Subtask("Title", "Desc", StatusTask.IN_PROGRESS, epic.getTaskId());
        manager.setSubtasks(subtask.getTaskId(), subtask);
        int indexOfInProgressStatus = epic.toString().indexOf("IN_PROGRESS");
        assertTrue(indexOfInProgressStatus > 0, "Неверный статус.");

    }

    @Test
    void ChangingEpicStatusWhenAddingSubtaskDone() {
        manager.setEpics(epic.getTaskId(), epic);
        subtask = new Subtask("Title", "Desc", StatusTask.DONE, epic.getTaskId());
        manager.setSubtasks(subtask.getTaskId(), subtask);
        int indexOfDoneStatus = epic.toString().indexOf("DONE");
        assertTrue(indexOfDoneStatus > 0, "Неверный статус.");
    }

    @Test
    void DeleteTaskByIdInTasksList() {
        manager.setTasks(task.getTaskId(), task);
        manager.deleteTasksForID(task.getTaskId());

        assertEquals(0, tasks.size(), "Задача не удалена.");
    }

    @Test
    void DeleteEpicByIdInEpicsList() {
        manager.setEpics(epic.getTaskId(), epic);
        manager.setSubtasks(subtask.getTaskId(), subtask);

        assertEquals(subtasks.size(), 1, "Подзадача добавлена");

        manager.deleteEpicForID(epic.getTaskId());
        assertEquals("{}", epics.toString(), "Задача не удалена.");

    }
}