FROM openjdk:17

WORKDIR /app

COPY ./target/tp-foyer-5.0.0.jar /app/tp-foyer-5.0.0.jar

EXPOSE 8082

CMD ["java", "-jar", "/app/tp-foyer-5.0.0.jar"]
