package taskmanager.tasks;

import taskmanager.managers.Managers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Subtask extends Task {

    private int epicId;

    public Subtask(String title, String description, StatusTask statusTask, int epicId, LocalDateTime startTime, Duration duration) {
        super(title, description, statusTask, startTime, duration);
        this.epicId = epicId;
    }

    protected static StatusTask getStatusSubtask(Subtask subtask) {
        return subtask.getStatusTask();
    }

    @Override
    public String toStringToFile() {
        String formatDateTime = this.getStartTime().format(Managers.timeFormatter);
        return String.format("SUBTASK,%d,%s,%s,%s,%s,%s,%d\n",
                this.getTaskId(),
                this.getTitle(),
                this.getDescription(),
                this.getStatusTask(),
                formatDateTime,
                this.getDuration().toString(),
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
        return new Subtask(task.getTitle(), task.getDescription(),
                task.getStatusTask(), task.getEpicId(),
                task.getStartTime(), task.getDuration()
        );
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "title='" + this.getTitle() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", taskID=" + this.getTaskId() +
                ", statusTask=" + this.getStatusTask() +
                ", startTime=" + this.getStartTime() +
                ", endTime=" + this.getEndTime() +
                ", duration=" + this.getDuration() +
                ", epicID=" + this.getEpicId() +
                '}';
    }
}
