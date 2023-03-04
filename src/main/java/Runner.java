import java.util.Scanner;

public class Runner {
  public static void main(String[] args) {
    String url = "jdbc:postgresql://localhost:5432/tasklist";
    String user = "postgres";
    String password = "admin";
    Scanner scanner = new Scanner(System.in);
    TaskRepository taskRepository = new TaskRepositoryImpl(url, user, password);

    TodoManager todoManager = new TodoManager(taskRepository, scanner);

    todoManager.run();
  }
}