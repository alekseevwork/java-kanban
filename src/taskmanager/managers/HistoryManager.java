package taskmanager.managers;

import taskmanager.tasks.Task;

import java.util.List;

public interface HistoryManager {
    <T extends Task> void add(T task);

    boolean remove(int id);

    List<Task> getHistory();

    int size();

    void clear();
}
