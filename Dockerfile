FROM openjdk:11-jre
ADD build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]