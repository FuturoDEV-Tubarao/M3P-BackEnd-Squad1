# M3P-BackEnd-Squad1
Repositório de back-end do projeto do módulo 3.

#Swagger

## Acessar o Swagger
http://localhost:8080/swagger-ui/index.html


#Dockerfile

## Commands

### Build project
mvn package

### Build the Docker Image
docker build -t labfoods .

### Tagging the Docker Image
docker tag labfoods nathalialanzendorf/labfoods:latest

### Pushing the Docker Image to Dockerhub
docker push nathalialanzendorf/labfoods:latest 

#DockerHub

- https://hub.docker.com/r/nathalialanzendorf/labfoods/tags

#Deploy database

##Render

Fonte: 


#Deploy service

## Render

Fontes: 
 - https://hostingtutorials.dev/blog/free-spring-boot-host-with-render
 - https://medium.com/spring-boot/free-hosting-bliss-deploying-your-spring-boot-app-on-render-d0ebd9713b9d





 https://www.baeldung.com/spring-security-cors-preflight
https://community.render.com/t/problem-with-fixing-cors-lost-header-issue/12544
https://community.render.com/t/request-blocked-by-cors-policy/15918
https://www.baeldung.com/spring-cors
@Bean
CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("*"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}