package com.projet.cineman.service;

import com.projet.cineman.model.Film;
import com.projet.cineman.repository.FilmProxy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Data   //getter et setter
@Service //agit comme l'annot component + specifie que la classe gere le traitement métier

//Classe qui intègre des méthodes CRUD
//agit comme un relais, on peut dire quil agit comme le controller finalement

public class FilmService {

    @Autowired //permet de réaliser l'injection automatiquement entre les beans de l'application
    private FilmProxy filmProxy;    //récupère les données de la classe filmProxy

    //READ
    public Film getFilm(final int id) {
        return filmProxy.getFilm(id);
    }

    public Iterable<Film> getAllFilms(){        //iterabale declare que l'objet Film sera la cible d'une boucle for each afin de parcourir toutes les instances de Film
        return filmProxy.getAllFilms();
    }

    //DELETE
    public void deleteFilm(final int id){
        filmProxy.deleteFilm(id);
    }

    //UPDATE
    public Film saveFilm(Film f){
        Film savedFilm;
        //petit traitement  métier :
            //pour mettre en majuscule les titres
        f.setTitle(f.getTitle().toUpperCase());

        if(f.getId() == null){
            savedFilm = filmProxy.createFilm(f);
        } else {
            savedFilm = filmProxy.updateFilm(f);
        }
        return savedFilm;
    }

    //CREATE
    public void createFilm(Film f)  {
        filmProxy.createFilm(f);
    }
}
