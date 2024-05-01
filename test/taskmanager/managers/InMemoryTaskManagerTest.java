package taskmanager.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.tasks.Epic;
import taskmanager.tasks.StatusTask;
import taskmanager.tasks.Subtask;
import taskmanager.tasks.Task;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private static Map<Integer, Task> tasks;
    private static Map<Integer, Epic> epics;
    private static Map<Integer, Subtask> subtasks;

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
    void addingTaskInTasksListAndGettingTaskById() {
        manager.setTasks(task.getTaskId(), task);
        assertEquals(1, tasks.size(), "Неверное количество задач.");

        final Task savedTask = tasks.get(task.getTaskId());


        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

    }

    @Test
    void addingEpicInEpicsListAndGettingEpicById() {
        manager.setEpics(epic.getTaskId(), epic);

        assertEquals(1, epics.size(), "Неверное количество задач.");

        final Task savedEpic = epics.get(epic.getTaskId());

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");
    }

    @Test
    void addingSubtaskInSubtasksListAndGettingSubtaskById() {
        manager.setSubtasks(subtask.getTaskId(), subtask);

        assertEquals(1, subtasks.size(), "Неверное количество задач.");

        final Task savedEpic = subtasks.get(subtask.getTaskId());

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(subtask, savedEpic, "Задачи не совпадают.");
    }

    @Test
    void changingEpicStatusWhenAddingSubtaskNEW() {
        int indexOfNewStatus = epic.toString().indexOf("NEW");
        assertTrue(indexOfNewStatus > 0, "Неверный статус.");
    }

    @Test
    void changingEpicStatusWhenAddingSubtaskInProgress() {
        manager.setEpics(epic.getTaskId(), epic);
        subtask = new Subtask("Title", "Desc", StatusTask.IN_PROGRESS, epic.getTaskId());
        manager.setSubtasks(subtask.getTaskId(), subtask);
        int indexOfInProgressStatus = epic.toString().indexOf("IN_PROGRESS");

        assertTrue(indexOfInProgressStatus > 0, "Неверный статус.");

    }

    @Test
    void changingEpicStatusWhenAddingSubtaskDone() {
        manager.setEpics(epic.getTaskId(), epic);
        subtask = new Subtask("Title", "Desc", StatusTask.DONE, epic.getTaskId());
        manager.setSubtasks(subtask.getTaskId(), subtask);
        int indexOfDoneStatus = epic.toString().indexOf("DONE");
        System.out.println(epic);
        assertTrue(indexOfDoneStatus > 0, "Неверный статус.");
    }

    @Test
    void deleteTaskByIdInTasksList() {
        manager.setTasks(task.getTaskId(), task);
        manager.deleteTasksForID(task.getTaskId());

        assertEquals(0, tasks.size(), "Задача не удалена.");
    }

    @Test
    void deleteEpicByIdInEpicsList() {
        manager.setEpics(epic.getTaskId(), epic);
        manager.setSubtasks(subtask.getTaskId(), subtask);

        assertEquals(subtasks.size(), 1, "Подзадача добавлена");

        manager.deleteEpicForID(epic.getTaskId());
        assertEquals("{}", epics.toString(), "Задача не удалена.");
    }

    @Test
    void setTaskFieldsNotChangeAfterAddTaskToManager() {
        manager.setTasks(task.getTaskId(), task);
        Task savedTask = manager.getTasksForId(task.getTaskId());

        savedTask.setTaskId(1);
        savedTask.setTitle("New Title");
        savedTask.setDescription("New Description");
        savedTask.setStatusTask(StatusTask.DONE);

        Task savedTaskAgain = manager.getTasksForId(task.getTaskId());

        assertNotEquals(1, savedTaskAgain.getTaskId(), "Изменился Id.");
        assertNotEquals("New Title", savedTaskAgain.getTitle(), "Изменился заголовок.");
        assertNotEquals("New Description", savedTaskAgain.getDescription(), "Изменилось описание.");
        assertNotEquals(StatusTask.DONE, savedTaskAgain.getStatusTask(), "Изменился статус.");

        assertEquals(savedTaskAgain, tasks.get(task.getTaskId()), "Задачи не совпадают.");
    }

    @Test
    void setSubtasksFieldsNotChangeAfterAddSubtasksToManager() {
        manager.setEpics(epic.getTaskId(), epic);
        manager.setSubtasks(subtask.getTaskId(), subtask);
        Subtask savedTask = manager.getSubTaskForId(subtask.getTaskId());

        savedTask.setTaskId(1);
        savedTask.setTitle("New Title");
        savedTask.setDescription("New Description");
        savedTask.setStatusTask(StatusTask.DONE);

        Subtask savedTaskAgain = manager.getSubTaskForId(subtask.getTaskId());

        assertNotEquals(1, savedTaskAgain.getTaskId(), "Изменился Id.");
        assertNotEquals("New Title", savedTaskAgain.getTitle(), "Изменился заголовок.");
        assertNotEquals("New Description", savedTaskAgain.getDescription(), "Изменилось описание.");
        assertNotEquals(StatusTask.DONE, savedTaskAgain.getStatusTask(), "Изменился статус.");

        assertEquals(savedTaskAgain, subtasks.get(subtask.getTaskId()), "Задачи не совпадают.");
    }

    @Test
    void setTaskFieldsChange() {
        manager.deleteTasksForID(task.getTaskId());
        task.setTaskId(1);
        task.setTitle("New Title");
        task.setDescription("New Description");
        task.setStatusTask(StatusTask.DONE);

        assertEquals(1, task.getTaskId(), "Не изменился Id.");
        assertEquals("New Title", task.getTitle(), "Не изменился заголовок.");
        assertEquals("New Description", task.getDescription(), "Не зменилось описание.");
        assertEquals(StatusTask.DONE, task.getStatusTask(), "Не изменился статус.");
    }
}
