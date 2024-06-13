package taskmanager.tasks;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    static LocalTime start = LocalTime.of(0, 0);
    static LocalTime finish = LocalTime.of(0, 3);

    static LocalDateTime startTime = LocalDateTime.now();
    static Duration duration = Duration.between(start, finish);

    @Test
    void instancesOfTaskClassAreEqualToEachOtherIfIdIsEqual() {
        Task task1 = new Task("Title", "Desc", StatusTask.NEW, startTime, duration);
        Task task2 = new Task("Title", "Desc", StatusTask.NEW, startTime, duration);

        assertNotNull(task1, "Задача не найдена.");
        assertEquals(task1, task2, "Задачи не совпадают.");
    }

    @Test
    void successorOfTaskClassAreEqualToEachOtherIfIdIsEqual() {
        Task task1 = new Epic("Title", "Desc", startTime);
        Task task2 = new Epic("Title", "Desc", startTime);

        assertNotNull(task1, "Задача не найдена.");
        assertEquals(task1, task2, "Задачи не совпадают.");
    }


}