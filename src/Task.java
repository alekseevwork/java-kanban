import java.util.HashMap;
import java.util.Objects;

public class Task {
    String title;
    String description;
    int taskID;
    private StatusTask statusTask;

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

    public void setStatusTask(StatusTask statusTask) {
        this.statusTask = statusTask;
    }

    static void printTasks(HashMap<Integer, Task> tasks) {
        if (!tasks.isEmpty()) {
            for (Task task : tasks.values()) {
                System.out.println(task);
            }
        } else {
            System.out.println("Список задач пуст.");
        }
    }

    static void deleteAllTasks(HashMap<Integer, Task> tasks) {
        tasks.clear();
        System.out.println("Задачи удалены.");
    }

    static void deleteTasksForID(int taskID, HashMap<Integer, Task> tasks) {
        tasks.remove(taskID);
        System.out.println("Задача удалена.");
    }

    static void setTasks(int taskID, Task task, HashMap<Integer, Task> tasks) {
        if (tasks.containsKey(taskID)) {
            tasks.put(taskID, task);
            System.out.println("Задача изменена.");
        } else {
            tasks.put(taskID, task);
            System.out.println("Задача заведена.");
        }
    }
}
