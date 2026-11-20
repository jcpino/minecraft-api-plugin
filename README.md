# Minecraft API Plugin

Custom Java plugin for Paper 1.8.8.

## Functionality
Listens to the PlayerJoinEvent and sends player data (name, rank, timestamp) as a JSON payload to a Spring Boot REST API. Uses asynchronous HTTP POST requests to prevent server lag.

## Tech Stack
- Java 8
- Paper API 1.8.8
- Maven
- HTTP Client