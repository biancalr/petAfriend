CREATE TABLE CLIENT(
    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    USERNAME VARCHAR(20) NOT NULL,
    EMAIL VARCHAR(50) NOT NULL,
    CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP()
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;