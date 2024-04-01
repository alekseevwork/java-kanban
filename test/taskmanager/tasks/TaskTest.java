package taskmanager.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void instancesOfTaskClassAreEqualToEachOtherIfIdIsEqual() {
        Task task1 = new Task("Title", "Desc", StatusTask.NEW);
        Task task2 = new Task("Title", "Desc", StatusTask.NEW);

        assertNotNull(task1, "Задача не найдена.");
        assertEquals(task1, task2, "Задачи не совпадают.");
    }
    @Test
    void successorOfTaskClassAreEqualToEachOtherIfIdIsEqual() {
        Task task1 = new Epic("Title", "Desc");
        Task task2 = new Epic("Title", "Desc");

        assertNotNull(task1, "Задача не найдена.");
        assertEquals(task1, task2, "Задачи не совпадают.");
    }


}