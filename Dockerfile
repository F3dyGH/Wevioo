FROM openjdk:8-jdk-alpine
ARG JAR=target/*.jar
COPY ${JAR} app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
