package taskmanager.managers;

import taskmanager.exception.ManagerSaveException;

import java.io.IOException;

public final class Managers {

    public static InMemoryTaskManager taskManager;
    public static InMemoryHistoryManager historyManager;
    public static FileBackedTaskManager fileBackedManager;

    public static TaskManager getDefault() {
        if (taskManager == null) {
            taskManager = new InMemoryTaskManager();
        }
        return taskManager;
    }

    public static HistoryManager getDefaultHistory() {
        if (historyManager == null) {
            historyManager = new InMemoryHistoryManager();
        }
        return historyManager;
    }

    public static TaskManager getBackedManager() {
        if (fileBackedManager == null) {
            try {
                fileBackedManager = new FileBackedTaskManager();
            } catch (IOException e) {
                throw new ManagerSaveException(e.getMessage(), (IOException) e.getCause());
            }
        }
        return fileBackedManager;
    }
}
