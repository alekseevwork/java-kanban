package taskmanager.tasks;

public class Subtask extends Task {

    private int epicId;

    public Subtask(String title, String description, StatusTask statusTask, int epicId) {
        super(title, description, statusTask);
        this.epicId = epicId;
    }

    protected static StatusTask getStatusSubtask(Subtask subtask) {
        return subtask.getStatusTask();
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "title='" + this.getTitle() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", taskID=" + this.getTaskId() +
                ", statusTask=" + this.getStatusTask() +
                ", epicID=" + this.getEpicId() +
                '}';
    }

    @Override
    public String toStringToFile() {
        return String.format("SUBTASK,%d,%s,%s,%s,%d\n",
                this.getTaskId(),
                this.getTitle(),
                this.getDescription(),
                this.getStatusTask(),
                this.epicId
        );
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public static Subtask copySubTask(Subtask task) {
        return new Subtask(task.getTitle(), task.getDescription(), task.getStatusTask(), task.getEpicId());
    }
}
