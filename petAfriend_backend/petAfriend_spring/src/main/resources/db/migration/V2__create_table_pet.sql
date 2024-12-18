CREATE TABLE PET(
    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(30) NOT NULL,
    STATUS VARCHAR(12) NOT NULL DEFAULT 'AVAILABLE',
    SPECIE VARCHAR(30) NOT NULL,
    PERSONALITY TEXT NOT NULL,
    CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP()
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;