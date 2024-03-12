import java.util.HashMap;

public class Epic extends Task {

    public Epic(String title, String description) {
        super(title, description, StatusTask.NEW);
    }

    public int getID() {
        return this.taskID;
    }

    static void printEpics(HashMap<Integer, Epic> epics) {
        if (!epics.isEmpty()) {
            for (Task epic : epics.values()) {
                System.out.println(epic);
            }
        } else {
            System.out.println("Список задач пуст.");
        }
    }
}
