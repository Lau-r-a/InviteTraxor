FROM maven:3.8-openjdk-18-slim as builder
WORKDIR /app
COPY . .
RUN mvn clean package

FROM openjdk:18-slim
WORKDIR /app
COPY --from=builder /app/target/*dependencies.jar /app/app.jar
COPY .env /app/.env
#EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


