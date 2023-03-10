# ToDoManager

com.todo.TodoManager is a Java application for managing tasks. It allows users to create, read, update, and delete tasks, as well as mark them as "to do", "in progress", or "done".

# Solid Principles 

**The Single Responsibility Principle**

The Single Responsibility Principle states that a class should do one thing and therefore it should have only a single reason to change.

_Applied in code:_
        
        com.todo.Task class: The com.todo.Task class represents a single task in the application.
        It has a single responsibility of holding the details of a task such as title, 
        description, and status.


**Open-Closed Principle**

The Open-Closed Principle requires that classes should be open for extension and closed to modification.

_Applied in code:_

        com.todo.repository.TaskRepository interface is open for extension in that we can add new methods to it 
        without modifying any of the existing code that depends on it. For example, 
        if we want to add a new method to get tasks by priority, we can simply add it 
        to the com.todo.repository.TaskRepository interface and implement it in the com.todo.repository.TaskRepositoryImpl class without 
        modifying any of the existing code that depends on the com.todo.repository.TaskRepository interface.

**Liskov Substitution Principle**

The Liskov Substitution Principle states that subclasses should be substitutable for their base classes.

_Applied in code:_

        Liskov Substitution Principle means that any implementation of the com.todo.repository.TaskRepository 
        interface should be able to be substituted for another implementation without 
        affecting the correctness of the com.todo.TodoManager class.

 **Interface Segregation Principle**
 
Segregation means keeping things separated, and the Interface Segregation Principle is about separating the interfaces.

_Applied in code:_

        com.todo.repository.TaskRepository interface is designed with only the methods that are necessary for 
        managing tasks, such as addTask, deleteTask, getTaskById, getAllTasks, getTasksByStatus. 
        Each of these methods is specific to the needs of the com.todo.TodoManager, which is the client
        that uses the com.todo.repository.TaskRepository interface.

 **Dependency Inversion Principle**

The Dependency Inversion principle states that our classes should depend upon interfaces or abstract classes instead of concrete classes and functions.

_Applied in code:_

        The com.todo.repository.TaskRepositoryImpl class, which provides the concrete implementation of the 
        repository, depends on the com.todo.repository.TaskRepository interface. This means that the implementation 
        depends on an abstraction rather than on specific details of the com.todo.TodoManager class.

