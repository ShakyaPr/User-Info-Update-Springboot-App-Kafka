# User-Info-Update Springboot App with Kafka Integration

![Architecture](https://github.com/ShakyaPr/User-Info-Update-Springboot-App-Kafka/blob/main/architecture.png)

## Overview

This Springboot application is designed to update user information and integrate with Kafka for event processing. The application follows a series of steps to fetch user data from the GitHub API, create a payload, and store it in a database. It then utilizes Kafka to send the payload as an event named "UserInfoChanged" to the "users" topic.

## How it Works

1. **Sending User Information Update Request**
   - Send a PUT request to the `/users/:user_id` endpoint of the Springboot application to update user information.

2. **Fetching User Data**
   - The application initiates a GET request to the GitHub API, using the `user_id` as a path parameter, to retrieve information starting from the `since` variable.
   - The application receives the next 30 users, starting from the given `since` variable value.
   - From the response, the `user_name` is extracted from the first element, representing the user with the given `user_id`.
   - Three additional API calls are made to the GitHub API using the `user_name` to fetch the user's basic details, list of followers, and list of repositories. This is handled by the `GitHubService`.

3. **Creating the Payload**
   - Using the responses obtained from the previous API calls and the request body of the user request made in step 1, the application creates a payload.
   - The payload is then stored in the database for further processing.

4. **Triggering Kafka Event**
   - User sends a POST request to the `/produce/:user_id` endpoint with an empty body.
   - The application retrieves the payload associated with the given `user_id` from the request and sends it to the `EventProducerService` for further processing.

5. **Event Processing with Kafka**
   - When the `EventProducerService` receives the payload, it creates a `UserInfoChanged` event.
   - The event is then sent to the Kafka server using the "users" topic for further event processing.

## Instructions for Deployment

1. Clone the project from GitHub: 
    git clone https://github.com/ShakyaPr/User-Info-Update-Springboot-App-Kafka.git
    cd User-Info-Update-Springboot-App-Kafka/

2. Deploy PostgreSQL Database, Kafka Server, and Kafdrop using Docker: 
    docker run --name postgres -e POSTGRES_PASSWORD=admin12#$ -d -p 5432:5432 postgres
    docker run --net=host -p 2181:2181 -p 9092:9092 -e ADVERTISED_HOST=127.0.0.1 -e NUM_PARTITIONS=10 --name kafka_server johnnypark/kafka-zookeeper

3. Ensure that port 8000 is available on your system.

4. Run the application: ./mvnw spring-boot:run
