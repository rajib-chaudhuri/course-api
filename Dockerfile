FROM java:8
EXPOSE 8082
ADD course-api-0.0.1-SNAPSHOT.jar course-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "course-api-0.0.1-SNAPSHOT.jar"]
