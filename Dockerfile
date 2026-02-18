FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/java-demo-app-1.0.jar app.jar

EXPOSE 8085

CMD ["java", "-jar", "app.jar"]
