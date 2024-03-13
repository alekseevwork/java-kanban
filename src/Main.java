public class Main {

    public static void main(String[] args) {
        Task task1 = new Task("Title1", "Desc1", StatusTask.DONE);
        Task task2 = new Task("Title2", "Desc1", StatusTask.IN_PROGRESS);

        Epic epic = new Epic("TitleE", "EpicDic");
        Subtask sub = new Subtask("Sub", "sada", StatusTask.NEW, epic.getTaskID());
        Subtask sub2 = new Subtask("Sub2", "sada", StatusTask.IN_PROGRESS, epic.getTaskID());
        TaskManager.setEpics(epic.getTaskID(), epic);
        TaskManager.setSubtasks(sub.getTaskID(), sub);
        TaskManager.setSubtasks(sub2.getTaskID(), sub2);
        System.out.println(TaskManager.epics);
        TaskManager.printOfTypeTasks(TaskManager.subtasks);
        System.out.println(TaskManager.getSubTaskForId(sub.getTaskID()));





    }
}
