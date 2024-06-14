FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21
COPY --from=build /target/assgn2-0.0.1-SNAPSHOT.jar assgn2.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "assgn2.jar"]