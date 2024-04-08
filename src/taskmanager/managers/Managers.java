package taskmanager.managers;

public final class Managers {

    public static InMemoryTaskManager taskManager;
    public static InMemoryHistoryManager historyManager;

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
}
