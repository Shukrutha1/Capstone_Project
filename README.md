Payroll Management System:
Backend:
For backend I have used Spring Boot with REST API's.
Swagger framework for interactive API documentation.(http://localhost:8080/swagger-ui/index.html#/)
Connected to MySQL Database via Spring Data JPA.

Here we have two primary users Admin and Employee.
When the user tries to login the server checks the credentials with the database if the data matches it generates the JWT token,
client stores it in the localstorage and with each API call it will share the JWT token 
and gives access accordingly either as an Admin or Employee.
