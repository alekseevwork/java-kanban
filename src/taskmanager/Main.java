package taskmanager;

import taskmanager.managers.Managers;
import taskmanager.managers.TaskManager;
import taskmanager.tasks.Epic;
import taskmanager.tasks.StatusTask;
import taskmanager.tasks.Subtask;
import taskmanager.tasks.Task;

import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        Path file = Path.of("resources\\tasksFile.csv");
        TaskManager manager = Managers.getBackedManager(file);

        Task task = new Task("Title", "Desc", StatusTask.NEW);
        Epic epic1 = new Epic("TitleE1", "EpicDic");
        Subtask sub1 = new Subtask("Sub1", "sada", StatusTask.NEW, epic1.getTaskId());
        Subtask sub2 = new Subtask("Sub2", "sada", StatusTask.NEW, epic1.getTaskId());
        Subtask sub3 = new Subtask("Sub3", "sada", StatusTask.NEW, epic1.getTaskId());

        manager.setEpics(epic1.getTaskId(), epic1);
        manager.setSubtasks(sub1.getTaskId(), sub1);
        manager.setSubtasks(sub2.getTaskId(), sub2);
        manager.setSubtasks(sub3.getTaskId(), sub3);
        manager.setTasks(task.getTaskId(), task);

        Epic epic2 = new Epic("TitleE", "EpicDic");
        manager.setEpics(epic2.getTaskId(), epic2);
    }
}
