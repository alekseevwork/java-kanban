public class Main {

    public static void main(String[] args) {
        Task task1 = new Task("Title1", "Desc1", StatusTask.DONE);
        TaskManager.setTasks(task1.taskID, task1);
        TaskManager.printOfTypeTasks(TaskManager.tasks);
        TaskManager.printOfTypeTasks(TaskManager.epics);
        TaskManager.setTasks(task1.taskID, task1);
        TaskManager.setTasks(task1.taskID, task1);
        System.out.println(TaskManager.epics);





    }
}
