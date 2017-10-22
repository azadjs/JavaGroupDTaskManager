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
import org.taskmanager.entities.util.TaskStatuses;

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

    public Task getTaskById(Long id) throws SQLException {
        connect();
        String query = "SELECT * FROM TASKS WHERE ID = ? ";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        resultSet = preparedStatement.executeQuery();
        Task t = new Task();
        if (resultSet.next()) {
            t.setId(resultSet.getLong(1));
            t.setTitle(resultSet.getString(2));
            t.setDescription(resultSet.getString(3));
            t.setOwner(new User(resultSet.getLong(4)));
            t.setCreated(resultSet.getTimestamp(5).toLocalDateTime());
            t.setDeadline(resultSet.getTimestamp(6).toLocalDateTime());
            t.setStatus(TaskStatuses.fromValue(resultSet.getString(7)));
            t.setSolved(resultSet.getTimestamp(8).toLocalDateTime());
            t.setAssigned(resultSet.getTimestamp(9).toLocalDateTime());
        }
        disconnect();
        return t;
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

    public void updateTask(Task task) throws SQLException {
        connect();
        String query = "UPDATE tasks SET "
                + " ( TITLE , DESCRIPTION , DEADLINE , STATUS , SOLVED ) "
                + " VALUES ( ? , ? , ? , ? , ? ) WHERE id = ? ";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, task.getTitle());
        preparedStatement.setString(2, task.getDescription());
        preparedStatement.setDate(3, convertFrom(task.getDeadline()));
        preparedStatement.setString(4, task.getStatus().getStatus());
        preparedStatement.setDate(5, convertFrom(task.getSolved()));
        preparedStatement.setLong(6, task.getId());
        preparedStatement.execute();
        disconnect();
    }

    public void removeTask(Task task) throws SQLException {
        connect();
        String query = "DELETE FROM task WHERE id = ? ";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, task.getId());
        preparedStatement.execute();
        disconnect();
    }

    List<Task> getTasksByAuthor(User author) throws SQLException {
        connect();
        List<Task> resultedTasks = new ArrayList<>();
        String query = "SELECT * FROM TASKS WHERE USER_ID = ? ";
        preparedStatement.setLong(1, author.getId());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Task t = new Task();
            t.setId(resultSet.getLong(1));
            t.setTitle(resultSet.getString(2));
            t.setDescription(resultSet.getString(3));
            t.setOwner(new User(resultSet.getLong(4)));
            t.setCreated(resultSet.getTimestamp(5).toLocalDateTime());
            t.setDeadline(resultSet.getTimestamp(6).toLocalDateTime());
            t.setStatus(TaskStatuses.fromValue(resultSet.getString(7)));
            t.setSolved(resultSet.getTimestamp(8).toLocalDateTime());
            t.setAssigned(resultSet.getTimestamp(9).toLocalDateTime());
            resultedTasks.add(t);
        }
        disconnect();
        return resultedTasks;
    }

    public List<Task> getTask(User responsible) throws SQLException {
        connect();
        List<Task> resultedTasks = new ArrayList<>();
        String query = "SELECT * FROM TASKS_BY_USER WHERE RESP_ID = ? ";
        preparedStatement.setLong(1, responsible.getId());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Task t = new Task();
            t.setId(resultSet.getLong(1));
            t.setTitle(resultSet.getString(2));
            t.setDescription(resultSet.getString(3));
            t.setOwner(new User(resultSet.getLong(4)));
            t.setCreated(resultSet.getTimestamp(5).toLocalDateTime());
            t.setDeadline(resultSet.getTimestamp(6).toLocalDateTime());
            t.setStatus(TaskStatuses.fromValue(resultSet.getString(7)));
            t.setSolved(resultSet.getTimestamp(8).toLocalDateTime());
            t.setAssigned(resultSet.getTimestamp(9).toLocalDateTime());
            resultedTasks.add(t);
        }
        disconnect();
        return resultedTasks;
    }

    public void assignTask(Long userId, Long taskId) throws SQLException {
        connect();
        String makeRelationQuery = "INSERT INTO USERS_TASKS VALUES ( ? , ? ) ";
        preparedStatement = connection.prepareStatement(makeRelationQuery);
        preparedStatement.setLong(1, userId);
        preparedStatement.setLong(2, taskId);
        preparedStatement.execute();
        disconnect();
    }

    public boolean register(User user) {
        try {
            connect();
            String query = " INSERT INTO users ( fullname , username , password, "
                    + " user_role ) "
                    + " VALUES ( ? , ? , ? , ? ) ";
            preparedStatement = connection.prepareCall(query);
            preparedStatement.setString(1, user.getFullname());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getUserRole().getRole());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return false;
        }
        disconnect();
        return true;
    }

    private Date convertFrom(LocalDateTime ldt) {
        return new Date(java.util.Date.from(ldt.atZone(ZoneId.systemDefault())
                .toInstant()).getTime());
    }

}
