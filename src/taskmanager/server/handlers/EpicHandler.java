package taskmanager.server.handlers;

import taskmanager.tasks.Epic;

public class EpicHandler extends BaseTaskHandler {

    public EpicHandler(String path) {
        super(path);
    }

    @Override
    String responseGetTasks() {
        return gson.toJson(manager.getEpics());
    }

    @Override
    String responseGetTasksById(int taskId) {
        if (manager.getEpicForId(taskId) == null) {
            return gson.toJson(null);
        }
        return gson.toJson(manager.getEpicForId(taskId));
    }

    @Override
    void responsePostTask(String responseBody) {
        Epic task = gson.fromJson(responseBody, Epic.class);
        manager.setEpics(task.getTaskId(), task);
    }

    @Override
    void responseDeleteTask(int taskId) {
        manager.deleteEpicForID(taskId);
    }
}
