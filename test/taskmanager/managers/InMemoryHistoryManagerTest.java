package taskmanager.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.tasks.Epic;
import taskmanager.tasks.StatusTask;
import taskmanager.tasks.Subtask;
import taskmanager.tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {
    static HistoryManager historyManager;

    static Task task;
    static Epic epic;
    static Subtask subtask;

    static LocalDateTime startTime;
    static Duration duration;

    @BeforeEach
    void beforeEach() {
        LocalTime start = LocalTime.of(0, 0);
        LocalTime finish = LocalTime.of(0, 3);

        startTime = LocalDateTime.now();
        duration = Duration.between(start, finish);

        historyManager = new InMemoryHistoryManager();
        historyManager.clear();
        task = new Task("titleTask", "desc", StatusTask.NEW, startTime, duration);
        epic = new Epic("titleEpic", "desc", startTime.plusMinutes(10), Duration.ZERO);
        subtask = new Subtask(
                "titleSubtask", "desc", StatusTask.NEW, epic.getTaskId(), startTime.plusMinutes(20), duration
        );
    }

    @Test
    void addedTaskInHistoryList() {
        historyManager.add(task);
        assertNotNull(historyManager, "История не пустая.");
        assertEquals(1, historyManager.size(), "История не пустая.");

        task = new Task("title Update", "desc", StatusTask.DONE, startTime, duration);

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
        assertFalse(historyManager.remove(123), "Удаление несуществующей задачи.");
    }
}
