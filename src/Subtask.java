import java.util.HashMap;

public class Subtask extends Task{

    int epicID;
    public Subtask(String title, String description, StatusTask statusTask, int epicID) {
        super(title, description, statusTask);
        this.epicID = epicID;
    }
}
