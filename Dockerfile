FROM openjdk:17
EXPOSE 8080
ADD /target/contest-back-0.0.1-SNAPSHOT.jar contest-back.jar
ENTRYPOINT ["java","-jar","contest-back.jar"]
