package org.taskmanager.entities;

import java.time.LocalDateTime;

/**
 *
 * @author Elmar H.Mammadov <jrelmarmammadov@gmail.com>
 */
public class Notification {

    private Long id;
    private String notificationText;
    private User owner;
    private User target;
    private Task task;
    private NotificationTypes notificationType;
    private LocalDateTime created;
    private Status status;

    public enum NotificationTypes {
        NEW_TASK_ASSIGNED, NEW_COMMENT, TASK_STATUS_CHANGED
    }

    public enum Status {
        NEW, SEEN
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public NotificationTypes getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationTypes notificationType) {
        this.notificationType = notificationType;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    

}
