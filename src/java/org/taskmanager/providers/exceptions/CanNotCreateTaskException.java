package org.taskmanager.providers.exceptions;

/**
 *
 * @author Elmar H.Mammadov <jrelmarmammadov@gmail.com>
 */
public class CanNotCreateTaskException extends Exception {
    
    private static final String DEFAULT_MESSAGE = "Can not create task";

    public CanNotCreateTaskException() {
        super(DEFAULT_MESSAGE);
    }

    public CanNotCreateTaskException(String message) {
        super(DEFAULT_MESSAGE.concat("\n").concat(message));
    }

    public CanNotCreateTaskException(String message, Throwable cause) {
        super(DEFAULT_MESSAGE.concat("\n").concat(message), cause);
    }

}
