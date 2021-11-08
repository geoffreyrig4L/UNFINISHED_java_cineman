package com.projet.api_cineman.service;

import com.projet.api_cineman.model.FilmToWatch;
import com.projet.api_cineman.repository.FilmToWatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
public class FilmToWatchService {

    @Autowired //injection automatique des données
    private FilmToWatchRepository filmRepository;

    public Optional<FilmToWatch> getAllFilmsToWatch(final Long id) {           //Optional -> encapsule un objet dont la valeur peut être null
        return filmRepository.findById(id);
    }

    @GetMapping
    public Page<FilmToWatch> getAllFilmsToWatch (@RequestParam Optional<Integer> page, @RequestParam Optional<String> sortBy) {
        return filmRepository.findAll(
                PageRequest.of( //Pour créer la page
                        page.orElse(0), //si page est null = on commence à la page 0
                        3,  //taille de la page
                        Sort.Direction.ASC, sortBy.orElse("id") //trier par ordre croissant, avec le param sortBy si il est null = on trie par id
                )
        );
    }

    public void deleteFilmToWatch(final Long id) {
        filmRepository.deleteById(id);
    }

    public FilmToWatch saveFilmToWatch(FilmToWatch f) {           //creer une instance de la table et genere automatiquement l'id
        FilmToWatch savedFilmToWatch = filmRepository.save(f);
        return savedFilmToWatch;

    }

}
