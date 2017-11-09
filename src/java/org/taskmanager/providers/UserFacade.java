package org.taskmanager.providers;

import java.sql.SQLException;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.taskmanager.entities.Task;
import org.taskmanager.entities.User;
import org.taskmanager.providers.exceptions.CommentException;
import org.taskmanager.providers.exceptions.UserAuthenticationException;
import org.taskmanager.providers.exceptions.UserException;
import org.taskmanager.services.UserServices;

/**
 *
 * @author Elmar H.Mammadov <jrelmarmammadov@gmail.com>
 */
public class UserFacade implements UserServices {

    private DataHelper dataHelper;
    private DataSource dataSource;

    public UserFacade() throws UserException {
        try {
            Context c = new InitialContext();
            dataSource = (DataSource) c.lookup("jdbc/groupd");
            dataHelper = new DataHelper(dataSource);
        } catch (NamingException e) {
            e.printStackTrace(System.err);
            throw new UserException();
        }
    }

    @Override
    public User login(String username, String password) throws UserAuthenticationException {
        User user = null;
        try {
            dataHelper.connect();
            user = dataHelper.login(username, password);
            dataHelper.commitChanges();
            if(user == null){
                throw new UserAuthenticationException();
            }
        } catch (SQLException e) {
            try {
                dataHelper.rollbackChanges();
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
            }
            e.printStackTrace(System.err);
            throw new UserAuthenticationException();
        } finally {
            dataHelper.disconnect();
        }
        return user;
    }

    @Override
    public User getUserById(Long userId) throws UserException {
        User user = null;
        try {
            dataHelper.connect();
            user = dataHelper.getUserById(userId);
            dataHelper.commitChanges();
        } catch (SQLException e) {
            try {
                dataHelper.rollbackChanges();
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
            }
            e.printStackTrace(System.err);
            throw new UserException("Can not get user by id " + userId);
        } finally {
            dataHelper.disconnect();
        }
        return user;
    }

    @Override
    public List<User> getUsersByManager(User manager) throws UserException {
        List<User> user = null;
        try {
            dataHelper.connect();
            user = dataHelper.getUsersByManager(manager);
            dataHelper.commitChanges();
        } catch (SQLException e) {
            try {
                dataHelper.rollbackChanges();
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
            }
            e.printStackTrace(System.err);
            throw new UserException("Can not get Users by Manager " + manager);
        } finally {
            dataHelper.disconnect();
        }
        System.out.println("Facade data "+user);
        return user;
    }

    @Override
    public void register(User user) throws UserException {
        try {
            dataHelper.connect();
            dataHelper.register(user);
            dataHelper.commitChanges();
        } catch (SQLException e) {
            try {
                dataHelper.rollbackChanges();
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
            }
            e.printStackTrace(System.err);
            throw new UserException("Can not register User " + user);

        } finally {
            dataHelper.disconnect();
        }
    }

    @Override
    public void writeComment(User user, Task task) throws CommentException {
        try {
            dataHelper.connect();
            dataHelper.writeComment(user, task);
            dataHelper.commitChanges();
        } catch (SQLException e) {
            try {
                dataHelper.rollbackChanges();
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
            }
            e.printStackTrace(System.err);
            throw new CommentException("Can not add comment " + user + task);
        } finally {
            dataHelper.disconnect();
        }
    }

}
