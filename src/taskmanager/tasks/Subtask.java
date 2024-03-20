package taskmanager.tasks;

public class Subtask extends Task{

    private final int epicId;
    public Subtask(String title, String description, StatusTask statusTask, int epicId) {
        super(title, description, statusTask);
        this.epicId = epicId;
    }

    protected static StatusTask getStatusSubtask(Subtask subtask) {
        return subtask.statusTask;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "title='" + this.getTitle() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", taskID=" + this.getTaskId() +
                ", statusTask=" + statusTask +
                ", epicID=" + this.getEpicId() +
                '}';
    }

    public int getEpicId() {
        return epicId;
    }
}
