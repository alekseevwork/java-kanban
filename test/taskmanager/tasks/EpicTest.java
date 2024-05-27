package taskmanager.tasks;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void statusOfEpicFirstCreatedShouldBeNew() {
        Epic epic = new Epic("Title", "Disc", LocalDateTime.now(), Duration.ZERO);

        assertNotNull(epic, "Задача не найдена.");
        assertEquals(StatusTask.NEW, epic.getStatusTask(), "Неверный статус.");
    }

    @Test
    void changingEpicDurationOfMethods() {
        LocalTime start = LocalTime.of(0, 0);
        LocalTime finish = LocalTime.of(0, 3);
        Duration duration = Duration.between(start, finish);
        Epic epic = new Epic("Title", "Disc", LocalDateTime.now(), Duration.ZERO);

        epic.durationPlusSubtaskDuration(duration);
        assertEquals(duration, epic.getDuration(), "Время задачи не изменилось.");

        epic.durationMinusSubtaskDuration(duration);
        assertEquals(Duration.ZERO, epic.getDuration(), "Время задачи не изменилось.");
    }

    @Test
    void changingStartTimeEpic() {
        Epic epic = new Epic("Title", "Disc", LocalDateTime.now(), Duration.ZERO);

        LocalDateTime now = LocalDateTime.now().plusMinutes(20);
        epic.updateStartTimeTask(now);
        assertEquals(now, epic.getStartTime(), "Время начала задачи не изменилось.");
    }
}