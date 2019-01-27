# Build + Run Instructions

* Install latest version of Gradle: https://gradle.org/install/
* Have Java8 installed and accessible on PATH
* Go to project root folder
* Run: gradle build
* Go to project/build/libs
* To run server instance: java -jar numbers-game-service-0.1.0.jar
* To run client instance: java -jar numbers-game-service-0.1.0.jar --instanceType=PLAYER2

# Short Overview

Numbers Game Service is implemented using event driven architecture. In the center is simple event bus
(located in com.takeaway.numbers.eventbus). Event bus transmits events (POJOs) between PLAYER1 (server) and PLAYER2
(client) in asynchronous way, using single tcp connection.
Business logic is encapsulated in event handlers (located in com.takeaway.numbers.eventbus.handler) and service classes
(located in com.takeaway.numbers.service).


