package org.taskmanager.entities;

import java.time.LocalDateTime;
import org.taskmanager.entities.util.UserRoles;
import static org.taskmanager.entities.util.UserRoles.NONE;
import static org.taskmanager.entities.util.UserRoles.values;

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
        NEW_TASK_ASSIGNED("NEW_TASK_ASSIGNED"), NEW_COMMENT("NEW_COMMMENT"), TASK_STATUS_CHANGED("TASK_STATUS_CHANGED"),NONE("NONE");
        
        private NotificationTypes(String role) {
        this.role = role;
    }

    private String role;

    public String getRole() {
        return role;
    }
    public static NotificationTypes fromValue(String role) {
        for (NotificationTypes n : values()) {
            if (n.getRole().equalsIgnoreCase(role)) {
                return n;
            }
        }
        return NONE;
    }
        
    }

    public enum Status {
        NEW("NEW"), SEEN("SEEN"),NONE("NONE");
        
        private Status(String role) {
        this.role = role;
    }

    private String role;

    public String getRole() {
        return role;
    }
    public static Status fromValue(String role) {
        for (Status s : values()) {
            if (s.getRole().equalsIgnoreCase(role)) {
                return s;
            }
        }
        return NONE;
    }
        
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
