# TODO List Application

## Description
This is a RESTful TODO List application built using Spring Boot. The application provides CRUD (Create, Read, Update, Delete) operations to manage tasks in a TODO list. It uses a MySQL database for persistent storage and is implemented using clean and modular architecture.

### Features
- Create new TODO tasks
- Retrieve all tasks or a specific task by ID
- Update existing tasks
- Delete tasks by ID
- Delete all tasks

---

## Prerequisites
Before running the application, ensure you have the following installed:

- Java 17 or higher
- Maven 3.8+
- MySQL Server

---

## Installation

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/todo-list-app.git
cd todo-list-app
```

### 2. Configure MySQL Database
Create a MySQL database for the application. For example:
```sql
CREATE DATABASE todo_list;
```

Update the database connection properties in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/todo_list
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Replace `your_mysql_username` and `your_mysql_password` with your MySQL credentials.

### 3. Build the Application
Use Maven to build the application:
```bash
mvn clean install
```

---

## Running the Application

### 1. Start the Application
Run the application using Maven:
```bash
mvn spring-boot:run
```

Alternatively, you can run the JAR file:
```bash
java -jar target/todo-list-app-1.0.0.jar
```

### 2. Access the API
The application runs on port `8080` by default. You can access the REST API at:
```
http://localhost:8080/api/todos
```

---

## API Endpoints

### 1. Get All TODOs
- **Endpoint:** `GET /api/todos`
- **Response:** List of all TODO tasks.

### 2. Get a TODO by ID
- **Endpoint:** `GET /api/todos/{id}`
- **Response:** Specific TODO task or `404 Not Found` if the ID does not exist.

### 3. Create a New TODO
- **Endpoint:** `POST /api/todos`
- **Body:**
  ```json
  {
      "title": "Task Title",
      "description": "Task Description"
  }
  ```
- **Response:** Created TODO task.

### 4. Update an Existing TODO
- **Endpoint:** `PUT /api/todos/{id}`
- **Body:**
  ```json
  {
      "title": "Updated Title",
      "description": "Updated Description"
  }
  ```
- **Response:** Updated TODO task or `404 Not Found` if the ID does not exist.

### 5. Delete a TODO by ID
- **Endpoint:** `DELETE /api/todos/{id}`
- **Response:** `204 No Content` or `404 Not Found` if the ID does not exist.

---

## Running Tests

### 1. Unit and Integration Tests
The application includes unit and integration tests for the `TodoController`, `TodoService`, and `TodoRepository`.

Run the tests using Maven:
```bash
mvn test
```

### 2. Test Coverage
The tests cover the following:
- CRUD operations in the `TodoController`
- Business logic in the `TodoService`
- Repository behavior in the `TodoRepository` using a MySQL database or an H2 in-memory database for testing.

---

## Troubleshooting

### Common Issues
1. **MySQL Connection Errors**:
   - Ensure MySQL is running and the database credentials are correct in `application.properties`.
   - Verify that the `todo_list` database exists.

2. **Port Already in Use**:
   - If port `8080` is in use, change the port in `application.properties`:
     ```properties
     server.port=8081
     ```

---

## License
This project is licensed under the [MIT License](LICENSE).

---

## Contributing
Contributions are welcome! Feel free to submit a pull request or open an issue for any bugs or feature requests.
