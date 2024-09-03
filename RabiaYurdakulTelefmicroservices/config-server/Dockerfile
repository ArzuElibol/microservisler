# Base image
FROM openjdk:17-jdk-alpine

# Argument to specify the JAR file, default is target/*.jar
ARG JAR_FILE=target/*.jar

# Set the working directory inside the container
WORKDIR /opt/app

# Copy the JAR file to the working directory
COPY ${JAR_FILE} app.jar

# Specify the entry point command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
