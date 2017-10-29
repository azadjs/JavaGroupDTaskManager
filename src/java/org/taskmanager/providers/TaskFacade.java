package org.taskmanager.providers;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.taskmanager.entities.Comment;
import org.taskmanager.entities.Task;
import org.taskmanager.entities.User;
import org.taskmanager.entities.util.TaskStatuses;
import org.taskmanager.providers.exceptions.CanNotChangeTaskException;
import org.taskmanager.providers.exceptions.CanNotCreateTaskException;
import org.taskmanager.providers.exceptions.TaskException;
import org.taskmanager.services.TaskServices;

/**
 *
 * @author Elmar H.Mammadov <jrelmarmammadov@gmail.com>
 */
public class TaskFacade implements TaskServices {

    private DataHelper dataHelper;
    private DataSource dataSource;

    public TaskFacade() throws TaskException {
        try {
            Context c = new InitialContext();
            dataSource = (DataSource) c.lookup("java:/comp/env/jdbc/groupd");
            dataHelper = new DataHelper(dataSource);
        } catch (NamingException e) {
            e.printStackTrace(System.err);
            throw new TaskException();
        }
    }

    @Override
    public void createTask(Task task) throws CanNotCreateTaskException {
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
            throw new CanNotCreateTaskException(e.getMessage());
        } finally {
            dataHelper.disconnect();
        }
    }

    @Override
    public void assignTask(Task task, List<User> users) throws CanNotChangeTaskException {
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
            throw new CanNotChangeTaskException("Can not assign task " + task + users);
        } finally {
            dataHelper.disconnect();
        }
    }

    @Override
    public void removeTask(Task task) throws TaskException {
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
            throw new TaskException("Can not remove task " + task);
        } finally {
            dataHelper.disconnect();
        }
    }

    @Override
    public void changeTaskStatus(Task task, TaskStatuses targetStatus) throws CanNotChangeTaskException {
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
            throw new CanNotChangeTaskException("Can not change status" + task + targetStatus);
        } finally {
            dataHelper.disconnect();
        }
    }

    @Override
    public void changeDeadline(Task task, LocalDateTime targetDeadline) throws CanNotChangeTaskException {
        try {
            dataHelper.connect();
            task.setDeadline(targetDeadline);
            dataHelper.updateTask(task);
            dataHelper.commitChanges();
        } catch (SQLException e) {
            try {
                dataHelper.rollbackChanges();
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
            }
            e.printStackTrace(System.err);
            throw new CanNotChangeTaskException("Can not change deadline" + task + targetDeadline);
        } finally {
            dataHelper.disconnect();
        }
    }

    @Override
    public List<Task> getTaskByAuthor(User user) throws TaskException {
        List<Task> authorstasks = null;
        try {
            dataHelper.connect();
            authorstasks = dataHelper.getTasksByAuthor(user);
            dataHelper.commitChanges();
        } catch (SQLException e) {
            try {
                dataHelper.rollbackChanges();
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
            }
            e.printStackTrace(System.err);
            throw new TaskException("Can not getTask by Author " + user);
        } finally {
            dataHelper.disconnect();
        }
        return authorstasks;
    }

    @Override
    public List<Task> getTaskByUser(User user) throws TaskException {
        List<Task> userstasks = null;
        try {
            dataHelper.connect();
            userstasks = dataHelper.getTasks(user);
            dataHelper.commitChanges();
        } catch (SQLException e) {
            try {
                dataHelper.rollbackChanges();
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
            }
            e.printStackTrace(System.err);
            throw new TaskException("Can not get Tasks by responsible User" + user);
        } finally {
            dataHelper.disconnect();
        }
        return userstasks;
    }

    @Override
    public Task getTaskById(Long taskId) throws TaskException {
        Task task = null;
        try {
            dataHelper.connect();
            task = dataHelper.getTaskById(taskId);
            dataHelper.commitChanges();
        } catch (SQLException e) {
            try {
                dataHelper.rollbackChanges();
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
            }
            e.printStackTrace(System.err);
            throw new TaskException("Can not get Task by ID" + taskId);
        } finally {
            dataHelper.disconnect();
        }
        return task;
    }

}
