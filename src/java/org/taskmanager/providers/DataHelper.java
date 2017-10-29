package org.taskmanager.providers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.taskmanager.entities.Comment;
import org.taskmanager.entities.Task;
import org.taskmanager.entities.User;
import org.taskmanager.entities.util.TaskStatuses;
import org.taskmanager.entities.util.UserRoles;

/**
 *
 * @author Elmar H.Mammadov <jrelmarmammadov@gmail.com>
 */
public final class DataHelper {

    private final DataSource groupDdatasource;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public DataHelper(DataSource groupDdatasource) {
        this.groupDdatasource = groupDdatasource;
    }

    protected void connect() throws SQLException {
        connection = groupDdatasource.getConnection();
        connection.setAutoCommit(false);
    }

    protected void disconnect() {
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
        return t;
    }

    public void insertTask(Task task) throws SQLException {
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
    }

    public void insertResponsibleUsers(Task task) throws SQLException {
        String query = "INSERT INTO USERS_TASKS "
                + "VALUES"
                + " (?,?) ";
        preparedStatement = connection.prepareStatement(query);
        for (User res : task.getResponsibles()) {
            preparedStatement.setLong(1, res.getId());
            preparedStatement.setLong(2, task.getId());
            preparedStatement.execute();
        }
    }

    public void updateTask(Task task) throws SQLException {
        String query = "UPDATE TASKS SET  "
                + "(TITLE,DESCRIPTION,USER_ID,DEADLINE,STATUS)"
                + " VALUES"
                + "(?,?,?,?,?) where id=?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, task.getTitle());
        preparedStatement.setString(2, task.getDescription());
        preparedStatement.setLong(3, task.getOwner().getId());
        preparedStatement.setDate(4, convertFrom(task.getDeadline()));
        preparedStatement.setString(5, task.getStatus().getStatus());
        preparedStatement.setLong(6, task.getId());
        preparedStatement.execute();
    }

    public void removeTask(Task task) throws SQLException {
        String query = "DELETE FROM task WHERE id = ? ";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, task.getId());
        preparedStatement.execute();
    }

    public void removeComment(Long commentId) throws SQLException {
        String query = "DELETE FROM COMMENTS WHERE ID = ? ";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, commentId);
        preparedStatement.execute();
    }

    List<Task> getTasksByAuthor(User author) throws SQLException {
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
        return resultedTasks;
    }

    public List<Task> getTasks(User responsible) throws SQLException {
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
        return resultedTasks;
    }

    public void assignTask(Long userId, Long taskId) throws SQLException {
        String makeRelationQuery = "INSERT INTO USERS_TASKS VALUES ( ? , ? ) ";
        preparedStatement = connection.prepareStatement(makeRelationQuery);
        preparedStatement.setLong(1, userId);
        preparedStatement.setLong(2, taskId);
        preparedStatement.execute();
    }

    public User login(String username, String password) throws SQLException{
        String query = "SELECT * FROM USERS WHERE USERNAME = ? and PASSWORD = ? ";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        resultSet = preparedStatement.executeQuery();
        User u = null;
        if(resultSet.next()){
            u = new User();
            u.setId(resultSet.getLong(1));
           u.setFullname(resultSet.getString(2));
           u.setUsername(resultSet.getString(3));
           u.setPassword(resultSet.getString(4));
           u.setUserRole(UserRoles.fromValue(resultSet.getString(5)));
           u.setLastLoginDate(resultSet.getTimestamp(6).toLocalDateTime());
           u.setManager(new User(resultSet.getLong(7)));
           return u;
        }
        return null;
    }
    
    public void writeComment(User user, Task task) throws SQLException{
        Comment c = new Comment();
        String query = "SELECT INTO COMMENTS (user_id , comment ,"
                + " created , task_id "
                + "VALUES (? , ? , ? , ? )";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, user.getId());
        preparedStatement.setString(2, c.getComment() );
        preparedStatement.setDate(3, convertFrom(c.getCreated())) ;
        preparedStatement.setLong(4, task.getId());
        preparedStatement.execute();
    }
    
    public void register(User user) throws SQLException {
        String query = " INSERT INTO USERS ( fullname , username , password, "
                + " user_role , manager_id ) "
                + " VALUES ( ? , ? , ? , ? , ? ) ";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getFullname());
        preparedStatement.setString(2, user.getUsername());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setString(4, user.getUserRole().getRole());
        if (user.getManager() == null) {
            preparedStatement.setNull(5, Types.INTEGER);
        } else {
            preparedStatement.setLong(5, user.getManager().getId());
        }
        preparedStatement.execute();
    }
    
    public User getUserById (Long userId) throws SQLException{
        String query = "SELECT * FROM USERS  WHERE ID = ? ";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, userId );
        resultSet = preparedStatement.executeQuery();
        User u = null;
        if (resultSet.next()) {
            u = new User();
           u.setId(resultSet.getLong(1));
           u.setFullname(resultSet.getString(2));
           u.setUsername(resultSet.getString(3));
           u.setPassword(resultSet.getString(4));
           u.setUserRole(UserRoles.fromValue(resultSet.getString(5)));
           u.setLastLoginDate(resultSet.getTimestamp(6).toLocalDateTime());
           u.setManager(new User(resultSet.getLong(7)));
        }
        return u;
    }
    
    public List<User> getUsersByManager(User manager) throws SQLException{
        List<User> usersList = new ArrayList<>();
        String query = "SELECT * FROM USERS WHERE MANAGER_ID = ? ";
        preparedStatement.setLong(1, manager.getId() );
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            User u = new User();
            u.setId(resultSet.getLong(1));
            u.setFullname(resultSet.getString(2));
            u.setUsername(resultSet.getString(3));
            u.setPassword(resultSet.getString(4));
            u.setUserRole(UserRoles.fromValue(resultSet.getString(5)));
            usersList.add(u);
        }
        return usersList;
    }

    private Date convertFrom(LocalDateTime ldt) {
        return new Date(java.util.Date.from(ldt.atZone(ZoneId.systemDefault())
                .toInstant()).getTime());
    }

    protected void commitChanges() throws SQLException {
        this.connection.commit();
    }

    protected void rollbackChanges() throws SQLException {
        this.connection.rollback();
    }
}
