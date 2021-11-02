FROM openjdk:8-jre-alpine
EXPOSE 9090
ARG WAR_FILE=./target/pipelineTest-0.0.1-SNAPSHOT.war
ADD ${WAR_FILE} app.war
ENTRYPOINT ["java","-jar","/app.war"]