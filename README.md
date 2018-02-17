# Credit X - project that uses kafka, springboot, spring cloud stream and React for interest Risk managment. 

This repository is only for demonstrative purpose, is simple CRUD with spring boot as Backend and React consuming the api service.

# Start

Tools that need for running this project:

- For running containers of Kafka and Postgre
	- Docker
	- Docker-compose
- For running the java application in spring boot
	- Maven
	- Java 8
- For the frontend:
	- Node
	- Npm
	

## 1- Running kafka, zookeeper and postgreSQL

Go to **docker** folder, and run **docker-compose up**

## 2- Run spring application 

 - Go to the folder **microservices** and run **mvn install**
 - Go to the **customer-service** folder  and **mvn spring-boot:run**, this will spring in dev mod, for running in prd mode use **mvn spring-boot:run -Pprod**
 - Go to the **risk-managment-service** folder  and **mvn spring-boot:run**, this will spring in dev mod, for running in prd mode use **mvn spring-boot:run -Pprod**

## 3- Run the frontend

- Go to the folder **frontend** and run **npm install** or **yarn** if you have.
- To run the project use **npm run start** for dev mode
-  If you want to build the bundle.js use **npm run prod**
- Test not implemented yet
- Go to **localhost:3000** to use the interface

# To see more about project get readme in each folder 

