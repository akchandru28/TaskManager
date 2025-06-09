# Kaiburr Task 1 – Java + REST API + MongoDB

This is the solution for Task 1 given by Kaiburr. I have created a Java Spring Boot project that allows users to create, get, delete, search, and execute shell command tasks. All the task details and outputs are stored in MongoDB.

---

# Tools & Technologies Used

- Java 17
- Spring Boot
- MongoDB
- Postman (for testing)
- VS Code
- GitHub

---

# How to Run This Project

1. Make sure MongoDB is running on your system. I'm using MongoDB 8 locally.
2. Clone this repo or download the code.
3. Open the project in VS Code.
4. Run the command:mvn spring-boot:run

# Endpoints and Example
# 1.Create Task
URL: http://localhost:8080/tasks/create
Method: POST
Body (raw JSON):
json
{
  "id": "123",
  "name": "Print Hello",
  "owner": "John Smith",
  "command": "echo Hello World!"
}

# 2.Get Task by ID
URL: http://localhost:8080/tasks/123
Method: GET

# 3.Search Task by Name
URL: http://localhost:8080/tasks/search/Hello
Method: GET

# 4.Delete Task
URL: http://localhost:8080/tasks/delete/123
Method: DELETE

# 5.Execute Task
URL: http://localhost:8080/tasks/execute/123
Method: PUT



