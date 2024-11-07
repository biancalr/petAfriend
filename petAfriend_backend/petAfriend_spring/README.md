<p align="center">
  <a href="https://spring.io/" target="blank"><img src="https://www.svgrepo.com/show/376350/spring.svg" width="120" alt="Spring Logo" /></a>
</p>

# Pet A Friend 

Start simple description

## Installation

Run Spring Boot app with java -jar command

```bash
java -jar target/petAfriend-0.0.1-SNAPSHOT.war
```
Run Spring Boot app using Maven
```bash
mvn spring-boot:run
```

## Usage

Register a Pet
```curl 
curl --location 'http://localhost:8080/api/pets/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name":"Rabby",
    "type": "rabbit",
    "specie": "English Lop",
    "personality": "energic"
}'
```

List avaliable pets (Returns a pagination). The 'specie' and 'status' are both optionals. When 'specie' is null, the result is not filtered. However, 'status' is filled with 'available' by default.
```curl 
curl --location 'http://localhost:8080/api/pets/show?specie=dog&page=0&size=10&status=available'
```

Register a Client
```curl 
curl --location 'http://localhost:8080/api/clients/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username":"user_name",
    "email":"user@mail" 
}'
```
Rent a Pet
```curl
curl --location 'http://localhost:8080/api/pets/register' \
--header 'Content-Type: application/json' \
--data-raw '{
     "hours":3,
     "clientId": 1,
     "petId": 1,
     "startsAt": "2024-11-02 12:00"
            
}'
```

## License

[MIT](https://choosealicense.com/licenses/mit/)