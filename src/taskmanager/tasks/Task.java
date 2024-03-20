package taskmanager.tasks;

import java.util.Objects;

public class Task {
    private final String title;
    private final String description;
    private final int taskId;
    protected StatusTask statusTask;

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

    public static void printTasks(Object tasks) {
        if (!tasks.toString().equals("{}")) {
            System.out.println(tasks);
        } else {
            System.out.println("Список задач пуст.");
        }
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
}
