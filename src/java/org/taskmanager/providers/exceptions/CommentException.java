package org.taskmanager.providers.exceptions;

/**
 *
 * @author Elmar H.Mammadov <jrelmarmammadov@gmail.com>
 */
public class CommentException extends Exception {

    public CommentException(String message) {
        super(message);
    }

    public CommentException(String message, Throwable cause) {
        super(message, cause);
    }

}
