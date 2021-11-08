package com.projet.api_cineman.repository;

import com.projet.api_cineman.model.FilmToWatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface FilmToWatchRepository extends PagingAndSortingRepository<FilmToWatch,Long> {
        /*CrudRepository fournit des méthodes automatiquement pour manipuler une entité comme
            | findById(id)
            | findAll()
            | deleteById(id)
            | save()


    List<FilmToWatch> findAllByDateReleased(String date_released, Pageable pageable);

    Pageable firstPage = PageRequest.of(0,3);
    Pageable secondPage = PageRequest.of(1,3);

    */
}
