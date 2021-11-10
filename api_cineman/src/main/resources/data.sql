CREATE TABLE films_vus(
    id INT Auto_Increment NOT NULL,
    title VARCHAR(50) NOT NULL,
    date_released VARCHAR(4) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE films_a_voir (
    id INT Auto_Increment NOT NULL,
    title VARCHAR(50) NOT NULL,
    date_released VARCHAR(4) NOT NULL,PRIMARY KEY (id)
);



INSERT INTO films_vus (id,title,date_released) VALUES
    (1, 'Insomnia', '2002'),
    (2, 'Zodiac', '2007'),
    (3, 'Les sentiers de la perdition', '2002');

INSERT INTO films_a_voir (id,title,date_released) VALUES
    (1, 'Le loup de Wall Street', '2013'),
    (2, 'Taxi Driver', '1976');