package taskmanager;

import taskmanager.tasks.Task;

import java.util.List;

public interface HistoryManager {
    <T extends Task> void add(T task);
    List<Task> getHistory();
    int size();
    void clear();
}
