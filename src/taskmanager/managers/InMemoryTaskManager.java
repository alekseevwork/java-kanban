package taskmanager.managers;

import taskmanager.tasks.Epic;
import taskmanager.tasks.Subtask;
import taskmanager.tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager{

    static Map<Integer, Task> tasks = new HashMap<>();
    static Map<Integer, Epic> epics = new HashMap<>();
    static Map<Integer, Subtask> subtasks = new HashMap<>();
    public static HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public void printOfSubtasksInEpic(int epicId) {
        for (Subtask sub : subtasks.values()) {
            if (epicId == sub.getEpicId()) {
                System.out.println(sub);
            }
        }
    }

    @Override
    public void deleteTypeTasks(Object object) {
        if (object == tasks) {
            tasks.clear();
        } else if (object == subtasks) {
            subtasks.clear();
        } else if (object == epics) {
            epics.clear();
        }
        System.out.println("Задачи удалены.");
    }

    @Override
    public void deleteTasksForID(int taskId) {
        tasks.remove(taskId);
        System.out.println("Задача удалена.");
    }
    @Override
    public void deleteSubTasksForID(int taskId) {
        int epicId = getSubTaskForId(taskId).getEpicId();
        subtasks.remove(taskId);
        getEpicForId(epicId).checkStatusSubtasks(subtasks);
        System.out.println("Задача удалена.");
    }
    @Override
    public void deleteEpicForID(int taskId) {
        if (!epics.isEmpty() && epics.containsKey(taskId)) {
            epics.remove(taskId);
            System.out.println("Задача удалена.");
            while (subtasks.containsKey(taskId)) {
                deleteSubTasksForID(taskId);
            }
        }
        System.out.println("Связанные подзадачи удалены.");
    }

    @Override
    public void setTasks(int taskId, Task task) {
        if (tasks.containsKey(taskId)) {
            tasks.put(taskId, task);
            System.out.println("Задача изменена.");
        } else {
            tasks.put(taskId, task);
            System.out.println("Задача заведена.");
        }
    }
    @Override
    public void setSubtasks(int subtaskId, Subtask task) {
        if (subtasks.containsKey(subtaskId)) {
            subtasks.put(subtaskId, task);
            System.out.println("Задача изменена.");
        } else {
            subtasks.put(subtaskId, task);
            System.out.println("Задача заведена.");
        }
        epics.get(task.getEpicId()).checkStatusSubtasks(subtasks);
    }
    @Override
    public void setEpics(int taskId, Epic epic) {
        if (epics.containsKey(taskId)) {
            epics.put(taskId, epic);
            System.out.println("Задача изменена.");
        } else {
            epics.put(taskId, epic);
            System.out.println("Задача заведена.");
        }
    }

    @Override
    public Task getTasksForId(int taskId) {
        Task copy = Task.copyTask(tasks.get(taskId));
        historyManager.add(tasks.get(taskId));
        return copy;
    }
    @Override
    public Subtask getSubTaskForId(int taskId) {
        Subtask copy = Subtask.copySubTask(subtasks.get(taskId));
        historyManager.add(subtasks.get(taskId));
        return copy;
    }
    @Override
    public Epic getEpicForId(int taskId) {
        Epic copy = Epic.copyEpic(epics.get(taskId));
        historyManager.add(epics.get(taskId));
        return copy;
    }

    @Override
    public Map<Integer, Task> getTasks() {
        return tasks;
    }
    @Override
    public Map<Integer, Epic> getEpics(){
        return epics;
    }
    @Override
    public Map<Integer, Subtask> getSubtasks(){
        return subtasks;
    }
}
