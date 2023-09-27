FROM openjdk:17-jdk-slim
COPY target/sample-project-0.0.1.jar sample-project-0.0.1.jar
CMD ["java","-jar","/sample-project-0.0.1.jar"]