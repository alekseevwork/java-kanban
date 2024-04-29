package taskmanager.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.tasks.Epic;
import taskmanager.tasks.StatusTask;
import taskmanager.tasks.Subtask;
import taskmanager.tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {
    static HistoryManager historyManager;

    static Task task;
    static Epic epic;
    static Subtask subtask;

    @BeforeEach
    void beforeEach() {
        historyManager = new InMemoryHistoryManager();
        historyManager.clear();
        task = new Task("titleTask", "desc", StatusTask.NEW);
        epic = new Epic("titleEpic", "desc");
        subtask = new Subtask("titleSubtask", "desc", StatusTask.NEW, epic.getTaskId());
    }

    @Test
    void addedTaskInHistoryList() {
        historyManager.add(task);
        assertNotNull(historyManager, "История не пустая.");
        assertEquals(1, historyManager.size(), "История не пустая.");

        task = new Task("title Update", "desc", StatusTask.DONE);

        historyManager.add(task);
        List<Task> history = historyManager.getHistory();
        assertNotEquals(history.getFirst(), history.get(1), "Данные перезаписались");
    }

    @Test
    void getFirstTaskInHistoryList() {
        historyManager.add(task);
        assertEquals(historyManager.getHistory().getFirst(), task, "Задачи не совпадает.");
    }

    @Test
    void addedTasksInHistoryLinkedList() {
        historyManager.add(task);
        historyManager.add(epic);

        assertEquals(historyManager.size(), 2, "Задачи не сохранились.");
        historyManager.add(task);

        assertEquals(historyManager.getHistory().getFirst(), epic, "Не обновился порядок задачь в истории.");
    }

    @Test
    void deleteTasksInHistoryLinkedList() {
        historyManager.add(task);
        historyManager.remove(task.getTaskId());

        assertNull(historyManager.getHistory(), "Задача не удалилась.");

        historyManager.remove(task.getTaskId());
        assertFalse(historyManager.remove(task.getTaskId()), "Метод удаления сработала.");
    }
}
