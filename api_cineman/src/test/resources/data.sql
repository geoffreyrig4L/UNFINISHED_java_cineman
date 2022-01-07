DROP TABLE IF EXISTS films;

CREATE TABLE films (
                       id INT Auto_Increment NOT NULL,
                       title VARCHAR(50) NOT NULL,
                       dateReleased VARCHAR(4) NOT NULL,
                       watched boolean not null,
                       PRIMARY KEY (id)
);