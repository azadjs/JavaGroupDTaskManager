package org.taskmanager.entities;

import java.time.LocalDate;
import java.util.List;
import org.taskmanager.entities.util.TaskStatuses;

/**
 *
 * @author elmarmammadov
 */
public class Task {

    public Task() {
        this.created = LocalDate.now();
    }

    private Long id;
    private String title;
    private String description;
    private User owner;
    private LocalDate created;
    private LocalDate deadline;
    private TaskStatuses status;
    private LocalDate solved;
    private LocalDate assigned;
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

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public TaskStatuses getStatus() {
        return status;
    }

    public void setStatus(TaskStatuses status) {
        this.status = status;
    }

    public LocalDate getSolved() {
        return solved;
    }

    public void setSolved(LocalDate solved) {
        this.solved = solved;
    }

    public LocalDate getAssigned() {
        return assigned;
    }

    public void setAssigned(LocalDate assigned) {
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
