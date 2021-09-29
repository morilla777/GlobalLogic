# GlobalLogic
Repository for User Service sources and resources.

## Sources
Spring Boot Gradle project at folder /users-service

## Resources
/resources folder with the followings files:

- Users Service - Authorization Header Value at JWT format.txt: JWT token for testing with Postman.
- Users Service - Components and Packages Diagram.pdf
- Users Service - Sequence Diagram.pdf
- Users Service.postman_collection.json: Postman collection for endpoint testing.

## Running

The service runs at the URL http://localhost:8080. 

## Swagger

API documentation at URL http://localhost:8080/swagger-ui.html.

## Notes

- The service uses Lombok library for help writing less code.
- The ideal solution for deal with JWT is using OAuth2 API of Spring Security, which was omited for solution's simplicity.
- For handling exceptions, I use a design pattern, which was omited for solution's simplicity.
