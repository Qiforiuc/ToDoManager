import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TodoManager {
  private TaskRepository taskRepository;

  public TodoManager(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public void run() {
    Scanner scanner = new Scanner(System.in);
    int choice = -1;

    while (choice != 0) {
      System.out.println("TODO MANAGER");
      System.out.println("1. Add Task");
      System.out.println("2. Update Task");
      System.out.println("3. Delete Task");
      System.out.println("4. Get Task By ID");
      System.out.println("5. Get Tasks By Status");
      System.out.println("0. Exit");
      System.out.print("Enter your choice: ");

      try {
        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
          case 1:
            addTask(scanner);
            break;
          case 2:
            updateTask(scanner);
            break;
          case 3:
            deleteTask(scanner);
            break;
          case 4:
            getTaskById(scanner);
            break;
          case 5:
            getTasksByStatus(scanner);
            break;
          case 0:
            System.out.println("Exiting...");
            break;
          default:
            System.out.println("Invalid choice, try again.");
        }
      } catch (Exception e) {
        System.out.println("Invalid input, try again.");
        scanner.nextLine();
      }
    }

    scanner.close();
  }

  private void addTask(Scanner scanner) {
    System.out.print("Enter task name: ");
    String name = scanner.nextLine();
    System.out.print("Enter task description: ");
    String description = scanner.nextLine();
    System.out.print("Enter task status (TODO, IN_PROGRESS, or DONE): ");
    String statusStr = scanner.nextLine();

    try {
      Status status = Status.valueOf(statusStr.toUpperCase());
      Task task = new Task(name, description, status);
      taskRepository.addTask(task);
      System.out.println("Task added: " + task);
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid status, try again.");
    }
  }

  private void updateTask(Scanner scanner) {
    System.out.print("Enter task ID: ");
    int id = scanner.nextInt();
    scanner.nextLine();

    Task task = taskRepository.getTaskById(id);

    if (task == null) {
      System.out.println("Task not found.");
    } else {
      System.out.println("Current task details: " + task);
      System.out.print("Enter new task name (leave blank to keep current value): ");
      String name = scanner.nextLine();
      System.out.print("Enter new task description (leave blank to keep current value): ");
      String description = scanner.nextLine();
      System.out.print("Enter new task status (leave blank to keep current value): ");
      String statusStr = scanner.nextLine();

      if (!name.isEmpty()) {
        task.setName(name);
      }
      if (!description.isEmpty()) {
        task.setDescription(description);
      }
      if (!statusStr.isEmpty()) {
        try {
          Status status = Status.valueOf(statusStr.toUpperCase());
          task.setStatus(status);
        } catch (IllegalArgumentException e) {
          System.out.println("Invalid status, task status will not be updated.");
        }
      }

      taskRepository.updateTask(task);
      System.out.println("Task updated: " + task);
    }
  }
  private void deleteTask(Scanner scanner) {
    System.out.print("Enter task ID: ");
    int id = scanner.nextInt();
    scanner.nextLine();

    Task task = taskRepository.getTaskById(id);

    if (task == null) {
      System.out.println("Task not found.");
    } else {
      taskRepository.deleteTask(task.getId());
      System.out.println("Task deleted: " + task);
    }
  }
  //not used
  private void getAllTasks() {
    List<Task> tasks = taskRepository.getAllTasks();
    if (tasks.isEmpty()) {
      System.out.println("No tasks found.");
    } else {
      System.out.println("All tasks:");
      for (Task task : tasks) {
        System.out.println(task);
      }
    }
  }

  private void getTasksByStatus(Scanner scanner) {
    System.out.print("Enter task status (TO_DO, IN_PROGRESS, or DONE): ");
    String statusString = scanner.nextLine().toUpperCase();

    try {
      Status status = Status.valueOf(statusString);
      List<Task> tasks = taskRepository.getTasksByStatus(status);

      if (tasks.isEmpty()) {
        System.out.println("No tasks found with status: " + status);
      } else {
        System.out.println("Tasks with status " + status + ":");
        for (Task task : tasks) {
          System.out.println(task);
        }
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid status. Please enter a valid status (TODO, IN_PROGRESS, or DONE).");
    }
  }

  private void getTaskById(Scanner scanner) {
    System.out.print("Enter task ID: ");
    int id = scanner.nextInt();
    scanner.nextLine();

    Task task = taskRepository.getTaskById(id);

    if (task == null) {
      System.out.println("Task not found.");
    } else {
      System.out.println("Task details: " + task);
    }
  }

}
