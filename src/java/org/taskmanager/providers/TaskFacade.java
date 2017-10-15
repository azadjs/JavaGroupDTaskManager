package org.taskmanager.providers;

import java.time.LocalDateTime;
import java.util.List;
import org.taskmanager.entities.Task;
import org.taskmanager.entities.User;
import org.taskmanager.entities.util.TaskStatuses;
import org.taskmanager.services.TaskServices;

/**
 *
 * @author Elmar H.Mammadov <jrelmarmammadov@gmail.com>
 */
public class TaskFacade implements TaskServices{

    @Override
    public boolean createTask(Task task) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void assignTask(Task task, List<User> users) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeTask(Task task) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changeTaskStatus(Task task, TaskStatuses targetStatus) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changeDeadline(Task task, LocalDateTime targetDeadline) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Task> getTaskByAuthor(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Task> getTaskByUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Task getTaskById(Long taskId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
