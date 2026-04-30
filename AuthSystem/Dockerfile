FROM eclipse-temurin:25-jdk-alpine

WORKDIR /app

COPY target/*.jar app.jar

ENV TZ=Asia/Kolkata
ENTRYPOINT ["java", "-Duser.timezone=Asia/Kolkata", "-jar", "app.jar"]

