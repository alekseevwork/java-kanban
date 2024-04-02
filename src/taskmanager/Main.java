package taskmanager;

import taskmanager.managers.Managers;
import taskmanager.managers.TaskManager;
import taskmanager.tasks.Epic;
import taskmanager.tasks.StatusTask;
import taskmanager.tasks.Subtask;
import taskmanager.tasks.Task;

import static taskmanager.managers.InMemoryTaskManager.historyManager;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        Task task1 = new Task("Title1", "Desc1", StatusTask.NEW);
        Task task2 = new Task("Title2", "Desc1", StatusTask.IN_PROGRESS);
        manager.setTasks(task1.getTaskId(), task1);
        manager.setTasks(task2.getTaskId(), task2);

        Epic epic1 = new Epic("TitleE1", "EpicDic");
        Subtask sub1 = new Subtask("Sub", "sada", StatusTask.NEW, epic1.getTaskId());
        Subtask sub2 = new Subtask("Sub2", "sada", StatusTask.NEW, epic1.getTaskId());
        manager.setEpics(epic1.getTaskId(), epic1);
        manager.setSubtasks(sub1.getTaskId(), sub1);
        manager.setSubtasks(sub2.getTaskId(), sub2);

        Epic epic2 = new Epic("TitleE", "EpicDic");
        Subtask sub3 = new Subtask("Sub3", "sada", StatusTask.NEW, epic2.getTaskId());
        manager.setEpics(epic2.getTaskId(), epic2);
        manager.setSubtasks(sub3.getTaskId(), sub3);

        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());

        Task updateTask1 = new Task("Title1", "Desc1", StatusTask.IN_PROGRESS);
        Task updateTask2 = new Task("Title2", "Desc1", StatusTask.DONE);
        manager.setTasks(task1.getTaskId(), updateTask1);
        manager.setTasks(task2.getTaskId(), updateTask2);
        System.out.println(manager.getTasksForId(task1.getTaskId()));
        System.out.println(manager.getTasksForId(task2.getTaskId()));

        Subtask updateSub1 = new Subtask("Sub1", "sada", StatusTask.DONE, epic1.getTaskId());
        Subtask updateSub2 = new Subtask("Sub2", "sada", StatusTask.IN_PROGRESS, epic1.getTaskId());
        manager.setSubtasks(sub1.getTaskId(), updateSub1);
        manager.setSubtasks(sub2.getTaskId(), updateSub2);
        System.out.println(manager.getEpicForId(epic1.getTaskId()));
        System.out.println(manager.getSubTaskForId(sub1.getTaskId()));

        Subtask updateSub3 = new Subtask("Sub3", "sada", StatusTask.DONE, epic2.getTaskId());
        manager.setSubtasks(sub3.getTaskId(), updateSub3);
        System.out.println(manager.getEpicForId(epic2.getTaskId()));
        System.out.println(manager.getSubTaskForId(sub3.getTaskId()));

        for (Task task : historyManager.getHistory()) {
            System.out.println(task);
        }
        System.out.println(historyManager.getHistory());

        manager.deleteTasksForID(task2.getTaskId());
        manager.deleteEpicForID(epic2.getTaskId());
        manager.deleteSubTasksForID(sub2.getTaskId());
        manager.deleteSubTasksForID(sub1.getTaskId());

        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());

        System.out.println(historyManager.getHistory());
    }
}
