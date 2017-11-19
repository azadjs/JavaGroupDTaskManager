package org.taskmanager.managedbeans;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.taskmanager.entities.Task;
import org.taskmanager.managedbeans.util.MessageController;
import org.taskmanager.providers.TaskFacade;
import org.taskmanager.providers.exceptions.CanNotCreateTaskException;
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
    private MessageController messageController;

    private Task task;
    private Date deadline;

    @PostConstruct
    public void init() {
        try {
            task = new Task();
            deadline = new Date();
            taskFacade = new TaskFacade();
            messageController = new MessageController();
        } catch (TaskException e) {
            e.printStackTrace(System.err);
            taskFacade = null;
        } catch (Exception e) {
            e.printStackTrace(System.err);
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

    public void createNewTask() {
        try {
            task.setOwner(loginController.getCurrentUser());
            task.setDeadline(deadline.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            taskFacade.createTask(task);
            messageController.showMessage("Task successfully created", FacesMessage.SEVERITY_INFO);
        } catch (CanNotCreateTaskException ex) {
            ex.printStackTrace(System.err);
            messageController.showMessage("Something went wrong \n" + ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
    
    public void removeTask(Task task){
        try {
            taskFacade.removeTask(task);
        } catch (TaskException e) {
            e.printStackTrace(System.err);
        }
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public TaskServices getTaskFacade() {
        return taskFacade;
    }

    public void setTaskFacade(TaskServices taskFacade) {
        this.taskFacade = taskFacade;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

}
