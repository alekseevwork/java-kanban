package taskmanager.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class Epic extends Task {

    public Epic(String title, String description, LocalDateTime startTime, Duration duration) {
        super(title, description, StatusTask.NEW, startTime, duration);
    }

    @Override
    public String toStringToFile() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm");
        String formatDateTime = this.getStartTime().format(formatter);
        return String.format("EPIC,%d,%s,%s,%s,%s,%s\n",
                this.getTaskId(),
                this.getTitle(),
                this.getDescription(),
                this.getStatusTask(),
                formatDateTime,
                this.getDuration().toString()
        );
    }

    public void checkStatusSubtasks(Map<Integer, Subtask> subtasks) {
        int countDone = 0;
        int countIteration = 0;
        if (subtasks.isEmpty()) {
            this.setStatusTask(StatusTask.NEW);
            return;
        }
        for (Subtask subtask : subtasks.values()) {
            if (getTaskId() == subtask.getEpicId()) {
                countIteration++;
                switch (subtask.getStatusTask()) {
                    case StatusTask.NEW:
                        break;
                    case StatusTask.IN_PROGRESS:
                        this.setStatusTask(StatusTask.IN_PROGRESS);
                        break;
                    case StatusTask.DONE:
                        countDone++;
                        break;
                }
            }
            if (countIteration == 0) {
                this.setStatusTask(StatusTask.NEW);
            } else if (countIteration == countDone) {
                this.setStatusTask(StatusTask.DONE);
            }
        }
    }

    public static Epic copyEpic(Epic task) {
        Epic copy = new Epic(task.getTitle(), task.getDescription(), task.getStartTime(), task.getDuration());
        copy.setStatusTask(task.getStatusTask());
        return copy;
    }

    public void durationPlusSubtaskDuration(Duration subDuration) {
        Duration now = this.getDuration().plus(subDuration);
        setDuration(now);
    }

    public void durationMinusSubtaskDuration(Duration subDuration) {
        Duration now = this.getDuration().minus(subDuration);
        setDuration(now);
    }

    public void updateStartTimeTask(LocalDateTime time) {
        if (getStartTime().isBefore(time)) {
            setStartTime(time);
        }
    }

    public void setLowStartTimeSubtaskForEpic(List<Subtask> subtaskList) {
        setStartTime(subtaskList.getFirst().getStartTime());
    }

    @Override
    public String toString() {
        return "Epic{" +
                "title='" + this.getTitle() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", taskID=" + this.getTaskId() +
                ", statusTask=" + this.getStatusTask() +
                ", startTime=" + this.getStartTime() +
                ", endTime=" + this.getEndTime() +
                ", duration=" + this.getDuration() +
                '}';
    }
}
