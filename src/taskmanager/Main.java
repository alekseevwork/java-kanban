package taskmanager;

import taskmanager.managers.Managers;
import taskmanager.managers.TaskManager;
import taskmanager.tasks.Epic;
import taskmanager.tasks.StatusTask;
import taskmanager.tasks.Subtask;

import static taskmanager.managers.InMemoryTaskManager.historyManager;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        Epic epic1 = new Epic("TitleE1", "EpicDic");
        Subtask sub1 = new Subtask("Sub1", "sada", StatusTask.NEW, epic1.getTaskId());
        Subtask sub2 = new Subtask("Sub2", "sada", StatusTask.NEW, epic1.getTaskId());
        Subtask sub3 = new Subtask("Sub3", "sada", StatusTask.NEW, epic1.getTaskId());

        manager.setEpics(epic1.getTaskId(), epic1);
        manager.setSubtasks(sub1.getTaskId(), sub1);
        manager.setSubtasks(sub2.getTaskId(), sub2);
        manager.setSubtasks(sub3.getTaskId(), sub3);

        Epic epic2 = new Epic("TitleE", "EpicDic");
        manager.setEpics(epic2.getTaskId(), epic2);

        manager.getEpicForId(epic1.getTaskId());
        System.out.println(historyManager.getHistory());

        manager.getEpicForId(epic2.getTaskId());
        System.out.println(historyManager.getHistory());

        manager.getEpicForId(epic1.getTaskId());
        System.out.println(historyManager.getHistory());
    }
}
