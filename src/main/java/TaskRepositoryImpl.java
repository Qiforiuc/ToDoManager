import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class TaskRepositoryImpl implements TaskRepository {
  private final String url;
  private final String username;
  private final String password;

  public TaskRepositoryImpl(String url, String username, String password) {
    this.url = url;
    this.username = username;
    this.password = password;
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(url, username, password);
  }

  @Override
  public void addTask(Task task) {
    String sql = "INSERT INTO tasks(name, description, status) VALUES (?, ?, ?)";
    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, task.getName());
      pstmt.setString(2, task.getDescription());
      pstmt.setString(3, task.getStatus().toString());
      pstmt.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void updateTask(Task task) {
    String sql = "UPDATE tasks SET name=?, description=?, status=? WHERE id=?";
    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, task.getName());
      pstmt.setString(2, task.getDescription());
      pstmt.setString(3, task.getStatus().toString());
      pstmt.setInt(4, task.getId());
      pstmt.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void deleteTask(int id) {
    String sql = "DELETE FROM tasks WHERE id=?";
    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public Task getTaskById(int id) {
    String sql = "SELECT * FROM tasks WHERE id=?";
    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          String name = rs.getString("name");
          String description = rs.getString("description");
          Status status = Status.valueOf(rs.getString("status"));
          return new Task(id, name, description, status);
        }
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  @Override
  public List<Task> getAllTasks() {
    String sql = "SELECT * FROM tasks";
    List<Task> tasks = new ArrayList<>();
    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) {
      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        Status status = Status.valueOf(rs.getString("status"));
        tasks.add(new Task(id, name, description, status));
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return tasks;
  }


  @Override
  public List<Task> getTasksByStatus(Status status) {
    String sql = "SELECT * FROM tasks WHERE status=?";
    List<Task> tasks = new ArrayList<>();
    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, status.toString());
      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          int id = rs.getInt("id");
          String name = rs.getString("name");
          String description = rs.getString("description");
          tasks.add(new Task(id, name, description, status));
        }
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return tasks;
  }
}
