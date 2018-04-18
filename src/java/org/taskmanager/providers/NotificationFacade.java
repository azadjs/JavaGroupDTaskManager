/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.taskmanager.providers;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.taskmanager.entities.Notification;
import org.taskmanager.entities.Task;
import org.taskmanager.entities.User;
import org.taskmanager.services.NotificationService;

/**
 *
 * @author Azad M
 */
public class NotificationFacade implements NotificationService{
    
    private DataHelper dataHelper;
    private DataSource dataSource;

    public NotificationFacade() {
        try {
            Context c = new InitialContext();
            dataSource = (DataSource) c.lookup("jdbc/groupd");
            dataHelper = new DataHelper(dataSource);
        } catch (NamingException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public Notification findById(Long id) {
         Notification notification = null;
        try {
            dataHelper.connect();
            notification = dataHelper.findById(id);
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
        return notification;
    }

    @Override
    public List<Notification> findAll() {
            List<Notification> all = null;
        try {
            dataHelper.connect();
            all = dataHelper.findAll();
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
        return all;
    }

    @Override
    public List<Notification> findAll(User user) {
         List<Notification> allUsers = null;
        try {
            dataHelper.connect();
            allUsers = dataHelper.findAll(user);
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
        return allUsers;
    }

    @Override
    public List<Notification> findAll(Task task) {
            List<Notification> allTasks = null;
        try {
            dataHelper.connect();
            allTasks = dataHelper.findAll(task);
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
        return allTasks;
    }

    @Override
    public void merge(Notification notification) {
    }

    @Override
    public void remove(Long id) {
         try {
            dataHelper.connect();
            dataHelper.remove(id);
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
    public void add(Notification notification) {
         try {
            dataHelper.connect();
            notification.setCreated(LocalDateTime.now());
            notification.setStatus(Notification.Status.NEW);
            dataHelper.add(notification);
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
    
}
