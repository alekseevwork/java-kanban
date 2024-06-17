package taskmanager.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import taskmanager.servers.type_adapters.DurationTypeAdapter;
import taskmanager.servers.type_adapters.LocalDateTimeTypeAdapter;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Managers {

    public static InMemoryTaskManager taskManager;
    public static InMemoryHistoryManager historyManager;
    public static FileBackedTaskManager fileBackedManager;

    public static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm");

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

    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
                .create();
    }
}
