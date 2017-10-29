package org.taskmanager.providers.exceptions;

/**
 *
 * @author Elmar H.Mammadov <jrelmarmammadov@gmail.com>
 */
public class UserAuthenticationException extends Exception {

    private static final String DEFAULT_MESSAGE = "User login failed";

    public UserAuthenticationException() {
        super(DEFAULT_MESSAGE);
    }

}
