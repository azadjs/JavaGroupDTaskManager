package org.taskmanager.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.taskmanager.entities.util.TaskStatuses;

/**
 *
 * @author elmarmammadov
 */
public class Task {

    public Task() {
        this.created = LocalDateTime.now();
    }

    private Long id;
    private String title;
    private String description;
    private User owner;
    private LocalDateTime created;
    private LocalDateTime deadline;
    private TaskStatuses status;
    private LocalDateTime solved;
    private LocalDateTime assigned;
    private List<User> responsibles;
    private List<Comment> comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public TaskStatuses getStatus() {
        return status;
    }

    public void setStatus(TaskStatuses status) {
        this.status = status;
    }

    public LocalDateTime getSolved() {
        return solved;
    }

    public void setSolved(LocalDateTime solved) {
        this.solved = solved;
    }

    public LocalDateTime getAssigned() {
        return assigned;
    }

    public void setAssigned(LocalDateTime assigned) {
        this.assigned = assigned;
    }

    public List<User> getResponsibles() {
        return responsibles;
    }

    public void setResponsibles(List<User> responsibles) {
        this.responsibles = responsibles;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
