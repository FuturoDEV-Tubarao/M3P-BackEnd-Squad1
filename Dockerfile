FROM openjdk:17-jdk-slim-buster AS build
# copia todos os arquivos da aplicação para a imagem

COPY . .
# Comando para tornar o mvnw em executável

RUN chmod 700 mvnw
# mesma função do install, gerar o .jar

RUN ./mvnw clean package 

# ******* Segunda parte da imagem
FROM openjdk:17-jdk-slim-buster

# Cria uma pasta
WORKDIR app

# Buscar o jar criado anteriormente
COPY --from=build target/*.jar app.jar

# Executa o jar gerado
ENTRYPOINT ["java", "-jar", "app.jar"]
