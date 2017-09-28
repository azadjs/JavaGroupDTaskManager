package org.taskmanager.entities;

import java.time.LocalDateTime;

/**
 *
 * @author elmarmammadov
 */
public class Comment {

    public Comment() {
        this.created = LocalDateTime.now();
    }

    private Long id;
    private User commenter;
    private String comment;
    private LocalDateTime created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

}
