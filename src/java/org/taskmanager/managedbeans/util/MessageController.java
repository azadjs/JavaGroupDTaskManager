package org.taskmanager.managedbeans.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author elmarmammadov
 */

public class MessageController {

    public void showMessage(String message, FacesMessage.Severity messageLevel) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(messageLevel, message, ""));
    }

}
