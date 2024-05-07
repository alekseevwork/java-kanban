package taskmanager.exception;

import java.io.IOException;
import java.io.UncheckedIOException;

public class ManagerSaveException extends UncheckedIOException {

    public ManagerSaveException(String message, IOException cause) {
        super(message, cause);
    }
}
