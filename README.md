# M3P-BackEnd-Squad1
Repositório de back-end do projeto do módulo 3.

## Acessar o Swagger
http://localhost:8080/swagger-ui/index.html

## Build project
mvn package

## Build the Docker Image
docker build -t labfoods .

## Tagging the Docker Image
docker tag labfoods nathalialanzendorf/labfoods:latest

## Pushing the Docker Image to Dockerhub
docker push nathalialanzendorf/labfoods:latest 
