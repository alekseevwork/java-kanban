package taskmanager.managers;

import java.nio.file.Path;

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

    public static TaskManager getBackedManager(Path file) {
        if (fileBackedManager == null) {
            fileBackedManager = FileBackedTaskManager.loadFromFile(file);
        }
        return fileBackedManager;
    }
}
