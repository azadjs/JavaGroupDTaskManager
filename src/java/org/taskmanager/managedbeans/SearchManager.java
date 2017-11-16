package org.taskmanager.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.taskmanager.entities.Task;
import org.taskmanager.providers.TaskFacade;
import org.taskmanager.providers.exceptions.TaskException;
import org.taskmanager.services.TaskServices;

/**
 *
 * @author elmarmammadov
 */
@ManagedBean
@ViewScoped
public class SearchManager implements Serializable {

    private TaskServices taskService;

    private String taskKeyword;

    List<Task> resultedTask;

    @PostConstruct
    public void init() {
        resultedTask = new ArrayList<>();
        try {
            taskService = new TaskFacade();
        } catch (TaskException e) {
            e.printStackTrace(System.err);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public void search() {
        try {
            if (taskKeyword == null || taskKeyword.isEmpty()) {
                resultedTask.clear();
            } else {
                resultedTask = taskService.searchTask(taskKeyword);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public String getTaskKeyword() {
        return taskKeyword;
    }

    public void setTaskKeyword(String taskKeyword) {
        this.taskKeyword = taskKeyword;
    }

    public List<Task> getResultedTask() {
        return resultedTask;
    }

    public void setResultedTask(List<Task> resultedTask) {
        this.resultedTask = resultedTask;
    }

}
