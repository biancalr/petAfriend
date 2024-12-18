# Pet A Friend

Endpoints of the application

## Usage

<h3>Pets Endpoints</h3>

Register a Pet
```curl 
curl --location 'http://{host}:{port}/api/pets/register' \
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
curl --location 'http://{host}:{port}/api/pets/show?specie=dog&page=0&size=10&status=available'
```

Find a Pet with given Id
```curl
curl --location 'http://{host}:{port}/api/pets/get?id=1'
```

<h3>Clients Endpoints</h3>

Register a Client
```curl 
curl --location 'http://{host}:{port}/api/clients/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username":"user_name",
    "email":"user@mail" 
}'
```

Find certain client with the given id
```curl
curl --location 'http://{host}:{port}/api/clients/get?id=1'
```

-(To Implement) Update certain client with given id
```curl
curl --location --request PUT 'http://localhost:8080/api/clients/update' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1
}'
```

<h3>Rents Endpoints</h3>

Rent a Pet
```curl
curl --location 'http://{host}:{port}/api/pets/register' \
--header 'Content-Type: application/json' \
--data-raw '{
     "hours":3,
     "clientId": 1,
     "petId": 1,
     "startsAt": "2024-11-02 12:00"
            
}'
```

Cancel a rent
```curl
curl --location 'http://{host}:{port}/api/rents/cancel' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1
}'
```

<h5>Update schedules</h5>

<p>Both rent status and pet availability are updated by schedules 
based of time start and time end of a rent</p>
