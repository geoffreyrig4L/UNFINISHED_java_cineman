drop database if exists cineman_v2;
CREATE database cineman_v2;
use cineman_v2;

DROP TABLE IF EXISTS films_vus;

CREATE TABLE films (
    id INT Auto_Increment NOT NULL,
    title VARCHAR(50) NOT NULL,
    date_released VARCHAR(4) NOT NULL,
    watched boolean not null,
    PRIMARY KEY (id)
);



INSERT INTO films (id,title,date_released, watched) VALUES
    (1, 'Insomnia', '2002', true),
    (2, 'Zodiac', '2007', true),
    (3, 'Les sentiers de la perdition', '2002', true),
    (4, 'Le loup de Wall Street', '2013', false),
    (5, 'Taxi Driver', '1976', false);