import java.util.HashMap;

public class TaskManager {
    static HashMap<Integer, Task> tasks = new HashMap<>();
    static HashMap<Integer, Epic> epics = new HashMap<>();
    static HashMap<Integer, Subtask> subtasks = new HashMap<>();

    /*static void printTasks() {
        if (!tasks.isEmpty()) {
            for (Task task : tasks.values()) {
                System.out.println(task);
            }
        } else {
            System.out.println("Список задач пуст.");
        }
    }


    static void deleteAllTasks() {
        tasks.clear();
        System.out.println("Задачи удалены.");
    }

    static void deleteTasksForID(int taskID) {
        tasks.remove(taskID);
        System.out.println("Задача удалена.");
    }

    static void setTasks(int taskID, Task task) {
        if (tasks.containsKey(taskID)) {
            tasks.put(taskID, task);
            System.out.println("Задача изменена.");
        } else {
            tasks.put(taskID, task);
            System.out.println("Задача заведена.");
        }
    }*/

    static void subEpicTaskPrint(int epicID) {
        for (Subtask sub : subtasks.values()) {
            if (epicID == sub.epicID) {
                System.out.println(sub);
            }
        }
    }
    static void printSubTasks() {
        if (!subtasks.isEmpty()) {
            for (Task subtask : subtasks.values()) {
                System.out.println(subtask);
            }
        } else {
            System.out.println("Список задач пуст.");
        }
    }

    static void deleteAllSubTasks() {
        subtasks.clear();
        System.out.println("Задачи удалены.");
    }

    static void deleteSubTasksForID(int taskID) {
        subtasks.remove(taskID);
        System.out.println("Задача удалена.");
    }

    static void setSubtasks(int subtaskID, Subtask task) {
        if (subtasks.containsKey(subtaskID)) {
            subtasks.put(subtaskID, task);
            System.out.println("Задача изменена.");
        } else {
            subtasks.put(subtaskID, task);
            System.out.println("Задача заведена.");
        }
    }

    static void epicTaskPrint(int epicID) {
        for (Subtask sub : subtasks.values()) {
            if (epicID == sub.epicID) {
                System.out.println(sub);
            }
        }
    }
    static void printEpics() {
        if (!subtasks.isEmpty()) {
            for (Task subtask : subtasks.values()) {
                System.out.println(subtask);
            }
        } else {
            System.out.println("Список задач пуст.");
        }
    }

    static void deleteAllEpics() {
        subtasks.clear();
        System.out.println("Задачи удалены.");
    }

    static void deleteEpicForID(int taskID) {
        int epicID = epics.get(taskID).getID();
        if (!epics.isEmpty() && !epics.containsKey(taskID)) {
            epics.remove(taskID);
            System.out.println("Задача удалена.");
            while (subtasks.containsKey(epicID)) {
                deleteSubTasksForID(epicID);
            }
        }
        System.out.println("Связанные подзадачи удалены.");
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
}
