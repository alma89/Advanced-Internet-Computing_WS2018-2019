FROM openjdk:8-jdk
WORKDIR /app
EXPOSE 80
EXPOSE 53
COPY target/app.jar /app/app.jar
ENTRYPOINT java -jar app.jar