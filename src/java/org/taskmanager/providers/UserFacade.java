package org.taskmanager.providers;

import java.sql.SQLException;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.taskmanager.entities.Task;
import org.taskmanager.entities.User;
import org.taskmanager.services.UserServices;

/**
 *
 * @author Elmar H.Mammadov <jrelmarmammadov@gmail.com>
 */
public class UserFacade implements UserServices {
    private DataHelper dataHelper;
    private DataSource dataSource;

    public UserFacade() {
        try {
            Context c = new InitialContext();
            dataSource = (DataSource) c.lookup("java:/comp/env/jdbc/groupd");
            dataHelper = new DataHelper(dataSource);
        } catch (NamingException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public User login(String username, String password) {
        User user = null; 
        try{
            dataHelper.connect();
            user = dataHelper.login(username, password);
            dataHelper.commitChanges();
        }catch(SQLException e){
            try {
                dataHelper.rollbackChanges();
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
            }
            e.printStackTrace(System.err);
        }finally{
            dataHelper.disconnect();
        }
        return user;
    }

    @Override
    public User getUserById(Long userId) {
        User user = null; 
        try{
            dataHelper.connect();
            user = dataHelper.getUserById(userId);
            dataHelper.commitChanges();
        }catch(SQLException e){
            try {
                dataHelper.rollbackChanges();
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
            }
            e.printStackTrace(System.err);
        }finally{
            dataHelper.disconnect();
        }
        return user;
    }

    @Override
    public List<User> getUsersByManager(User manager) {
        List<User> user = null;
        try{
            dataHelper.connect();
            user = dataHelper.getUsersByManager(manager);
            dataHelper.commitChanges();
        }catch(SQLException e){
            try {
                dataHelper.rollbackChanges();
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
            }
            e.printStackTrace(System.err);
        }finally{
            dataHelper.disconnect();
        }
        return user;
    }

    @Override
    public void register(User user) {
        try {
            dataHelper.connect();
            dataHelper.register(user);
            dataHelper.commitChanges();
        }catch(SQLException e){
            try {
                dataHelper.rollbackChanges();
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
            }
            e.printStackTrace(System.err);
        }finally{
            dataHelper.disconnect();
        }
    }

    @Override
    public void writeComment(User user, Task task) {
        try {
            dataHelper.connect();
            dataHelper.writeComment(user, task);
            dataHelper.commitChanges();
        }catch(SQLException e){
            try {
                dataHelper.rollbackChanges();
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
            }
            e.printStackTrace(System.err);
        }finally{
            dataHelper.disconnect();
        }
    }

    
}
