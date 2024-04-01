package taskmanager.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    private static Subtask subtask1;
    private static Subtask subtask2;
    private static final Epic epic = new Epic("Epic", "Desc");


    @BeforeEach
    public void beforeEach() {
        subtask1 = new Subtask("Title", "Desc", StatusTask.NEW, epic.getTaskId());
        subtask2 = new Subtask("Title", "Desc", StatusTask.NEW, epic.getTaskId());
    }

    @Test
    public void instancesOfSubtaskClassAreEqualToEachOtherIfIdIsEqual() {
        assertEquals(subtask1, subtask2, "Задачи не совпадают.");
    }

    @Test
    public void getEpicIdEqualToIdIsSubtask() {
        assertEquals(epic.getTaskId(), subtask1.getEpicId(), "Не совпадаюе ID эпика и его подзадачи");
    }
}