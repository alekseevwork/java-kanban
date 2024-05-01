package taskmanager.tasks;

import java.util.Map;

public class Epic extends Task {

    public Epic(String title, String description) {
        super(title, description, StatusTask.NEW);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "title='" + this.getTitle() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", taskID=" + this.getTaskId() +
                ", statusTask=" + this.getStatusTask() +
                '}';
    }

    public void checkStatusSubtasks(Map<Integer, Subtask> subtasks) {
        int countDone = 0;
        int countIteration = 0;
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
        Epic copy = new Epic(task.getTitle(), task.getDescription());
        copy.setStatusTask(task.getStatusTask());
        return copy;
    }
}
