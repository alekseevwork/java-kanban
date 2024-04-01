package taskmanager.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void StatusOfEpicFirstCreatedShouldBeNew() {
        Epic epic = new Epic("Title", "Disc");

        assertNotNull(epic, "Задача не найдена.");
        assertEquals(StatusTask.NEW, epic.statusTask,"Неверный статус.");
    }
}