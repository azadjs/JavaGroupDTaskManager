package org.taskmanager.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.taskmanager.entities.Task;
import org.taskmanager.entities.User;

/**
 *
 * @author Elmar H.Mammadov <jrelmarmammadov@gmail.com>
 */
public class DataHelper {

    private final DataSource groupDdatasource;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public DataHelper(DataSource groupDdatasource) {
        this.groupDdatasource = groupDdatasource;
    }

    private void connect() throws SQLException {
        connection = groupDdatasource.getConnection();
    }

    private void disconnect() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public void insertTask(Task task) throws SQLException {
        connect();
        String query = "INSERT INTO TASKS "
                + "(TITLE,DESCRIPTION,USER_ID,CREATED,DEADLINE,STATUS,SOLVED,ASSIGNED) "
                + " VALUES "
                + "(?,?,?,?,?,?,?,?) ";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, task.getTitle());
        preparedStatement.setString(2, task.getDescription());
        preparedStatement.setLong(3, task.getOwner().getId());
        preparedStatement.setDate(4, convertFrom(task.getCreated()));
        preparedStatement.setDate(5, convertFrom(task.getDeadline()));
        preparedStatement.setString(6, task.getStatus().getStatus());
        preparedStatement.setDate(7, convertFrom(task.getSolved()));
        preparedStatement.setDate(8, convertFrom(task.getAssigned()));
        preparedStatement.execute();
        disconnect();
    }

    public void insertResponsibleUsers(Task task) throws SQLException {
        connect();
        String query = "INSERT INTO USERS_TASKS "
                + "VALUES"
                + " (?,?) ";
        preparedStatement = connection.prepareStatement(query);
        for (User res : task.getResponsibles()) {
            preparedStatement.setLong(1, res.getId());
            preparedStatement.setLong(2, task.getId());
            preparedStatement.execute();
        }
        disconnect();
    }
    
    public void updateTask(Task task) throws SQLException{
        connect();
        String query = "UPDATE tasks description = ? ,"
                + " deadline = ? , status = ? , solved = ? , assigned = ? where id = ? ";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, task.getDescription());
        preparedStatement.setDate(2, convertFrom(task.getDeadline()));
        preparedStatement.setString(3, task.getStatus().getStatus());
        preparedStatement.setDate(4, convertFrom(task.getSolved()));
        preparedStatement.setDate(5, convertFrom(task.getAssigned()));
        preparedStatement.setLong(6, task.getId());
        preparedStatement.execute();
        disconnect();
    }
    public void removeTask(Task task) throws SQLException{
        connect();
        String query = "DELETE from task where id = ? ";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, task.getId());
        preparedStatement.execute();
        disconnect();
    }
    public List<Task>GetTask(User user) throws SQLException{
       List<Task> task = new ArrayList<>();
        connect();
       String query = "SELECT * FROM task_by_user where user_id = ? ";
       preparedStatement = connection.prepareStatement(query);
       preparedStatement.setLong(1, user.getId());
       preparedStatement.execute();
       while(resultSet.next()){
           task.add(new Task(resultSet.getString(query)));
       }
       disconnect();
        return task ; 
    }
    
    
    
    
    

    private Date convertFrom(LocalDateTime ldt) {
        return new Date(java.util.Date.from(ldt.atZone(ZoneId.systemDefault())
                .toInstant()).getTime());
    }

}
