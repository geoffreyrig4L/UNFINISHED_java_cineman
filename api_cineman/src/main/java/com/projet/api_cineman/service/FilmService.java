package com.projet.api_cineman.service;

import com.projet.api_cineman.model.Film;
import com.projet.api_cineman.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FilmService {

    @Autowired //injection automatique des données
    private FilmRepository filmRepository;

    public Optional<Film> getFilm(final Long id) {           //Optional -> encapsule un objet dont la valeur peut être null
        return filmRepository.findById(id);
    }

    public Page<Film> getAllFilms (Optional<Integer> page, Optional<String> sortBy) {
        return filmRepository.findAll(
                PageRequest.of( //Pour créer la page
                        page.orElse(0), //si page est null = on commence à la page 0
                        3,  //taille de la page
                        Sort.Direction.ASC, sortBy.orElse("id") //trier par ordre croissant, avec le param sortBy si il est null = on trie par id
                )
        );
    }

    public void deleteFilm(final Long id) {
        filmRepository.deleteById(id);
    }

    public Film saveFilm(Film f) {           //creer une instance de la table et genere automatiquement l'id
        Film savedFilm = filmRepository.save(f);
        return savedFilm;
    }
}
