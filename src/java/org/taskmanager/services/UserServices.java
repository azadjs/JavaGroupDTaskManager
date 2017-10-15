package org.taskmanager.services;

import java.util.List;
import org.taskmanager.entities.Task;
import org.taskmanager.entities.User;

/**
 *
 * @author Elmar H.Mammadov <jrelmarmammadov@gmail.com>
 */
public interface UserServices {

    User login(String username, String password);

    boolean register(User user);

    boolean logout(User user);

    boolean writeComment(User user, Task task);

    User getUserById(Long userId);

    List<User> getUsersByManager(User manager);
}
