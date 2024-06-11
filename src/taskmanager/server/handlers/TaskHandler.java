package taskmanager.server.handlers;

import taskmanager.tasks.StatusTask;
import taskmanager.tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;

public class TaskHandler extends BaseHttpHandler {

    public TaskHandler(String path) {
        super(path);
    }

    void startTest() {   // delete later
        System.out.println("startTest");
        LocalDateTime startTime = LocalDateTime.now();
        Task task = new Task("Title", "Desc", StatusTask.NEW,
                startTime.plusMinutes(100), Duration.ofMinutes(3));
        manager.setTasks(task.getTaskId(), task);
    }
    @Override
    String responseGetTasks () {   // override later
        startTest();  // delete later
        return gson.toJson(manager.getTasks());
    }
}