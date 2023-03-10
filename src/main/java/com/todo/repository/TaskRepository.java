package com.todo.repository;

import com.todo.Status;
import com.todo.Task;
import java.util.List;

public interface TaskRepository {
  public void addTask(Task task);
  public void updateTask(Task task);
  public void deleteTask(int id);
  public Task getTaskById(int id);
  public List<Task> getAllTasks();
  public List<Task> getTasksByStatus(Status status);
}