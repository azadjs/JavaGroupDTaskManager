package org.taskmanager.managedbeans.util;

/**
 *
 * @author elmarmammadov
 */
public enum Menus {
    NOTIFICATIONS, MY_TASKS, ASSIGNED_TASKS, MY_EMPLOYEES;

    public static Menus fromVal(String val){
        for(Menus m : values()){
            if(m.toString().equals(val)){
                return m;
            } 
        }
        return NOTIFICATIONS;
    }

}
