# Use an official OpenJDK image as the base image
FROM openjdk:17-jdk-alpine

# Copy the JAR file generated by Maven into the Docker image
ADD target/tp-foyer.jar foyer.jar

# Expose the application port
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app.jar"]

//FROM openjdk:17

//WORKDIR /app

//COPY ./target/tp-foyer-5.0.0.jar /app/tp-foyer-5.0.0.jar

//EXPOSE 8080

//CMD ["java", "-jar", "/app/tp-foyer-5.0.0.jar"]