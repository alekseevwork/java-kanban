package taskmanager.tasks;

import java.util.HashMap;

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
                ", statusTask=" + statusTask +
                '}';
    }

    public void checkStatusSubtasks(HashMap<Integer, Subtask> subtasks) {
        int countDone = 0;
        int countIteration = 0;
        for (Subtask subtask : subtasks.values()) {
            if (getTaskId() == subtask.getEpicId()) {
                countIteration++;
                switch (subtask.statusTask) {
                    case StatusTask.NEW:
                        break;
                    case StatusTask.IN_PROGRESS:
                        this.statusTask = StatusTask.IN_PROGRESS;
                        break;
                    case StatusTask.DONE:
                        countDone++;
                        break;
                }
            }
            if (countIteration == 0) {
                this.statusTask = StatusTask.NEW;
            } else if (countIteration == countDone) {
                this.statusTask = StatusTask.DONE;
            }
        }
    }
}
