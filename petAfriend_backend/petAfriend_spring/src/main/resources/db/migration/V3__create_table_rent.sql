CREATE TABLE RENT(
    ID INT PRIMARY KEY AUTO_INCREMENT,
    CLIENT_ID INT NOT NULL,
    PET_ID INT NOT NULL,
    HOURS INT NOT NULL,
    STATUS VARCHAR(10) DEFAULT 'STARTED',
    CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    UPDATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    CONSTRAINT FK_USER_RENT FOREIGN KEY (CLIENT_ID) REFERENCES CLIENT(ID),
    CONSTRAINT FK_RENT_PET FOREIGN KEY (PET_ID) REFERENCES PET(ID)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;