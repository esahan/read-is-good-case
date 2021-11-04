# ReadIsGood

Case: ReadingIsGood is an online books retail firm which operates only on the Internet

## Author
Ege Şahan

## Installation

Use docker-compose.yml file to install.

```bash
docker-compose up
```


Or

You can use dockerfile to dockerize project and use it with any mongo instance

```bash
docker build -t read-is-good .
```

## docker-compose-yml for development purpose

```bash
version: '3'
services:
  mongo:
    image: mongo
    hostname: mongo
    container_name: mongodb
    entrypoint: ["/usr/bin/mongod","--bind_ip_all","--replSet","rs0"]
    ports:
      - "27017:27017"
  mongosetup:
    image: mongo
    depends_on:
      - mongo
    restart: "no"
    entrypoint: [ "bash", "-c", "sleep 10 && mongo --host mongo:27017 --eval 'rs.initiate()'"]
  getir-api:
    container_name: getir-app
    build: .
    restart: always
    depends_on:
      - mongosetup
    environment:
      - "SPRING_PROFILES_ACTIVE=docker"
    ports:
      - "8080:8080"
```
## Default Users

user : admin@readisgood.com

pwd  : 12345

generated with "ROLE_ADMIN" via command line runner if it is not already exists in db


## API Documentation 

```bash
Swagger V2 : <host>:<port>/swagger-ui
```
