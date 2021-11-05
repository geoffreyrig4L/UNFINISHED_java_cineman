package com.projet.api_cineman.repository;

import com.projet.api_cineman.model.FilmWatched;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmWatchedRepository extends CrudRepository<FilmWatched,Long> {
        /*CrudRepository fournit des méthodes automatiquement pour manipuler une entité comme
            | findById(id)
            | findAll()
            | deleteById(id)
            | save()
     */
}
