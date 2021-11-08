package com.projet.api_cineman.controller;

import com.projet.api_cineman.model.FilmWatched;
import com.projet.api_cineman.service.FilmWatchedService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/films-watched")
public class FilmWatchedController {

    private final FilmWatchedService filmWatchedService;

    public FilmWatchedController(FilmWatchedService filmWatchedService) {
        this.filmWatchedService = filmWatchedService;
    }

    @GetMapping
    public ResponseEntity<Page<FilmWatched>> getAllFilmsToWatch(@RequestParam("page") final Optional<Integer> page, @RequestParam("sortBy") final Optional<String> sortBy) {
        Page<FilmWatched> filmList = filmWatchedService.getAllFilmsWatched(page, sortBy);
        return ResponseEntity.ok(filmList);
    }

    /*
        @RequestParam recupere des infos concernant les ressources, tout ce qu'on peut trouver apres le ?, ses infos servent principalement de filtrage
        @PathVariable récupère la ressource directement soit les champs contenu dans notre bdd (id, title, date_released)
     */

    @GetMapping("/{id}")
    public ResponseEntity<FilmWatched> getFilmWatched(@PathVariable("id") final Long id) {     //PathVariable -> permet de manipuler des variables dans l'URI de la requete mapping
        Optional<FilmWatched> film = filmWatchedService.getFilmWatched(id); //Optional -> encapsule un objet dont la valeur peut être null
        if (film.isPresent()) {   //si il existe dans la bdd
            return ResponseEntity.ok(film.get());  //recupere la valeur de film
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> createFilmWatched(@RequestBody FilmWatched film) {         // deserialise les JSON dans un langage Java -> regroupe des données séparées dans un meme flux
        // le JSON saisie par l'user dans le body sera donc utiliser pour générer une instance de FilmWatched
        filmWatchedService.saveFilmWatched(film);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilmWatched(@PathVariable("id") final Long id) {  //void sgnifie qu'il n'y a rien dans le body
        filmWatchedService.deleteFilmWatched(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFilmWatched(@PathVariable("id") final Long id, @RequestBody FilmWatched film) {
        Optional<FilmWatched> optFilmWatched = filmWatchedService.getFilmWatched(id);  //Optional -> encapsule un objet dont la valeur peut être null

        if (optFilmWatched.isPresent()) {
            FilmWatched currentFilmWatched = optFilmWatched.get();

            //recupere les variables du film fourni en parametre pour les manipuler
            String title = film.getTitle();
            String date = film.getDateReleased();

            if (title != null) {
                currentFilmWatched.setTitle(title);
            }
            if (date != null) {
                currentFilmWatched.setDateReleased(date);
            }
            filmWatchedService.saveFilmWatched(currentFilmWatched);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
