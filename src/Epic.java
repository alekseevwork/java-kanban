public class Epic extends Task {

    public Epic(String title, String description) {
        super(title, description, StatusTask.NEW);
    }

    public int getID() {
        return this.taskID;
    }
}
