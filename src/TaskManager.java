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
    }  //print done

    static void subEpicTaskPrint(int epicID) {
        for (Subtask sub : subtasks.values()) {
            if (epicID == sub.epicID) {
                System.out.println(sub);
            }
        }
    }  // print all Subtask in Epic done

    static void deleteTypeTasks(Object object) {
        if (object == tasks) {
            tasks.clear();
        } else if (object == subtasks) {
            subtasks.clear();
        } else if (object == epics) {
            epics.clear();
        }
        System.out.println("Задачи удалены.");
    } // all Tasks delete done

    static void deleteTasksForID(int taskID) {
        tasks.remove(taskID);
        System.out.println("Задача удалена.");
    }         // delete for ID done
    static void deleteSubTasksForID(int taskID) {
        subtasks.remove(taskID);
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
    }             // setTask done
    static void setSubtasks(int subtaskID, Subtask task) {
        if (subtasks.containsKey(subtaskID)) {
            subtasks.put(subtaskID, task);
            System.out.println("Задача изменена.");
        } else {
            subtasks.put(subtaskID, task);
            System.out.println("Задача заведена.");
        }
        Epic.checkStatusSubtasks(subtasks, getEpicForId(task.epicID));
    }  // update status Epic done
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
    }     // get Task for ID done
    public static Subtask getSubTaskForId(int taskID) {
        return subtasks.get(taskID);
    }
    public static Epic getEpicForId(int taskID) {
        return epics.get(taskID);
    }



}
