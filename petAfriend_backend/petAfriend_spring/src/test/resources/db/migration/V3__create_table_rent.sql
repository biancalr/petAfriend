CREATE TABLE RENT(
    ID BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    CLIENT_ID INT NOT NULL,
    PET_ID INT NOT NULL,
    HOURS INT NOT NULL,
    STATUS VARCHAR(10),
    CREATED_AT TIMESTAMP NOT NULL,
    UPDATED_AT TIMESTAMP NOT NULL,
    CONSTRAINT PK_RENT PRIMARY KEY (ID),
    CONSTRAINT FK_USER_RENT FOREIGN KEY (CLIENT_ID) REFERENCES CLIENT(ID),
    CONSTRAINT FK_RENT_PET FOREIGN KEY (PET_ID) REFERENCES PET(ID)
);