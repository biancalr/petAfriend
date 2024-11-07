package com.pets.petAfriend.features.shared;

public class ApiExamples {

    private ApiExamples() {
    }

    public static final String PET_RESPONSE_REGISTERED_DTO_SUCCESS = """
            {
                "id": 1,
                "status": 201,
                "message": "success",
                "entity": "Pet"
            }""";

    public static final String PET_RESPONSE_REGISTERED_DTO_ERROR = """
            [
                400,
                "2024-10-26T05:57:37.150+00:00",
                [
                    "A raça é obrigatória",
                    "A personalidade é obrigatória",
                    "O nome do pet é obrigatório",
                    "A espécie é obrigatória"
                ]
            ]""";

    public static final String PET_REQUEST_REGISTER_DTO_SUCCESS = """
            {
                "name":"Rabby",
                "type": "rabbit",
                "specie": "English Lop",
                "personality": "energic"
            }""";

    public static final String PET_RESPONSE_LIST_PAGE_SUCESS = """
            {
                "content": [
                    {
                        "id": 1,
                        "name": "Doggo",
                        "specie": "dog",
                        "breed": "golden retriever",
                        "personality": "goofy",
                        "status": "AVAILABLE"
                    },
                    {
                        "id": 2,
                        "name": "Catty",
                        "specie": "cat",
                        "breed": "Scottish Fold",
                        "personality": "quiet",
                        "status": "AVAILABLE"
                    },
                    {
                        "id": 3,
                        "name": "Rabby",
                        "specie": "rabbit",
                        "breed": "English Lop",
                        "personality": "energic",
                        "status": "AVAILABLE"
                    }
                ],
                "pageable": {
                    "pageNumber": 0,
                    "pageSize": 10,
                    "sort": {
                        "empty": true,
                        "sorted": false,
                        "unsorted": true
                    },
                    "offset": 0,
                    "paged": true,
                    "unpaged": false
                },
                "last": true,
                "totalPages": 1,
                "totalElements": 3,
                "size": 10,
                "number": 0,
                "sort": {
                    "empty": true,
                    "sorted": false,
                    "unsorted": true
                },
                "first": true,
                "numberOfElements": 3,
                "empty": false
            }""";

    public static final String PET_RESPONSE_LIST_PAGE_ERROR = """
            [
                400,
                "2024-10-26T07:17:18.428+00:00",
                [
                    "Invalid value for parameter 'availability': availbiity"
                ]
            ]""";

    public static final String PET_RESPONSE_DTO_SUCCESS = """
            {
                "id": 2,
                "name": "Catty",
                "specie": "cat",
                "breed": "Scottish Fold",
                "personality": "quiet",
                "status": "AVAILABLE"
            }""";

    public static final String CLIENT_RESPONSE_REGISTERED_DTO_SUCCESS = """
            {
                "id": 1,
                "status": 201,
                "message": "success",
                "entity": "Client"
            }""";

    public static final String CLIENT_RESPONSE_REGISTERED_DTO_ERROR = """
            [
                400,
                "2024-10-26T05:57:37.150+00:00",
                [
                    "Username already exists"
                ]
            ]""";

    public static final String CLIENT_REQUEST_REGISTER_DTO_SUCCESS = """
            {
               "username":"user_name",
                "email":"user@mail.com" \
            }""";

    public static final String CLIENT_RESPONSE_DTO_SUCCESS = """
            {
                "id": 1,
                "username": "user_name",
                "email": "user@mail.com"
            }""";

    public static final String RENT_REQUEST_REGISTER_DTO_SUCCESS = """
            {
                "hours":3,
                "clientId": 1,
                "petId": 1,
                "startsAt": "2024-11-02 12:00"
            }""";

    public static final String RENT_RESPONSE_REGISTERED_DTO_SUCCESS = """
            {
                "id": 1,
                "status": 201,
                "message": "success",
                "entity": "Rent"
            }""";

    public static final String RENT_RESPONSE_REGISTERED_DTO_ERROR = """
            [
                400,
                "2024-10-26T05:57:37.150+00:00",
                [
                    "Minimal time rent is 1",
                    "Time rent too long",
                ]
            ]""";

    public static final String RENT_RESPONSE_CANCELED_DTO_SUCCESS = """
            {
                "id": 1,
                "status": 201,
                "message": "success",
                "entity": "Rent"
            }""";

}
