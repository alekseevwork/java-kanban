package taskmanager.tasks;

import java.util.HashMap;

public class Epic extends Task {

    public Epic(String title, String description) {
        super(title, description, StatusTask.NEW);
    }

    public static void checkStatusSubtasks(HashMap<Integer, Subtask> subtasks, Epic epic) {
        int countDone = 0;
        int count = 0;
        for (Subtask subtask : subtasks.values()) {
            if (epic.getTaskID() == subtask.getEpicID()) {
                count++;
                switch (subtask.statusTask) {
                    case StatusTask.NEW:
                        break;
                    case StatusTask.IN_PROGRESS:
                        epic.statusTask = StatusTask.IN_PROGRESS;
                        break;
                    case StatusTask.DONE:
                        countDone++;
                         break;
                }
            }
            if (count == 0) {
                epic.statusTask = StatusTask.NEW;
            } else if (count == countDone) {
                epic.statusTask = StatusTask.DONE;
            }
        }
    }

    @Override
    public String toString() {
        return "Epic{" +
                "title='" + this.getTitle() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", taskID=" + this.getTaskID() +
                ", statusTask=" + statusTask +
                '}';
    }
}
