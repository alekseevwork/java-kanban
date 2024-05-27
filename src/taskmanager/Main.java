package taskmanager;

import taskmanager.managers.Managers;
import taskmanager.managers.TaskManager;
import taskmanager.tasks.Epic;
import taskmanager.tasks.StatusTask;
import taskmanager.tasks.Subtask;
import taskmanager.tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
//        TaskManager manager = Managers.getDefault();
        Path file = Path.of("resources\\tasksFile.csv");
        TaskManager manager = Managers.getBackedManager(file);

        LocalTime start = LocalTime.of(0, 0);
        LocalTime finish = LocalTime.of(0, 3);

        LocalDateTime startTime = LocalDateTime.now();

        Duration duration = Duration.between(start, finish);

        Task task = new Task("Title", "Desc", StatusTask.NEW, startTime.plusMinutes(100), duration);

        Epic epic1 = new Epic("TitleE1", "EpicDic", startTime.plusMinutes(10), Duration.ZERO);
        Subtask sub1 = new Subtask(
                "Sub1", "sada", StatusTask.NEW, epic1.getTaskId(), startTime.plusMinutes(21), duration
        );
        Subtask sub2 = new Subtask(
                "Sub2", "sada", StatusTask.NEW, epic1.getTaskId(), startTime.plusMinutes(3), duration
        );
        Subtask sub3 = new Subtask(
                "Sub3", "sada", StatusTask.NEW, epic1.getTaskId(), startTime.plusMinutes(8), duration
        );

        manager.setEpics(epic1.getTaskId(), epic1);
        manager.setSubtasks(sub1.getTaskId(), sub1);
        manager.setSubtasks(sub2.getTaskId(), sub2);
        manager.setSubtasks(sub3.getTaskId(), sub3);
        manager.setTasks(task.getTaskId(), task);
        manager.setTasks(task.getTaskId(), task);
        manager.setTasks(1212, null);

        Epic epic2 = new Epic("TitleE", "EpicDic", startTime.plusMinutes(5), Duration.ZERO);
        manager.setEpics(epic2.getTaskId(), epic2);
        System.out.println(manager.getPrioritizedTasks());
        System.out.println(manager.getTasks());

        manager.printOfSubtasksInEpic(epic1.getTaskId());

//        manager.deleteEpicForID(epic1.getTaskId());

    }
}
