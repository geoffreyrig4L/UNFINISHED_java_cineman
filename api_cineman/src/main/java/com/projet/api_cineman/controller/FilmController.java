package com.projet.api_cineman.controller;

import com.projet.api_cineman.model.Film;
import com.projet.api_cineman.service.FilmService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/films")
public class FilmController {

    private FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public ResponseEntity<Page<Film>> getAllFilms(@RequestParam("page") final Optional<Integer> page, @RequestParam("sortBy") final Optional<String> sortBy) {
        Page<Film> filmList = filmService.getAllFilms(page, sortBy);
        return ResponseEntity.ok(filmList);
    }

    /*
        @RequestParam recupere des infos concernant les ressources, tout ce qu'on peut trouver apres le ?, ses infos servent principalement de filtrage
        @PathVariable récupère la ressource directement soit les champs contenu dans notre bdd (id, title, date_released)
     */

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilm(@PathVariable("id") final Long id) {     //PathVariable -> permet de manipuler des variables dans l'URI de la requete mapping
        Optional<Film> film = filmService.getFilm(id); //Optional -> encapsule un objet dont la valeur peut être null
        if (film.isPresent()) {   //si il existe dans la bdd
            return ResponseEntity.ok(film.get());  //recupere la valeur de film
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> createFilm(@RequestBody Film film) {         // deserialise les JSON dans un langage Java -> regroupe des données séparées dans un meme flux
        // le JSON saisie par l'user dans le body sera donc utiliser pour générer une instance de Film
        filmService.saveFilm(film);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable("id") final Long id) {  //void sgnifie qu'il n'y a aucun objet dans le body
        Optional<Film> optFilm = filmService.getFilm(id);  //Optional -> encapsule un objet dont la valeur peut être null

        if (optFilm.isPresent()){
            filmService.deleteFilm(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFilm(@PathVariable("id") final Long id, @RequestBody Film film) { //film contenu dans le body
        Optional<Film> optFilm = filmService.getFilm(id);  //Optional -> encapsule un objet dont la valeur peut être null

        if (optFilm.isPresent()) {
            Film currentFilm = optFilm.get();

            //recupere les variables du film fourni en parametre pour les manipuler
            String title = film.getTitle();
            String date = film.getDateReleased();
            boolean watched = film.isWatched();

            if (title != null) {
                currentFilm.setTitle(title);
            }
            if (date != null) {
                currentFilm.setDateReleased(date);
            }
            if (watched != film.isWatched()) {
                currentFilm.setWatched(watched);
            }
            filmService.saveFilm(currentFilm);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}