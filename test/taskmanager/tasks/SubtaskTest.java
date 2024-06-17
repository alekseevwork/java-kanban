package taskmanager.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    private static Subtask subtask1;
    private static Subtask subtask2;
    private static final Epic epic = new Epic("Epic", "Desc", LocalDateTime.now());


    @BeforeEach
    public void beforeEach() {
        LocalTime start = LocalTime.of(0, 0);
        LocalTime finish = LocalTime.of(0, 3);

        LocalDateTime startTime = LocalDateTime.now();
        Duration duration = Duration.between(start, finish);

        subtask1 = new Subtask(
                "Title", "Desc", StatusTask.NEW, epic.getTaskId(), startTime, duration);
        subtask2 = new Subtask(
                "Title", "Desc", StatusTask.NEW, epic.getTaskId(), startTime, duration);
    }

    @Test
    public void instancesOfSubtaskClassAreEqualToEachOtherIfIdIsEqual() {
        assertEquals(subtask1, subtask2, "Задачи не совпадают.");
    }

    @Test
    public void getEpicIdEqualToIdIsSubtask() {
        assertEquals(epic.getTaskId(), subtask1.getEpicId(), "Не совпадают ID эпика и его подзадачи");
    }

    @Test
    public void getStatusSubtaskCorrect() {
        assertEquals(subtask1.getStatusTask(), StatusTask.NEW, "Не совпадает статус задачи.");
    }

    @Test
    public void setEpicIdCorrect() {
        subtask1.setEpicId(1);
        assertEquals(subtask1.getEpicId(), 1, "Не измениля ID задачи.");
    }
}