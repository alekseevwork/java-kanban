package taskmanager.managers;

import taskmanager.tasks.Epic;
import taskmanager.tasks.Subtask;
import taskmanager.tasks.Task;

import java.util.Map;

public interface TaskManager {

    void printOfSubtasksInEpic(int epicId);

    void deleteTypeTasks(Object object);

    void deleteTasksForID(int taskId);
    void deleteSubTasksForID(int taskId);
    void deleteEpicForID(int taskId);

    void setTasks(int taskID, Task task);
    void setSubtasks(int subtaskID, Subtask task);
    void setEpics(int taskID, Epic epic);

    Task getTasksForId(int taskId);
    Subtask getSubTaskForId(int taskId);
    Epic getEpicForId(int taskId);

    Map<Integer, Task> getTasks();
    Map<Integer, Epic> getEpics();
    Map<Integer, Subtask> getSubtasks();
}
