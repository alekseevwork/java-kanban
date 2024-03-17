import java.util.HashMap;

public class Epic extends Task {

    public Epic(String title, String description) {
        super(title, description, StatusTask.NEW);
    }

    static void checkStatusSubtasks(HashMap<Integer, Subtask> subtasks, Epic epic) {
        int countDone = 0;
        int count = 0;
        for (Subtask subtask : subtasks.values()) {
            if (epic.getTaskID() == subtask.epicID) {
                count++;
                switch (subtask.statusTask) {
                    case NEW:
                        break;
                    case IN_PROGRESS:
                        epic.statusTask = StatusTask.IN_PROGRESS;
                        break;
                    case DONE:
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
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", taskID=" + this.getTaskID() +
                ", statusTask=" + statusTask +
                '}';
    }
}
