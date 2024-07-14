FROM eclipse-temurin:17-jdk-alpine
# copia todos os arquivos da aplicação para a imagem

# Cria uma pasta
VOLUME /tmp

# Buscar o jar criado anteriormente
COPY target/*.jar app.jar

# Executa o jar gerado
ENTRYPOINT ["java","-jar","/app.jar"]




