FROM openjdk:11
COPY target/recruitment.jar recruitment.jar
ENTRYPOINT ["java","-jar","/recruitment.jar"]