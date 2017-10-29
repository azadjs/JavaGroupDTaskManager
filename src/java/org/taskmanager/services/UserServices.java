package org.taskmanager.services;

import java.util.List;
import org.taskmanager.entities.Task;
import org.taskmanager.entities.User;
import org.taskmanager.providers.exceptions.CommentException;
import org.taskmanager.providers.exceptions.UserAuthenticationException;
import org.taskmanager.providers.exceptions.UserException;

/**
 *
 * @author Elmar H.Mammadov <jrelmarmammadov@gmail.com>
 */
public interface UserServices {

    User login(String username, String password) throws UserAuthenticationException;

    void writeComment(User user, Task task) throws CommentException;

    User getUserById(Long userId) throws UserException;

    List<User> getUsersByManager(User manager) throws UserException;

    void register(User user) throws UserException;
}
