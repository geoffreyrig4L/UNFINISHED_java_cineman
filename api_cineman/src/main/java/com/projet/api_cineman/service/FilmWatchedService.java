package com.projet.api_cineman.service;

import com.projet.api_cineman.model.FilmWatched;
import com.projet.api_cineman.repository.FilmWatchedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FilmWatchedService {

    @Autowired //injection automatique des données
    private FilmWatchedRepository filmRepository;

    public Optional<FilmWatched> getFilmWatched(final Long id) {           //Optional -> encapsule un objet dont la valeur peut être null
        return filmRepository.findById(id);
    }

    public List<FilmWatched> getAllFilmsWatched() {
        List<FilmWatched> listResult = new ArrayList<>();
        Iterable<FilmWatched> films = filmRepository.findAll();
        films.forEach(listResult::add);
        return listResult;
    }

    public void deleteFilmWatched(final Long id) {
        filmRepository.deleteById(id);
    }

    public FilmWatched saveFilmWatched(FilmWatched f) {           //creer une instance de la table et genere automatiquement l'id
        FilmWatched savedFilmWatched = filmRepository.save(f);
        return savedFilmWatched;

    }
}
