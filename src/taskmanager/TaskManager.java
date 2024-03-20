package taskmanager;

import taskmanager.tasks.Epic;
import taskmanager.tasks.Subtask;
import taskmanager.tasks.Task;

import java.util.HashMap;

public class TaskManager {
    static HashMap<Integer, Task> tasks = new HashMap<>();
    static HashMap<Integer, Epic> epics = new HashMap<>();
    static HashMap<Integer, Subtask> subtasks = new HashMap<>();

    static void printOfTypeTasks(Object object) {
        if (object == tasks) {
            Task.printTasks(tasks);
        } else if (object == subtasks) {
            Subtask.printTasks(subtasks);
        } else if (object == epics) {
            Epic.printTasks(epics);
        }
    }

    static void printOfSubtasksInEpic(int epicId) {
        for (Subtask sub : subtasks.values()) {
            if (epicId == sub.getEpicId()) {
                System.out.println(sub);
            }
        }
    }

    static void deleteTypeTasks(Object object) {
        if (object == tasks) {
            tasks.clear();
        } else if (object == subtasks) {
            subtasks.clear();
        } else if (object == epics) {
            epics.clear();
        }
        System.out.println("Задачи удалены.");
    }

    static void deleteTasksForID(int taskId) {
        tasks.remove(taskId);
        System.out.println("Задача удалена.");
    }
    static void deleteSubTasksForID(int taskId) {
        int epicId = getSubTaskForId(taskId).getEpicId();
        subtasks.remove(taskId);
        getEpicForId(epicId).checkStatusSubtasks(subtasks);
        System.out.println("Задача удалена.");
    }
    static void deleteEpicForID(int taskId) {
        int epicID = epics.get(taskId).getTaskId();
        if (!epics.isEmpty() && !epics.containsKey(taskId)) {
            epics.remove(taskId);
            System.out.println("Задача удалена.");
            while (subtasks.containsKey(epicID)) {
                deleteSubTasksForID(epicID);
            }
        }
        System.out.println("Связанные подзадачи удалены.");
    }

    static void setTasks(int taskId, Task task) {
        if (tasks.containsKey(taskId)) {
            tasks.put(taskId, task);
            System.out.println("Задача изменена.");
        } else {
            tasks.put(taskId, task);
            System.out.println("Задача заведена.");
        }
    }
    static void setSubtasks(int subtaskId, Subtask task) {
        if (subtasks.containsKey(subtaskId)) {
            subtasks.put(subtaskId, task);
            System.out.println("Задача изменена.");
        } else {
            subtasks.put(subtaskId, task);
            System.out.println("Задача заведена.");
        }
        getEpicForId(task.getEpicId()).checkStatusSubtasks(subtasks);
    }
    static void setEpics(int taskId, Epic epic) {
        if (epics.containsKey(taskId)) {
            epics.put(taskId, epic);
            System.out.println("Задача изменена.");
        } else {
            epics.put(taskId, epic);
            System.out.println("Задача заведена.");
        }
    }

    public static Task getTasksForId(int taskId) {
        return tasks.get(taskId);
    }
    public static Subtask getSubTaskForId(int taskId) {
        return subtasks.get(taskId);
    }
    public static Epic getEpicForId(int taskId) {
        return epics.get(taskId);
    }
}
