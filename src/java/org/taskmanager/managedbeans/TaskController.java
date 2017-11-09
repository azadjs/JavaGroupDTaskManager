package org.taskmanager.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.taskmanager.entities.Task;
import org.taskmanager.providers.TaskFacade;
import org.taskmanager.providers.exceptions.TaskException;
import org.taskmanager.services.TaskServices;

/**
 *
 * @author Elmar H.Mammadov <jrelmarmammadov@gmail.com>
 */
@ManagedBean(name = "taskctrl")
@SessionScoped
public class TaskController implements Serializable {

    @ManagedProperty(value = "#{login}")
    private LoginController loginController;

    private TaskServices taskFacade;

    @PostConstruct
    public void init() {
        try {
            taskFacade = new TaskFacade();
        } catch (TaskException e) {
            e.printStackTrace(System.err);
            taskFacade = null;
        }
    }

    public List<Task> getMyTasks() {
        try {
            return taskFacade.getTaskByAuthor(loginController.getCurrentUser());
        } catch (TaskException e) {
            e.printStackTrace(System.err);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return new ArrayList<>();
    }

    public List<Task> getAssignedTasks() {
        try {
            return taskFacade.getTaskByUser(loginController.getCurrentUser());
        } catch (TaskException e) {
            e.printStackTrace(System.err);
        }
        return new ArrayList<>();
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

}
