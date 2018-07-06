# Build Reactive Rest APIs with Spring WebFlux and Reactive MongoDB

Read the tutorial : https://www.callicoder.com/reactive-rest-apis-spring-webflux-reactive-mongo/

## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

3. MongoDB - 3.x.x

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/callicoder/spring-webflux-reactive-rest-api-demo.git
```

**2. Build and run the app using maven**

```bash
cd spring-webflux-reactive-rest-api-demo
mvn package
java -jar target/webflux-demo-0.0.1-SNAPSHOT.jar
```

Alternatively, you can run the app without packaging it using -

```bash
mvn spring-boot:run
```

The server will start at <http://localhost:8080>.

## Exploring the Rest APIs

The application defines following REST APIs

```
1. GET /tweet - Get All Tweets

2. POST /tweet - Create a new Tweet

3. GET /tweet/{id} - Retrieve a Tweet by Id

3. PUT /tweet/{id} - Update a Tweet

4. DELETE /tweet/{id} - Delete a Tweet

4. GET /tweet/stream - Stream tweets to a browser as Server-Sent Events
```

## Running integration tests

The project also contains integration tests for all the Rest APIs. For running the integration tests, go to the root directory of the project and type `mvn test` in your terminal.