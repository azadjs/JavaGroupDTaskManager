package org.taskmanager.providers.exceptions;

/**
 *
 * @author Elmar H.Mammadov <jrelmarmammadov@gmail.com>
 */
public class UserException extends Exception {

    private static final String DEFAULT_MESSAGE = "Can not create UserFacade";

    public UserException() {
        super(DEFAULT_MESSAGE);
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

}
