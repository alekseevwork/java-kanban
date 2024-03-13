public class Main {

    public static void main(String[] args) {
        Task task1 = new Task("Title1", "Desc1", StatusTask.DONE);
        Task task2 = new Task("Title2", "Desc1", StatusTask.IN_PROGRESS);
        TaskManager.setTasks(task1.getTaskID(), task1);
        TaskManager.printOfTypeTasks(TaskManager.tasks);
        TaskManager.printOfTypeTasks(TaskManager.epics);
        TaskManager.setTasks(task1.getTaskID(), task1);
        TaskManager.setTasks(task2.getTaskID(), task2);
        TaskManager.deleteTypeTasks(TaskManager.tasks);
        TaskManager.deleteTypeTasks(TaskManager.subtasks);
        TaskManager.deleteTypeTasks(TaskManager.epics);
        Epic epic = new Epic("TitleE", "EpicDic");
        Subtask sub = new Subtask("Sub", "sada", StatusTask.NEW, epic.getTaskID());
        Subtask sub2 = new Subtask("Sub2", "sada", StatusTask.NEW, epic.getTaskID());
        TaskManager.setEpics(epic.getTaskID(), epic);
        TaskManager.setSubtasks(sub.getTaskID(), sub);
        TaskManager.setSubtasks(epic.getTaskID(), sub2);
        System.out.println(TaskManager.epics);
        TaskManager.printOfTypeTasks(TaskManager.subtasks);





    }
}
