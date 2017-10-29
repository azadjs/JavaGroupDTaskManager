package org.taskmanager.providers.exceptions;

/**
 *
 * @author Elmar H.Mammadov <jrelmarmammadov@gmail.com>
 */
public class CanNotChangeTaskException extends Exception {

    private static final String DEFAULT_MESSAGE = "Can not change task";

    public CanNotChangeTaskException() {
        super(DEFAULT_MESSAGE);
    }

    public CanNotChangeTaskException(String message) {
        super(DEFAULT_MESSAGE.concat("\n").concat(message));
    }

    public CanNotChangeTaskException(String message, Throwable cause) {
        super(DEFAULT_MESSAGE.concat("\n").concat(message), cause);
    }

}
