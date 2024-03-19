package taskmanager.tasks;

public class Subtask extends Task{

    private final int epicID;
    public Subtask(String title, String description, StatusTask statusTask, int epicID) {
        super(title, description, statusTask);
        this.epicID = epicID;
    }

    protected static StatusTask getStatusSubtask(Subtask subtask) {
        return subtask.statusTask;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "title='" + this.getTitle() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", taskID=" + this.getTaskID() +
                ", statusTask=" + statusTask +
                ", epicID=" + this.getEpicID() +
                '}';
    }

    public int getEpicID() {
        return epicID;
    }
}
