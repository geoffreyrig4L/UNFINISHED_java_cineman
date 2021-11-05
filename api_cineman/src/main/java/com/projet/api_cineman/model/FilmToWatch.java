package com.projet.api_cineman.model;

import lombok.Data;

import javax.persistence.*;

@Data   //genere guetter et setter
@Entity //indique que la classe correspond à une table de la bdd
@Table(name="films_a_voir")  //indique le nom de la table
public class FilmToWatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="title")
    private String title;

    @Column(name="date_released")
    private String dateReleased;
}
