import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TodoManager implements TaskRepository{
  private final TaskRepository taskRepository;
  private final Scanner scanner;
  private Task task;
  public TodoManager(TaskRepository taskRepository, Scanner scanner) {
    this.taskRepository = taskRepository;
    this.scanner = scanner;
    task = new Task();
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
            System.out.println("Enter task name: ");
            task.setName(scanner.nextLine());
            System.out.println("Enter task description: ");
            task.setDescription(scanner.nextLine());
            task.setStatus(Status.TODO);
            addTask(task);
            task = null;
            break;
          case 2:
            System.out.print("Enter task ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            task = taskRepository.getTaskById(id);

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
              updateTask(task);
              task = null;
            }
            break;
          case 3:
            System.out.print("Enter task ID: ");
            id = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            deleteTask(id);
            break;
          case 4:
            System.out.print("Enter task ID: ");
            id = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            getTaskById(id);
            break;
          case 5:
            System.out.print("Enter task status (TO_DO, IN_PROGRESS, or DONE): ");
            Status status = Status.valueOf(scanner.nextLine().toUpperCase());
            getTasksByStatus(status);
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

  @Override
  public void addTask(Task task) {
    taskRepository.addTask(task);
  }

  @Override
  public void updateTask(Task task) {
    taskRepository.updateTask(task);
    System.out.println("Task updated: " + task);
  }

  @Override
  public void deleteTask(int taskId) {
    Task task = taskRepository.getTaskById(taskId);

    if (task == null) {
      System.out.println("Task not found.");
    } else {
      taskRepository.deleteTask(taskId);
      System.out.println("Task deleted: " + task);
    }
  }

  @Override
  public Task getTaskById(int taskId) {
    task = taskRepository.getTaskById(taskId);
    if (task == null) {
      System.out.println("Task not found.");
      return task;
    } else {
      System.out.println("Task details: " + task);
      return task;
    }
  }

  @Override
  public List<Task> getTasksByStatus(Status status) {
    try {
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
      System.out.println("Invalid status. Please enter a valid status (TO_DO, IN_PROGRESS, or DONE).");
    }
    return taskRepository.getTasksByStatus(status);
  }

  @Override
  public List<Task> getAllTasks() {
    return taskRepository.getAllTasks();
  }

}
