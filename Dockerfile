FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/labfoods-0.0.1-SNAPSHOT.jar labfoods.jar
ENTRYPOINT ["java","-jar","/labfoods.jar"]