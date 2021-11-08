package com.projet.api_cineman.repository;

import com.projet.api_cineman.model.FilmToWatch;
import com.projet.api_cineman.model.FilmWatched;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmWatchedRepository extends PagingAndSortingRepository<FilmWatched,Long> {
        /*CrudRepository ou PagingAndSortingRepository fournit des méthodes automatiquement pour manipuler une entité comme
            | findById(id)
            | findAll()
            | deleteById(id)
            | save()

            Paging permet en + de gérer ses index = faire une pagination
    */
}
