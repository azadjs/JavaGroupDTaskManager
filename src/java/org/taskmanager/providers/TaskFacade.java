package org.taskmanager.providers;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.taskmanager.entities.Comment;
import org.taskmanager.entities.Task;
import org.taskmanager.entities.User;
import org.taskmanager.entities.util.TaskStatuses;
import org.taskmanager.services.TaskServices;

/**
 *
 * @author Elmar H.Mammadov <jrelmarmammadov@gmail.com>
 */
public class TaskFacade implements TaskServices {

    private DataHelper dataHelper;
    private DataSource dataSource;

    public TaskFacade() {
        try {
            Context c = new InitialContext();
            dataSource = (DataSource) c.lookup("java:/comp/env/jdbc/groupd");
            dataHelper = new DataHelper(dataSource);
        } catch (NamingException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void createTask(Task task) {
        try {
            dataHelper.connect();
            dataHelper.insertTask(task);
            dataHelper.insertResponsibleUsers(task);
            dataHelper.commitChanges();
        } catch (SQLException e) {
            try {
                dataHelper.rollbackChanges();
            } catch (SQLException e2) {
                e2.printStackTrace(System.err);
            }
            e.printStackTrace(System.err);
        } finally {
            dataHelper.disconnect();
        }
    }

    @Override
    public void assignTask(Task task, List<User> users) {
        try {
            dataHelper.connect();
            for (User u : users) {
                dataHelper.assignTask(u.getId(), task.getId());
            }
            dataHelper.commitChanges();
        } catch (SQLException | NullPointerException e) {
            try {
                dataHelper.rollbackChanges();
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
            }
            e.printStackTrace(System.err);
        } finally {
            dataHelper.disconnect();
        }
    }

    @Override
    public void removeTask(Task task) {
        try {
            dataHelper.connect();
            for (Comment c : task.getComments()) {
                dataHelper.removeComment(c.getId());
            }
            dataHelper.removeTask(task);
            dataHelper.commitChanges();
        } catch (SQLException e) {
            try {
                dataHelper.rollbackChanges();
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
            }
            e.printStackTrace(System.err);
        } finally {
            dataHelper.disconnect();
        }
    }

    @Override
    public void changeTaskStatus(Task task, TaskStatuses targetStatus) {
        try {
            dataHelper.connect();
            task.setStatus(targetStatus);
            dataHelper.updateTask(task);
            dataHelper.commitChanges();
        } catch (SQLException e) {
            try {
                dataHelper.rollbackChanges();
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
            }
            e.printStackTrace(System.err);
        } finally {
            dataHelper.disconnect();
        }
    }

    @Override
    public void changeDeadline(Task task, LocalDateTime targetDeadline) {

    }

    @Override
    public List<Task> getTaskByAuthor(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Task> getTaskByUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Task getTaskById(Long taskId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
