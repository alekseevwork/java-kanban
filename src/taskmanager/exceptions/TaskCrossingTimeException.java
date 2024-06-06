package taskmanager.exceptions;

public class TaskCrossingTimeException extends RuntimeException {

    public TaskCrossingTimeException(String message) {
        super(message);
    }
}
