# Credit X - Microservices. 



## Overview

The mainly microservices uses:

-  Maven for dependency managment.
-  Spring-cloud-stream generic approach to make your application based on events and queues.
-  Spring-boot for auto configuration.
-  Jpa for entity mapping 
-  Hibernate ORM
-  Lombok auto-generate getter, setters and constructors
-  Spring profiles for managment of ambients.
-  And H2 for tests
 
## customer-commons

Created for sharing models and other think about the customer for other microservices.

## customer-services

Created for handle the information of the customer, like credit risk. This service also has an async call to calculate the interest of credit limit, this is made with a queue customer-interest-percentage-calculate-queue, and received back with the queue customer-interest-percentage-calculate-done-queue, and then saved on DB.

## risk-managment-service

Is the trade rules of the microservices, risk-managment-service take cares of the rules of the business, like how much of interest its apply by credit limit of Y.This microservice stay listen for messages on customer-interest-percentage-calculate-queue and calculate the interest and after is done, the result is put in the customer-interest-percentage-calculate-done-queue for the customer-service save in the DB.