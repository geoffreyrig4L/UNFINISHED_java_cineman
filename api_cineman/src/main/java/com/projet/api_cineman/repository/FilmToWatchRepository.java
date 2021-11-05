package com.projet.api_cineman.repository;

import com.projet.api_cineman.model.FilmToWatch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmToWatchRepository extends CrudRepository <FilmToWatch,Long> {
        /*CrudRepository fournit des méthodes automatiquement pour manipuler une entité comme
            | findById(id)
            | findAll()
            | deleteById(id)
            | save()
     */
}
