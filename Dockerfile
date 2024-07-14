FROM eclipse-temurin:21-jdk as build
COPY . /app
WORKDIR /app
# Ensure the Maven build names the JAR file explicitly, e.g., app.jar
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre
ARG DATABASE_URL
ARG DATABASE_USERNAME
ARG DATABASE_PASSWORD
ARG PORT
ENV PORT=${PORT}
# Copy the explicitly named JAR file
COPY --from=build /app/target/app.jar .
RUN useradd runtime
USER runtime
ENTRYPOINT [ "java", "-Dserver.port=${PORT}", "-jar", "app.jar" ]