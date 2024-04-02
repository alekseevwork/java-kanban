package taskmanager.managers;

import taskmanager.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    static List<Task> history = new ArrayList<>();

    @Override
    public List<Task> getHistory()  {
        return history;
    }

    @Override
    public <T extends Task> void add(T task) {
        if (history.size() < 10) {
            history.add(task);
        } else {
            history.removeFirst();
            history.add(task);
        }
    }

    @Override
    public int size() {
        return history.size();
    }

    @Override
    public void clear() {
        history.clear();
    }
}
