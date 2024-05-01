package taskmanager.managers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void getDefaultReturnNotNull() {
        TaskManager manager = Managers.getDefault();
        assertNotNull(manager, "Менеджер не найден");
        assertEquals(manager.getClass(), InMemoryTaskManager.class);
    }

    @Test
    void getDefaultHistoryReturnNotNull() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "Менеджер не найден");
        assertEquals(historyManager.getClass(), InMemoryHistoryManager.class);
    }
}
