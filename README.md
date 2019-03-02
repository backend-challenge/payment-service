# Invillia Payment Server

# Link do Swagger

http://localhost:8060/swagger-ui.html#/

## Requisitos
Para construir a aplicação é necessário que o container com o banco de dados já esteja rodando, caso não esteja é só rodar o seguinte comando:
    
    docker pull mysql
    docker run --name mysql-database -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=backend_challenge -e MYSQL_USER=springuser -e MYSQL_PASSWORD=ThePassword -d mysql:8



## Como rodar a aplicação

1) Fazer checkout do projeto.
2) Fazer o build do projeto com o comando:
        
        mvn clean package
3) Construir o imagem com o Dockerfile: 

        docker build . -t docker-payment-service
4) Subir a aplicação no container: 

        docker run -p 8060:8060 --name docker-payment-service --link mysql-database:mysql -d docker-payment-service

## Funcionalidades
    Neste micro-serviço foi implementado as seguintes funcionalidades:
        1) Pagemento de uma ordem
        2) Solicitação de reembolso de uma ordem
        3) Solicitação de reembolso de um item de uma ordem
        4) Rotina que roda diariamente verificando se o estorno foi solicitado a mais de 10 dias e caso essa condição seja verdade reembolsamos a ordem
