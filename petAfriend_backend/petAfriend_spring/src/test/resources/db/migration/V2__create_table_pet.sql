CREATE TABLE PET(
    ID BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NAME VARCHAR(30) NOT NULL,
    STATUS VARCHAR(12) NOT NULL,
    SPECIE VARCHAR(30) NOT NULL,
    PERSONALITY TEXT NOT NULL,
    CREATED_AT TIMESTAMP NOT NULL,
    constraint pk_pet primary key (id)
);