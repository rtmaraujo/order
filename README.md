# Projeto de Serviço de Pedidos com Spring Boot e Kafka

Este projeto é um serviço de pedidos desenvolvido com Spring Boot e Kafka, que permite criar e ler pedidos. O serviço também integra com um produto externo A para receber pedidos e produto externo B para enviar dados de um Pedido.

## Passo a passo para baixar e configurar o projeto
1. Git clone no projeto
2. Abra o mesmo na ide de sua preferência
3. Certifique-se que esteja utilizando o Java 21
4. Executar o comando mvn clean install para baixar as dependência relacionadas ao projeto
5. Para executar local, realizar configuração padrão do Java(Springboot) para executar a classe main( OrderApplication).
6. Para acesso ao swagger - http://localhost:8080/swagger-ui/index.html
7. Para acessar o h2 -http://localhost:8080/h2-console com as configurações no application.properties.


## Tecnologias utilizadas
1. Java 21
2. Spring Boot 3.3.5
3. Maven
4. Lombok
5. Springdoc OpenAPI
6. H2 Database(Testes local) e Postgresql(Entrega final)
7. Kafka
8. Junit
9. OpenFeign