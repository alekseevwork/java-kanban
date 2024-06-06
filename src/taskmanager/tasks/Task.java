package taskmanager.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task implements Comparable<Task> {
    private String title;
    private String description;
    private int taskId;
    private StatusTask statusTask;
    private Duration duration;
    private LocalDateTime startTime;

    public Task(String title, String description, StatusTask statusTask, LocalDateTime startTime, Duration duration) {
        this.title = title;
        this.description = description;
        this.statusTask = statusTask;
        this.duration = duration;
        this.startTime = startTime;
        this.taskId = this.hashCode();
    }

    public String toStringToFile() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm");
        String formatDateTime = startTime.format(formatter);
        return String.format("TASK,%d,%s,%s,%s,%s,%s\n",
                taskId,
                title,
                description,
                statusTask,
                formatDateTime,
                duration.toString()
        );
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration.getSeconds() / 60);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public StatusTask getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(StatusTask statusTask) {
        this.statusTask = statusTask;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public static Task copyTask(Task task) {
        return new Task(task.getTitle(), task.getDescription(), task.getStatusTask(), task.startTime, task.duration);
    }

    @Override
    public int compareTo(Task o) {
        if (this.startTime.isAfter(o.startTime)) {
            return 1;
        } else if (this.startTime.isBefore(o.startTime)) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", taskID=" + taskId +
                ", statusTask=" + statusTask +
                ", startTime=" + startTime +
                ", endTime=" + this.getEndTime() +
                ", duration=" + duration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId == task.taskId &&
                Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                statusTask == task.statusTask;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, taskId, statusTask, toStringToFile());
    }

    public void setTitle(String title) {

        this.title = title;
    }
}