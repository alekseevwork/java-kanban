package taskmanager;

import taskmanager.tasks.Epic;
import taskmanager.tasks.StatusTask;
import taskmanager.tasks.Subtask;
import taskmanager.tasks.Task;

public class Main {

    public static void main(String[] args) {
        Task task1 = new Task("Title1", "Desc1", StatusTask.NEW);
        Task task2 = new Task("Title2", "Desc1", StatusTask.IN_PROGRESS);
        TaskManager.setTasks(task1.getTaskID(), task1);
        TaskManager.setTasks(task2.getTaskID(), task2);

        Epic epic1 = new Epic("TitleE1", "EpicDic");
        Subtask sub1 = new Subtask("Sub", "sada", StatusTask.NEW, epic1.getTaskID());
        Subtask sub2 = new Subtask("Sub2", "sada", StatusTask.NEW, epic1.getTaskID());
        TaskManager.setEpics(epic1.getTaskID(), epic1);
        TaskManager.setSubtasks(sub1.getTaskID(), sub1);
        TaskManager.setSubtasks(sub2.getTaskID(), sub2);

        Epic epic2 = new Epic("TitleE", "EpicDic");
        Subtask sub3 = new Subtask("Sub3", "sada", StatusTask.NEW, epic2.getTaskID());
        TaskManager.setEpics(epic2.getTaskID(), epic2);
        TaskManager.setSubtasks(sub3.getTaskID(), sub3);

        System.out.println(TaskManager.tasks);
        System.out.println(TaskManager.epics);
        System.out.println(TaskManager.subtasks);

        Task updateTask1 = new Task("Title1", "Desc1", StatusTask.IN_PROGRESS);
        Task updateTask2 = new Task("Title2", "Desc1", StatusTask.DONE);
        TaskManager.setTasks(task1.getTaskID(), updateTask1);
        TaskManager.setTasks(task2.getTaskID(), updateTask2);
        System.out.println(TaskManager.getTasksForId(task1.getTaskID()));
        System.out.println(TaskManager.getTasksForId(task2.getTaskID()));

        Subtask updateSub1 = new Subtask("Sub1", "sada", StatusTask.DONE, epic1.getTaskID());
        Subtask updateSub2 = new Subtask("Sub2", "sada", StatusTask.IN_PROGRESS, epic1.getTaskID());
        TaskManager.setSubtasks(sub1.getTaskID(), updateSub1);
        TaskManager.setSubtasks(sub2.getTaskID(), updateSub2);
        System.out.println(TaskManager.getEpicForId(epic1.getTaskID()));
        System.out.println(TaskManager.getSubTaskForId(sub1.getTaskID()));

        Subtask updateSub3 = new Subtask("Sub3", "sada", StatusTask.DONE, epic2.getTaskID());
        TaskManager.setSubtasks(sub3.getTaskID(), updateSub3);
        System.out.println(TaskManager.getEpicForId(epic2.getTaskID()));
        System.out.println(TaskManager.getSubTaskForId(sub3.getTaskID()));

        TaskManager.deleteTasksForID(task2.getTaskID());
        TaskManager.deleteEpicForID(epic2.getTaskID());
        TaskManager.deleteSubTasksForID(sub2.getTaskID());
        TaskManager.deleteSubTasksForID(sub1.getTaskID());

        TaskManager.printOfTypeTasks(TaskManager.tasks);
        TaskManager.printOfTypeTasks(TaskManager.epics);
        TaskManager.printOfTypeTasks(TaskManager.subtasks);
    }
}
