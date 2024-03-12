import java.util.HashMap;

public class Subtask extends Task{

    int epicID;
    public Subtask(String title, String description, StatusTask statusTask, int epicID) {
        super(title, description, statusTask);
        this.epicID = epicID;
    }

    static void printSubTasks(HashMap<Integer, Subtask> subtasks) {
        if (!subtasks.isEmpty()) {
            for (Task task : subtasks.values()) {
                System.out.println(task);
            }
        } else {
            System.out.println("Список задач пуст.");
        }
    }
}
