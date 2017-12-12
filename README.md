Spring Boot JMS Sandbox
================================
Spring Boot version of the spring-jms-sandbox project (located in this repository). This project uses Spring Boot
to autoconfigure most of the JMS features. In addition, rather than creating DefaultMessageListenerContainers
manually, this project uses @EnableJms and @JmsListener to do this.

## Overview
This app starts up, creates a "User" object, pushes it onto the userQueue, and then lets the UserReceiver class
consume the message and publish a confirmation message to the confirmation queue. Logging is done to the console.

## Requirements
* A standalone, ActiveMQ Broker
  *an embedded broker could be used but you will need to modify the POM and Application.java


## Running
Run the main Spring Boot applicaton class:  **Application**