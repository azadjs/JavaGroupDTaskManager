package org.taskmanager.entities.util;

/**
 *
 * @author elmarmammadov
 */
public enum TaskStatuses {
    SOLVED("SOLVED"),
    NEW("NEW"),
    ASSIGNED("ASSIGNED"),
    ONGOING("ONGOING"),
    EXPIRED("EXPIRED"),
    CANCELLED("CANCELED"),
    NONE("NONE");

    private TaskStatuses(String status) {
        this.status = status;
    }
    private String status;

    public String getStatus() {
        return status;
    }

    public static TaskStatuses fromValue(String status) {
        for (TaskStatuses ts : values()) {
            if (ts.getStatus().equalsIgnoreCase(status)) {
                return ts;
            }
        }
        return NONE;
    }

}
