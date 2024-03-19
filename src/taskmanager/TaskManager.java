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

    static void printOfSubtasksInEpic(int epicID) {
        for (Subtask sub : subtasks.values()) {
            if (epicID == sub.getEpicID()) {
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

    static void deleteTasksForID(int taskID) {
        tasks.remove(taskID);
        System.out.println("Задача удалена.");
    }
    static void deleteSubTasksForID(int taskID) {
        int epicID = getSubTaskForId(taskID).getEpicID();
        subtasks.remove(taskID);
        Epic.checkStatusSubtasks(subtasks, getEpicForId(epicID));
        System.out.println("Задача удалена.");
    }
    static void deleteEpicForID(int taskID) {
        int epicID = epics.get(taskID).getTaskID();
        if (!epics.isEmpty() && !epics.containsKey(taskID)) {
            epics.remove(taskID);
            System.out.println("Задача удалена.");
            while (subtasks.containsKey(epicID)) {
                deleteSubTasksForID(epicID);
            }
        }
        System.out.println("Связанные подзадачи удалены.");
    }

    static void setTasks(int taskID, Task task) {
        if (tasks.containsKey(taskID)) {
            tasks.put(taskID, task);
            System.out.println("Задача изменена.");
        } else {
            tasks.put(taskID, task);
            System.out.println("Задача заведена.");
        }
    }
    static void setSubtasks(int subtaskID, Subtask task) {
        if (subtasks.containsKey(subtaskID)) {
            subtasks.put(subtaskID, task);
            System.out.println("Задача изменена.");
        } else {
            subtasks.put(subtaskID, task);
            System.out.println("Задача заведена.");
        }
        Epic.checkStatusSubtasks(subtasks, getEpicForId(task.getEpicID()));
    }
    static void setEpics(int taskID, Epic epic) {
        if (epics.containsKey(taskID)) {
            epics.put(taskID, epic);
            System.out.println("Задача изменена.");
        } else {
            epics.put(taskID, epic);
            System.out.println("Задача заведена.");
        }
    }

    public static Task getTasksForId(int taskID) {
        return tasks.get(taskID);
    }
    public static Subtask getSubTaskForId(int taskID) {
        return subtasks.get(taskID);
    }
    public static Epic getEpicForId(int taskID) {
        return epics.get(taskID);
    }
}
