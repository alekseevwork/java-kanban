package taskmanager;

import taskmanager.tasks.Epic;
import taskmanager.tasks.StatusTask;
import taskmanager.tasks.Subtask;
import taskmanager.tasks.Task;

public class Main {

    public static void main(String[] args) {
        Task task1 = new Task("Title1", "Desc1", StatusTask.NEW);
        Task task2 = new Task("Title2", "Desc1", StatusTask.IN_PROGRESS);
        TaskManager.setTasks(task1.getTaskId(), task1);
        TaskManager.setTasks(task2.getTaskId(), task2);

        Epic epic1 = new Epic("TitleE1", "EpicDic");
        Subtask sub1 = new Subtask("Sub", "sada", StatusTask.NEW, epic1.getTaskId());
        Subtask sub2 = new Subtask("Sub2", "sada", StatusTask.NEW, epic1.getTaskId());
        TaskManager.setEpics(epic1.getTaskId(), epic1);
        TaskManager.setSubtasks(sub1.getTaskId(), sub1);
        TaskManager.setSubtasks(sub2.getTaskId(), sub2);

        Epic epic2 = new Epic("TitleE", "EpicDic");
        Subtask sub3 = new Subtask("Sub3", "sada", StatusTask.NEW, epic2.getTaskId());
        TaskManager.setEpics(epic2.getTaskId(), epic2);
        TaskManager.setSubtasks(sub3.getTaskId(), sub3);

        System.out.println(TaskManager.tasks);
        System.out.println(TaskManager.epics);
        System.out.println(TaskManager.subtasks);

        Task updateTask1 = new Task("Title1", "Desc1", StatusTask.IN_PROGRESS);
        Task updateTask2 = new Task("Title2", "Desc1", StatusTask.DONE);
        TaskManager.setTasks(task1.getTaskId(), updateTask1);
        TaskManager.setTasks(task2.getTaskId(), updateTask2);
        System.out.println(TaskManager.getTasksForId(task1.getTaskId()));
        System.out.println(TaskManager.getTasksForId(task2.getTaskId()));

        Subtask updateSub1 = new Subtask("Sub1", "sada", StatusTask.DONE, epic1.getTaskId());
        Subtask updateSub2 = new Subtask("Sub2", "sada", StatusTask.IN_PROGRESS, epic1.getTaskId());
        TaskManager.setSubtasks(sub1.getTaskId(), updateSub1);
        TaskManager.setSubtasks(sub2.getTaskId(), updateSub2);
        System.out.println(TaskManager.getEpicForId(epic1.getTaskId()));
        System.out.println(TaskManager.getSubTaskForId(sub1.getTaskId()));

        Subtask updateSub3 = new Subtask("Sub3", "sada", StatusTask.DONE, epic2.getTaskId());
        TaskManager.setSubtasks(sub3.getTaskId(), updateSub3);
        System.out.println(TaskManager.getEpicForId(epic2.getTaskId()));
        System.out.println(TaskManager.getSubTaskForId(sub3.getTaskId()));

        TaskManager.deleteTasksForID(task2.getTaskId());
        TaskManager.deleteEpicForID(epic2.getTaskId());
        TaskManager.deleteSubTasksForID(sub2.getTaskId());
        TaskManager.deleteSubTasksForID(sub1.getTaskId());

        TaskManager.printOfTypeTasks(TaskManager.tasks);
        TaskManager.printOfTypeTasks(TaskManager.epics);
        TaskManager.printOfTypeTasks(TaskManager.subtasks);
    }
}
