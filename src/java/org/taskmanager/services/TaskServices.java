package org.taskmanager.services;

import java.time.LocalDateTime;
import java.util.List;
import org.taskmanager.entities.Task;
import org.taskmanager.entities.User;
import org.taskmanager.entities.util.TaskStatuses;
import org.taskmanager.providers.exceptions.CanNotChangeTaskException;
import org.taskmanager.providers.exceptions.CanNotCreateTaskException;
import org.taskmanager.providers.exceptions.TaskException;

/**
 *
 * @author Elmar H.Mammadov <jrelmarmammadov@gmail.com>
 */
public interface TaskServices {

    void createTask(Task task) throws CanNotCreateTaskException;

    void assignTask(Task task, List<User> users) throws CanNotChangeTaskException;

    void removeTask(Task task) throws TaskException;

    void changeTaskStatus(Task task, TaskStatuses targetStatus) throws CanNotChangeTaskException;

    void changeDeadline(Task task, LocalDateTime targetDeadline) throws CanNotChangeTaskException;

    List<Task> getTaskByAuthor(User user) throws TaskException;

    List<Task> getTaskByUser(User user) throws TaskException;
    
    List<Task> searchTask(String keyword) throws TaskException;

    Task getTaskById(Long taskId) throws TaskException;
    
    

}
