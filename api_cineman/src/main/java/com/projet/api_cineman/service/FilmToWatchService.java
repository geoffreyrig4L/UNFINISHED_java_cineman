package com.projet.api_cineman.service;

import com.projet.api_cineman.model.FilmToWatch;
import com.projet.api_cineman.repository.FilmToWatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FilmToWatchService {

    @Autowired //injection automatique des données
    private FilmToWatchRepository filmRepository;

    public Optional<FilmToWatch> getAllFilmsToWatch(final Long id) {           //Optional -> encapsule un objet dont la valeur peut être null
        return filmRepository.findById(id);
    }

    public List<FilmToWatch> getAllFilmsToWatch() {
        List<FilmToWatch> listResult = new ArrayList<>();
        Iterable<FilmToWatch> films = filmRepository.findAll();
        films.forEach(listResult::add);
        return listResult;
    }

    public void deleteFilmToWatch(final Long id) {
        filmRepository.deleteById(id);
    }

    public FilmToWatch saveFilmToWatch(FilmToWatch f) {           //creer une instance de la table et genere automatiquement l'id
        FilmToWatch savedFilmToWatch = filmRepository.save(f);
        return savedFilmToWatch;

    }

}
