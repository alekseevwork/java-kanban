package taskmanager.managers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.exceptions.ErrorSavingTasksException;
import taskmanager.exceptions.TaskCrossingTimeException;
import taskmanager.tasks.Epic;
import taskmanager.tasks.StatusTask;
import taskmanager.tasks.Subtask;
import taskmanager.tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static taskmanager.managers.InMemoryTaskManager.*;
import static taskmanager.managers.InMemoryTaskManager.subtasks;

class InMemoryTaskManagerTest {
    static TaskManager manager;
    static LocalDateTime startTime;
    static Duration duration;

    @BeforeAll
    static void beforeAll() {
        manager = new InMemoryTaskManager();

        treeTasks = manager.getPrioritizedTasks();
        tasks = manager.getTasks();
        epics = manager.getEpics();
        subtasks = manager.getSubtasks();
    }

    @BeforeEach
    void beforeEach() {
        tasks.clear();
        epics.clear();
        subtasks.clear();

        LocalTime start = LocalTime.of(0, 0);
        LocalTime finish = LocalTime.of(0, 3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm");
        startTime = LocalDateTime.parse("26.05.2024 - 16:02", formatter);
        duration = Duration.between(start, finish);
    }

    @Test
    void addingTaskInTasksListAndGettingTaskById() {
        Task task = new Task("Title", "Desc", StatusTask.NEW, startTime, duration);
        manager.setTasks(task.getTaskId(), task);

        assertEquals(1, tasks.size(), "Неверное количество задач.");

        final Task savedTask = manager.getTasksForId(task.getTaskId());

        assertThrows(ErrorSavingTasksException.class, () -> manager.setTasks(123, null));
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

    }

    @Test
    void addingEpicInEpicsListAndGettingEpicById() {
        Epic epic = new Epic("Title", "Disc", startTime, Duration.ZERO);
        manager.setEpics(epic.getTaskId(), epic);

        assertEquals(1, epics.size(), "Неверное количество задач.");

        assertThrows(ErrorSavingTasksException.class, () -> manager.setEpics(123, null));
        final Task savedEpic = manager.getEpicForId(epic.getTaskId());
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");
    }

    @Test
    void addingSubtaskInSubtasksListAndGettingSubtaskById() {
        Epic epic = new Epic("Title", "Disc", startTime, Duration.ZERO);
        manager.setEpics(epic.getTaskId(), epic);
        Subtask subtask = new Subtask(
                "Title", "Desc", StatusTask.NEW, epic.getTaskId(), startTime.plusMinutes(20), duration);
        manager.setSubtasks(subtask.getTaskId(), subtask);

        assertEquals(1, subtasks.size(), "Неверное количество задач.");

        final Task savedEpic = manager.getSubTaskForId(subtask.getTaskId());

        assertThrows(ErrorSavingTasksException.class, () -> manager.setSubtasks(123, null));
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(subtask, savedEpic, "Задачи не совпадают.");
    }

    @Test
    void changingEpicStatusWhenAddingSubtaskNEW() {
        Epic epic = new Epic("Title", "Disc", startTime, Duration.ZERO);
        int indexOfNewStatus = epic.toString().indexOf("NEW");
        assertTrue(indexOfNewStatus > 0, "Неверный статус.");
    }

    @Test
    void changingEpicStatusWhenAddingSubtaskInProgress() {
        Epic epic = new Epic("Title", "Disc", startTime, Duration.ZERO);
        manager.setEpics(epic.getTaskId(), epic);
        Subtask subtask = new Subtask(
                "Title", "Desc", StatusTask.IN_PROGRESS, epic.getTaskId(), startTime.plusMinutes(20), duration);
        manager.setSubtasks(subtask.getTaskId(), subtask);
        int indexOfInProgressStatus = epic.toString().indexOf("IN_PROGRESS");

        assertTrue(indexOfInProgressStatus > 0, "Неверный статус.");

    }

    @Test
    void changingEpicStatusWhenAddingSubtaskDone() {
        Epic epic = new Epic("Title", "Disc", startTime, Duration.ZERO);
        manager.setEpics(epic.getTaskId(), epic);
        Subtask subtask = new Subtask(
                "Title", "Desc", StatusTask.DONE, epic.getTaskId(), startTime.plusMinutes(20), duration);
        manager.setSubtasks(subtask.getTaskId(), subtask);
        int indexOfDoneStatus = epic.toString().indexOf("DONE");

        assertTrue(indexOfDoneStatus > 0, "Неверный статус.");
    }

    @Test
    void deleteTaskByIdInTasksList() {
        Task task = new Task("Title", "Desc", StatusTask.NEW, startTime, duration);
        manager.setTasks(task.getTaskId(), task);

        manager.deleteTasksForID(task.getTaskId());

        assertEquals(0, tasks.size(), "Задача не удалена.");
    }

    @Test
    void deleteEpicByIdInEpicsList() {
        Epic epic = new Epic("Title", "Disc", startTime, Duration.ZERO);
        Subtask subtask = new Subtask(
                "Title", "Desc", StatusTask.IN_PROGRESS, epic.getTaskId(), startTime.plusMinutes(20), duration);
        manager.setEpics(epic.getTaskId(), epic);
        manager.setSubtasks(subtask.getTaskId(), subtask);

        assertEquals(subtasks.size(), 1, "Подзадача добавлена");

        manager.deleteEpicForID(epic.getTaskId());
        assertEquals("{}", epics.toString(), "Задача не удалена.");
        assertEquals("{}", subtasks.toString(), "Задача не удалена.");
    }

    @Test
    void deleteTypeTasksInList() {
        Task task = new Task("Title", "Desc", StatusTask.NEW, startTime, duration);
        Task task2 = new Task("Title2", "Desc", StatusTask.NEW, startTime.plusMinutes(5), duration);
        Epic epic = new Epic("Title", "Disc", startTime.plusMinutes(10), Duration.ZERO);
        Subtask subtask = new Subtask(
                "Title", "Desc", StatusTask.IN_PROGRESS, epic.getTaskId(), startTime.plusMinutes(20), duration);

        manager.setTasks(task.getTaskId(), task);
        manager.setTasks(task2.getTaskId(), task2);
        manager.setEpics(epic.getTaskId(), epic);
        manager.setSubtasks(subtask.getTaskId(), subtask);

        manager.deleteTypeTasks(tasks);
        manager.deleteTypeTasks(epics);
        manager.deleteTypeTasks(subtasks);

        assertEquals("{}", tasks.toString(), "Задача не удалена.");
        assertEquals("{}", epics.toString(), "Задача не удалена.");
        assertEquals("{}", subtasks.toString(), "Задача не удалена.");
    }

    @Test
    void setTaskFieldsNotChangeAfterAddTaskToManager() {
        Task task = new Task("Title", "Desc", StatusTask.NEW, startTime, duration);
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
        Epic epic = new Epic("Title", "Disc", startTime, Duration.ZERO);
        Subtask subtask = new Subtask(
                "Title", "Desc", StatusTask.IN_PROGRESS, epic.getTaskId(), startTime.plusMinutes(20), duration);
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
        Task task = new Task("Title", "Desc", StatusTask.NEW, startTime, duration);
        manager.setTasks(task.getTaskId(), task);
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

    @Test
    void changeDurationEpicWhenAddSubtask() {
        Epic epic = new Epic("Title", "Disc", startTime, Duration.ZERO);
        Subtask subtask = new Subtask(
                "Title", "Desc", StatusTask.IN_PROGRESS, epic.getTaskId(), startTime.plusMinutes(10), duration);
        Subtask subtaskSecond = new Subtask(
                "Title", "Desc", StatusTask.IN_PROGRESS, epic.getTaskId(), startTime.plusMinutes(20), duration);
        assertNotEquals(epic.getDuration(), subtask.getDuration(),
                "Время задачи сохранилось не правильно");
        manager.setEpics(epic.getTaskId(), epic);
        manager.setSubtasks(subtask.getTaskId(), subtask);

        assertEquals(epic.getDuration(), subtask.getDuration(),
                "Продолжительность задачи не изменилась");
        manager.setSubtasks(subtaskSecond.getTaskId(), subtaskSecond);

        assertEquals(epic.getDuration(), subtask.getDuration().plus(subtaskSecond.getDuration()),
                "Продолжительность задачи не изменилась");
    }

    @Test
    void checkingIsCrossingTimeTask() {
        Task firstTask = new Task("Title", "Desc", StatusTask.NEW, startTime, duration);
        Task secondTask = new Task("Title", "Desc", StatusTask.NEW, startTime.plusMinutes(20), duration);
        Task secondTask2 = new Task("Title", "Desc", StatusTask.NEW, startTime.plusMinutes(3), duration);

        assertTrue(isCrossingTimeTask(secondTask, firstTask), "Вторая задача начинается до окончания первой.");
        assertFalse(isCrossingTimeTask(firstTask, secondTask), "Задачи пересекаются.");
        assertFalse(isCrossingTimeTask(firstTask, null), "Не обработалось значени null");
        assertFalse(isCrossingTimeTask(firstTask, secondTask2), "Время окончания первой задачи и начало второй не совпали.");
    }

    @Test
    void checkingSaveTaskInTreeMap() {
        Task firstTask = new Task("firstTask", "Desc", StatusTask.NEW, startTime, duration);
        Task secondTask = new Task("secondTask", "Desc", StatusTask.NEW, startTime.plusMinutes(2), duration);
        Task secondTask2 = new Task("secondTask2", "Desc", StatusTask.NEW, startTime.plusMinutes(5), duration);

        saveTaskInTreeMap(secondTask2);
        saveTaskInTreeMap(firstTask);

        assertThrows(ErrorSavingTasksException.class, () -> saveTaskInTreeMap(null), "Не обработалось значени null");
        assertEquals(treeTasks.first(), firstTask, "Порядок задач не поменялся.");

        assertThrows(TaskCrossingTimeException.class, () -> saveTaskInTreeMap(secondTask), "Исключение не пробросилось.");
    }
}
