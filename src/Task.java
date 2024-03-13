import java.util.HashMap;
import java.util.Objects;

public class Task {
    String title;
    String description;
    private final int taskID;
    protected StatusTask statusTask;

    public Task(String title, String description, StatusTask statusTask) {
        this.title = title;
        this.description = description;
        this.taskID = this.hashCode();
        this.statusTask = statusTask;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", taskID=" + taskID +
                ", statusTask=" + statusTask +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskID == task.taskID &&
                Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                statusTask == task.statusTask;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, taskID, statusTask);
    }

    static void printTasks(Object tasks) {
        if (!tasks.toString().equals("{}")) {
            System.out.println(tasks);
        } else {
            System.out.println("Список задач пуст.");
        }
    }

    public int getTaskID() {
        return taskID;
    }


}
