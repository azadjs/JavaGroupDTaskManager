package org.taskmanager.entities.util;

/**
 *
 * @author elmarmammadov
 */
public enum UserRoles {

    MANAGER("MANAGER"), EMPLOYEE("EMPLOYEE"), NONE("NONE");

    private UserRoles(String role) {
        this.role = role;
    }

    private String role;

    public String getRole() {
        return role;
    }

    public static UserRoles fromValue(String role) {
        for (UserRoles us : values()) {
            if (us.getRole().equalsIgnoreCase(role)) {
                return us;
            }
        }
        return NONE;
    }

}
