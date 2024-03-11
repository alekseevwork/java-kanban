public class Main {

    public static void main(String[] args) {
        /* Epic epic = new Epic("Title", "desc", TaskManager.getTaskID(), StatusTask.IN_PROGRESS);
        Subtask subtask = new Subtask("Title1", "desc1", TaskManager.getTaskID(),
                StatusTask.IN_PROGRESS, epic.getID());
        //Subtask subtask2 = new Subtask("Title2", "desc2", TaskManager.getTaskID(),
                StatusTask.IN_PROGRESS, epic.getID());*/
        Epic task = new Epic("Title1", "desc");
        Epic task2 = new Epic("Title2", "desc");
        Epic task3 = new Epic("Title3", "desc");


        Task.setTasks(task.taskID, task, TaskManager.tasks);
        Task.setTasks(task2.taskID, task2, TaskManager.tasks);
        Task.setTasks(task3.taskID, task3, TaskManager.tasks);


        Task.printTasks(TaskManager.tasks);
        task.setStatusTask(StatusTask.DONE);
        System.out.println(task);




    }
}
