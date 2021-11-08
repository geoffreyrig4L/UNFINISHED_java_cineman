package com.projet.api_cineman.controller;

import com.projet.api_cineman.model.FilmToWatch;
import com.projet.api_cineman.service.FilmToWatchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/films-to-watch")
public class FilmToWatchController {

    private FilmToWatchService filmToWatchService;

    public FilmToWatchController(FilmToWatchService filmToWatchService) {
        this.filmToWatchService = filmToWatchService;
    }

    @GetMapping("?{page}&{sortBy}")
    public ResponseEntity<Page<FilmToWatch>> getAllFilmsToWatch(@PathVariable("page") final Optional<Integer> page, @PathVariable("sortBy") final Optional<String> sortBy) {
        Page<FilmToWatch> filmList = filmToWatchService.getAllFilmsToWatch(page, sortBy);
        return ResponseEntity.ok(filmList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmToWatch> getFilmToWatch(@PathVariable("id") final Long id) {     //PathVariable -> permet de manipuler des variables dans l'URI de la requete mapping
        Optional<FilmToWatch> film = filmToWatchService.getAllFilmsToWatch(id); //Optional -> encapsule un objet dont la valeur peut être null
        if (film.isPresent()) {   //si il existe dans la bdd
            return ResponseEntity.ok(film.get());  //recupere la valeur de film
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> createFilmToWatch(@RequestBody FilmToWatch film) {         // deserialise les JSON dans un langage Java -> regroupe des données séparées dans un meme flux
        // le JSON saisie par l'user dans le body sera donc utiliser pour générer une instance de FilmToWatch
        filmToWatchService.saveFilmToWatch(film);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilmToWatch(@PathVariable("id") final Long id) {  //void sgnifie qu'il n'y a aucun objet dans le body
        filmToWatchService.deleteFilmToWatch(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFilmToWatch(@PathVariable("id") final Long id, @RequestBody FilmToWatch film) { //film contenu dans le body
        Optional<FilmToWatch> optFilmToWatch = filmToWatchService.getAllFilmsToWatch(id);  //Optional -> encapsule un objet dont la valeur peut être null

        if (optFilmToWatch.isPresent()) {
            FilmToWatch currentFilmToWatch = optFilmToWatch.get();

            //recupere les variables du film fourni en parametre pour les manipuler
            String title = film.getTitle();
            String date = film.getDateReleased();

            if (title != null) {
                currentFilmToWatch.setTitle(title);
            }
            if (date != null) {
                currentFilmToWatch.setDateReleased(date);
            }
            filmToWatchService.saveFilmToWatch(currentFilmToWatch);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}