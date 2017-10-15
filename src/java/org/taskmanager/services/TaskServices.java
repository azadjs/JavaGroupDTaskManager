package org.taskmanager.services;

import java.time.LocalDateTime;
import java.util.List;
import org.taskmanager.entities.Task;
import org.taskmanager.entities.User;
import org.taskmanager.entities.util.TaskStatuses;

/**
 *
 * @author Elmar H.Mammadov <jrelmarmammadov@gmail.com>
 */
public interface TaskServices {

    boolean createTask(Task task);

    void assignTask(Task task, List<User> users);

    void removeTask(Task task);

    void changeTaskStatus(Task task, TaskStatuses targetStatus);

    void changeDeadline(Task task, LocalDateTime targetDeadline);

    List<Task> getTaskByAuthor(User user);

    List<Task> getTaskByUser(User user);
    
    Task getTaskById(Long taskId);
    
    
    
}
