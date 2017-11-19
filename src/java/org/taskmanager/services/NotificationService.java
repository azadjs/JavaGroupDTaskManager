package org.taskmanager.services;

import java.util.List;
import org.taskmanager.entities.Notification;
import org.taskmanager.entities.Task;
import org.taskmanager.entities.User;

/**
 *
 * @author Elmar H.Mammadov <jrelmarmammadov@gmail.com>
 */
public interface NotificationService {

    public Notification findById();

    public List<Notification> findAll();

    public List<Notification> findAll(User user);

    public List<Notification> findAll(Task task);

    public void merge(Notification notification);

    public void remove(Long id);

    public void add(Notification notification);
    
    
    
}
