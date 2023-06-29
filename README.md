# Hiring System Web Application

![cover](https://github.com/DragosGhinea/HiringSystem/blob/main/docs/cover.gif)

This project represents a web application made specifically for one company to help with their hiring procedures. It is designed to help with user & application management and to offer interview scheduling and incorporated conference rooms. It also helps with emails and applicants' notifications.

# Software Development Methodologies

This project was made for the course _Software Development Methodologies_ held by Prof. Dr. Alin Ștefănescu, at University of Bucharest, Faculty of Mathematics and Computer Science.

The requirements for the project evaluation are described in [wiki](https://github.com/DragosGhinea/HiringSystem/wiki).

## Team

The team that worked on this project is composed of:
- Dragoș-Dumitru Ghinea
- Gabriel-Bogdan Iliescu
- Ștefania Rîncu
- Dafina Trufaș

# Base Technologies

Since the application is **fullstack** we use a variety of technologies to acommodate the implementation of both and the communication between them.

- **Frontend** - The main technology used for frontend is **REACT**, which will start by default on port 3000. The reason behind using REACT is the necessity of a interactive and lightweight dynamic page update, especially for the interview rooms (more details later).
- **Backend** - We chose to develop a Java backend using **Spring Boot** (which starts on the 8081 port)

## REACT

The react project was created using `npx create-react-app react-hiringsystem` within a separate folder from the backend `src/frontend/react-hiringsystem`(even though they run separately, we just keep the code clustered).

To make this project we had to get acquainted with some of the concepts from this javascript library, such as:
- Functional Components
- Various hooks to simulate the behaviour of Class Components inside Functional Components to improve efficiency such as:
    * useState
    * useRef
    * useEffect
    * useNavigation
    * useLocation
    * useContext
    * and so on...
- Props
- Rendering behaviours

## Spring Boot

We chose to use Spring Boot because it is a popular implementation of the Spring Framework (widly adopted for Java application development) and has a rich ecosystem. It also increases productivity, as it provides a wide range of already implemented integrations (such as database access, security, caching and logging).

In this project we used:
- **Hibernate & HikariCP** - Hibernate is an object-relational mapping (ORM) framework that provides a convenient way to map Java objects to database tables. HikariCP is a high-performance connection pool for managing database connections efficiently.
- **MapStruct** - MapStruct is a code generation library that simplifies the mapping between different Java bean types. It generates mapping code based on defined mappings, reducing the boilerplate code needed for mapping.
- **Spring Mails** -  Spring Mail provides integration with JavaMail for sending and receiving emails. It offers a high-level abstraction and simplifies email-related tasks in Spring applications.
- **Spring Security** - Spring Security is a powerful framework for implementing authentication and authorization in Java applications. It provides a wide range of features and tools for securing web applications and APIs.
- **Spring Web** - Spring Web is a module in the Spring framework that provides support for building web applications. It includes features like request handling, routing, and handling HTTP requests and responses.
- **Spring WebSockets** -  Spring WebSockets enables real-time, bidirectional communication between a client and server over a single, long-lived connection. It is commonly used for applications that require real-time updates and event-driven communication. We use them for the interview room peer to peer connection and message handling.
- **Spring JDBC + JPA** - Spring JDBC is a module that simplifies database access using the JDBC API. It provides convenient utilities and templates for executing SQL queries and handling database operations. Spring JPA is a module that provides an abstraction layer over JPA (Java Persistence API) for simplified database access and object-relational mapping.
- **Json WebTokens** - JWT is a compact and self-contained mechanism for securely transmitting information between parties as a JSON object. It is commonly used for authentication and authorization in web applications, providing a stateless and portable token-based approach.
- **~Spring Thymeleaf~** - Spring Thymeleaf is a templating engine that integrates well with Spring applications, allowing you to create dynamic server-rendered views. **We started implementing pages using it before deciding to move to a frontend library (REACT)**, kept it anyway in case we would need to server render something (but it didn't happen).


## Spring Security + JWT

We use Spring Security to secure the requests to the backend and personalize its config inside, mainly configuring the routes and the permission required to access them. In our case, also allowing CORS operations by altering the CORS filter.

Since we use **Json Web Tokens** for authentication, we don't need sessions. Therefore, in Spring Security's configuration we set the session creation policy to STATELESS. Inside the application.properties file we set configuration options for how much a token takes to expire. In our case we use two type of tokens, **access_token & refresh_token**. The access_token is used for the actual authorization while the refresh_token is used to obtain a new access_token when one expires.

For the utilization of the tokens mentioned above, we take advantage of the already preimplemented chain of responsability of Spring Security, and we insert our own filter, `JwtAuthFilter` to handle requests. This filter will validate the user and will also obtain an **Authentication** for them, which we can access later in the chain of responsability via Spring Boot's dependency injection, giving an **Authentication** parameter, wherever we need it.

# Project Code Layout

We have tried to respect the SOLID principles as much as possible (more in the backend than the frontend). To help us in this regard, we chose to use a **repository-service-controller-model layout** to properly separate and manage the responsability of specific regions of code and ensure a somehow natural flow between the layers.

```
<<Backend structure>>

+ main
|--- .java
|    |--- .ro.hiringsystem
|         |--- config
|         |--- controller
|         |--- exceptions
|         |--- mapper
|         |--- model
|         |--- repository
|         |--- security
|         |--- seeders
|         |--- service
|--- HiringSystemApplication.java
|--- .resources
     |--- email_templates
     |--- application.properties
     |--- application-dev.properties
     |--- application-prod.properties

```

## A bit of explaining

Although we have defined packages for specific class roles, we sometimes mix them for the sake of convenience (having them grouped based on a bigger picture). For example the security package contains a JwtService and a SecurityConfig, which should normally be placed in the according packages (service & config).

The role of each package (in short):
- **config** - contains classes used in configuring specific aspects of the backend, mostly related to Spring Boot. Usually marked classes via the @Configuration annotation.
- **controller** - contains classes responsible for handling incoming HTTP requests and generating appropriate responses.
- **exceptions** - contains classes defining custom exceptions and error handling logic.
- **mapper** - contains classes responsible for mapping entities or DTOs (Data Transfer Objects) to each other.
- **model** - contains classes representing the entities and DTOs of the application.
- **repository** - contains classes responsible for interacting with the database or data storage.
- **security** - contains classes related to authentication, authorization, and security configurations.
- **seeders** - contains classes or scripts used for seeding/populating initial data into the database.
- **service** - contains classes that implement the business logic of the application.

Some explaination is also required for the `resources` folder, but we'll get more into that in the **Testing** section.


# Understanding the application

As we mentioned a long time ago (at the beginning of this documentation), the application is supposed to help with the **hiring system** of a company, for which we define the following domain of interests:
- **Handling Users** - having managers, interviewers and candidates that can interact on our platform
- **Emails** - we want the users to be notified of modifications that concern them (via email)
- **Jobs** - we want to be able to efficiently create/delete/modify jobs in the platform, with which the users can interact
- **Job Applications** - the actual interaction between a job and a candidate, moderated by an interviewer/manager
- **Interviews** - we also want to handle the interviewing process inside our platform, so we have to implement ways to schedule them, join them, and offer user interactions when they are in the same room
- **User Experience** - also a thing to always keep in mind, we want the website to be easy to navigate, nice looking, interactive and informative.
- **Database Actions** - for all the concepts we want to use, we need to store something about them for later usage, in other words we have to persist changes.

## Users

- **An anonymous user** (not logged in) can navigate the jobs, but can't take any action regarding them.
- **A candidate** can also interact with the jobs (apply to them), has a profile page, and special pages for applications and scheduled interviews.
- **An interviewer** can interact with job applications, schedule interviews and moderate them.
- **A manager** can do anything an interviewer can do to which we add the CRUD functionalities on jobs and users.

You can see how each user can navigate the application starting from the main page:
![userFlow](https://github.com/DragosGhinea/HiringSystem/blob/main/docs/UMLUserFlow.drawio.svg)

## Emails

The application sends emails to a candidate in the following scenarios:
- When they register
- When/If their application is accepted/denied
- When/If an interview is scheduled, and it contains them

![email1](https://github.com/DragosGhinea/HiringSystem/blob/main/docs/email1.png)
![email2](https://github.com/DragosGhinea/HiringSystem/blob/main/docs/email2.png)
![email3](https://github.com/DragosGhinea/HiringSystem/blob/main/docs/email3.png)

## Interviews

Interviewers are able to schedule interviews via a calendar which, after selecting a date, redirects them to a create form. As the interview gets created, all the candidates involved are notified on email as we have seen above. The email contains the date of the interview and a button for quick access. The interview can also be accessed via the "My interviews" page.

If you try to join an interview room before its scheduled date, a countdown pops up. After the countdown ends you are redirected to a "Preparing room" which allows you to configure your initial settings for joining the room (camera/microphone on/off).

An interview room offers:
- a chat box
- a display of the members
- mute/unmute posibility
- camera on/off posibility
- camera off for other members (room moderator only)
- mic off for other members (room moderator only)
- kick members (room moderator only)
- close room (room moderator only)

The interview room also has a self closing mechanism in case of inactivity. The room is automatically closed after 10 minutes of inactivity or if a user tries to join for the first time the room after 10 minutes from the scheduled starting date (basically it is considered cancelled).

## Project Objects

Below is the diagram consisting the models of the application so you can get a clearer view on them.

![objects](https://github.com/DragosGhinea/HiringSystem/blob/main/docs/UMLEntities.drawio.svg)

# Testing the application

Inside pom.xml we have configured two profiles, **dev and prod**. Dev is used for testing purposes while prod is the deployment configuration. Besides the application.properties which is common to both, we have two separate files for each. The main thing that differs for each is the database. For dev/testing we are using a H2 database and for production we are using a PostgreSQL database. Spring Boot allows us to configure the database connection and driver to be used by Hibernate.

To help us test the application, we are using seeders to create users, jobs, interviews and relationships between them.

We also have a custom config setting `app.data.seeding.enabled` which is set to true in dev only, so we don't seed data in production.

## Automatic Testing

We also wanted to experiment a bit with automatic testing, even though we have not created automated tests for all the business logic inside the application, we created tests for two services (jobs and interview conference rooms related).

For automatic testing we used **JUnit**, a popular testing framework for Java and **Mockito**, a framework which allows us to mock dependencies so we can better test only the logic we are interested in. In our case, the repositories are mocked, so we are not dependent of their implementation, when testing the business logic.

# Running the application
As mentioned in a previour section, even though the frontend and backend are grouped, they run separately.

To start the backend you would use `mvn spring-boot:run`, after installing the proper dependencies via `mvn clean install`, which will start it on the port 8081 (editable in application.properties). The backend endpoints (offered by controller and websockets) are api routes, therefore all of them start with **/api/v1/**. Take a look at the controllers to see all the routes and also at WebSocketConfig.java for the socket communication endpoints.

To start the frontend you would use `npm start`, after installing the required dependencies via `npm install`. The application starts on port 3000 (default for REACT).

The frontend and backend communicate via axios requests.

# More images from the application

![app1](https://github.com/DragosGhinea/HiringSystem/blob/main/docs/app1.png)
![app2](https://github.com/DragosGhinea/HiringSystem/blob/main/docs/app2.png)
![app3](https://github.com/DragosGhinea/HiringSystem/blob/main/docs/app3.png)
![app4](https://github.com/DragosGhinea/HiringSystem/blob/main/docs/app4.png)
![app5](https://github.com/DragosGhinea/HiringSystem/blob/main/docs/app5.png)
![app6](https://github.com/DragosGhinea/HiringSystem/blob/main/docs/app6.png)
![app7](https://github.com/DragosGhinea/HiringSystem/blob/main/docs/app7.png)
![app8](https://github.com/DragosGhinea/HiringSystem/blob/main/docs/app8.png)
![app9](https://github.com/DragosGhinea/HiringSystem/blob/main/docs/app9.png)
![app10](https://github.com/DragosGhinea/HiringSystem/blob/main/docs/app10.png)

# To keep in mind for future
- The application is not responsive! We didn't have enough time to properly handle the design of the frontend.
- We made the skeleton of the security for the application by ensuring the tokens work, but we do not take into account privileges when accessing pages. If we go back to this project, that is a **must do**.
- The frontend forms offer validation messages of its inputs, but the email sending takes a while and it does not indicate that an email is being sent, it just freezes there.

# Bibliography

Things we used to help us build this application:

- https://www.baeldung.com/spring-boot-hibernate
- https://start.spring.io/
- https://www.baeldung.com/security-spring
- https://www.youtube.com/watch?v=KxqlJblhzfI
- https://www.youtube.com/playlist?list=PLC3y8-rFHvwgg3vaYJgHGnModB54rxOk3
- https://www.baeldung.com/java-dto-pattern
- https://www.youtube.com/watch?v=tIdNeoHniEY
- https://getbootstrap.com/docs/5.3/
- https://icons.getbootstrap.com/
- https://www.baeldung.com/java-clean-code
- https://www.oracle.com/ro/technical-resources/articles/java/javadoc-tool.html
- https://www.npmjs.com/package//react-big-calendar
- https://particles.js.org/samples/index.html#slow
- https://www.npmjs.com/package/simple-peer
- https://chat.openai.com/
- https://www.baeldung.com/junit-5
- https://www.baeldung.com/mockito-series
- https://www.drawio.com/