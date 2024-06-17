package taskmanager.servers.handlers;

import taskmanager.tasks.Subtask;

public class SubtaskHandler extends BaseTaskHandler {

    public SubtaskHandler(String path) {
        super(path);
    }

    @Override
    String responseGetTasks() {
        return gson.toJson(manager.getSubtasks());
    }

    @Override
    String responseGetTasksById(int taskId) {
        if (manager.getSubTaskForId(taskId) == null) {
            return gson.toJson(null);
        }
        return gson.toJson(manager.getSubTaskForId(taskId));
    }

    @Override
    void responsePostTask(String responseBody) {
        Subtask task = gson.fromJson(responseBody, Subtask.class);
        manager.setSubtasks(task.getTaskId(), task);
    }

    @Override
    void responseDeleteTask(int taskId) {
        manager.deleteSubTasksForID(taskId);
    }
}
