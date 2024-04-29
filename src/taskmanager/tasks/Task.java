package taskmanager.tasks;

import java.util.Objects;

public class Task {
    private String title;
    private String description;
    private int taskId;
    private StatusTask statusTask;

    public Task(String title, String description, StatusTask statusTask) {
        this.title = title;
        this.description = description;
        this.taskId = this.hashCode();
        this.statusTask = statusTask;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", taskID=" + taskId +
                ", statusTask=" + statusTask +
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
        return Objects.hash(title, description, taskId, statusTask);
    }

    public void setTitle(String title) {

        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public static Task copyTask(Task task) {
        return new Task(task.getTitle(), task.getDescription(), task.getStatusTask());
    }
}