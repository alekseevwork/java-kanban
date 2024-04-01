package taskmanager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.tasks.StatusTask;
import taskmanager.tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    static HistoryManager historyManager;
    static Task task;

    @BeforeEach
    void beforeEach() {
        historyManager = new InMemoryHistoryManager();
        historyManager.clear();
        task = new Task("title", "desc", StatusTask.NEW);
    }

    @Test
    void addedTaskInHistoryList() {
        historyManager.add(task);
        assertNotNull(historyManager, "История не пустая.");
        assertEquals(1, historyManager.size(), "История не пустая.");

        task = new Task("title Update", "desc", StatusTask.DONE);

        historyManager.add(task);
        List<Task> history = historyManager.getHistory();
        assertNotEquals(history.getFirst(),history.get(1), "Данные перезаписались");
    }

    @Test
    void getFirstTaskInHistoryList() {
        historyManager.add(task);
        assertEquals(historyManager.getHistory().getFirst(), task, "Задачи не совпадает.");
    }
}