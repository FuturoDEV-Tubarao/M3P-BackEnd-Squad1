# LabFoods - M3P-FrontEnd-Squad1

# Sobre o projeto

Este projeto é o frontend de uma aplicação web full-stack **-LabFoods-** que permite aos usuários se registrarem, postar receitas, receber avaliações em suas receitas e também avaliar receitas de outros usuários. O frontend é construído com React, TypeScript e styled-components. 

O projeto é desenvolvido para resolver o desafio final do modulo 3 do curso **Análise e Desenvolvimento Web**, curso oferecido pelo grupo **Senai - Lab365**.

## Link do Site
- https://www.labfoods.com.br
  
# Tecnologias Utilizadas
## Back end
- Jave 17
- Spring Framework
- Postgresql


 **Extra** 
 - ModelMapper
 - Logback
 - Lombok
 - Token JWT
 - Swagger
 - Render
 - Vercel
 - Dockerfile
   
# Como executar o projeto

```bash
# clonar repositório
git clone: https://github.com/FuturoDEV-Tubarao/M3P-BackEnd-Squad1

## executar o Dockerfile

### Build project
mvn package

### Build the Docker Image
docker build -t labfoods .

### Tagging the Docker Image
docker tag labfoods nathalialanzendorf/labfoods:latest

### Pushing the Docker Image to Dockerhub
docker push nathalialanzendorf/labfoods:latest 

# acessar o Swagger
http://localhost:8080/swagger-ui/index.html

```

# Autores

- Manoel Cavenati Fernandes Neto
- Milena Clara Ribeiro Rizzi
- Nathalia Lanzendorf

