# To-Do List Application

A simple and efficient To-Do List application built with Java, Spring Boot, and Maven. This project demonstrates a RESTful API for managing tasks, including creating, reading, updating, and deleting tasks.

## Features

- **Create Task**: Add new tasks with a title, description, and completion status.
- **Read Tasks**: Retrieve all tasks or a specific task by ID.
- **Update Task**: Modify existing tasks.
- **Delete Task**: Remove tasks by ID.
- **H2 Database**: In-memory database for easy setup and testing.
- **Docker Support**: Containerize the application for deployment.

## Technologies Used

- **Java 17**
- **Spring Boot**
- **Maven**
- **H2 Database**
- **Docker**

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- Docker (optional, for containerization)

### Installation

1. **Clone the repository**:
   ```sh
   git clone https://github.com/natanxds/todolist.git
   cd todolist
   ```

2. **Build the project**:
   ```sh
   mvn clean package
   ```

3. **Run the application**:
   ```sh
   mvn spring-boot:run
   ```

### Running with Docker

1. **Build the Docker image**:
   ```sh
   docker build -t natanxds/todolist:latest .
   ```

2. **Run the Docker container**:
   ```sh
   docker run -p 8080:8080 natanxds/todolist:latest
   ```

### API Endpoints

- **GET /api/v1/tasks**: Retrieve all tasks.
- **GET /api/v1/tasks/{id}**: Retrieve a task by ID.
- **POST /api/v1/tasks**: Create a new task.
- **PUT /api/v1/tasks/{id}**: Update an existing task.
- **DELETE /api/v1/tasks/{id}**: Delete a task by ID.

### Example Requests

**Create a Task**:
```sh
curl -X POST http://localhost:8080/api/v1/tasks \
-H "Content-Type: application/json" \
-d '{
  "title": "Buy groceries",
  "description": "Milk, Bread, Eggs, Butter",
  "isCompleted": false
}'
```

**Get All Tasks**:
```sh
curl -X GET http://localhost:8080/api/v1/tasks
```

**Update a Task**:
```sh
curl -X PUT http://localhost:8080/api/v1/tasks/1 \
-H "Content-Type: application/json" \
-d '{
  "title": "Buy groceries and fruits",
  "description": "Milk, Bread, Eggs, Butter, Apples",
  "isCompleted": false
}'
```

**Delete a Task**:
```sh
curl -X DELETE http://localhost:8080/api/v1/tasks/1
```

## Running Tests

To run the tests, use the following command:
```sh
mvn test
```

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
