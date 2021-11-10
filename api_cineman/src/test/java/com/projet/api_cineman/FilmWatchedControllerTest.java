package com.projet.api_cineman;

import com.projet.api_cineman.model.FilmToWatch;
import com.projet.api_cineman.model.FilmWatched;
import com.projet.api_cineman.repository.FilmToWatchRepository;
import com.projet.api_cineman.repository.FilmWatchedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Array;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmWatchedControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public FilmWatchedRepository filmWatchedRepository;

    @BeforeEach   // s execute avant chaque methode de test
    void insertInH2(){
        FilmWatched film = new FilmWatched();
        film.setTitle("Harry Potter");
        film.setDate_released("2001");
        film.setId(1L);
        filmWatchedRepository.save(film);
    }

       /*         AUTRE METHODE PERMETTANT DE TESTER DANS UN MOCK = simulateur
       


           @TestConfiguration
    static class FilmWatchedControllerTestConfig{

        @Bean
        @Primary
        //creer un autre repo qui est prioritaire sur le VRAI repo, les tests seront exeutés sur ce dernier
        //l'objectif etant de teste sans modifier notre bdd
        public FilmWatchedRepository filmWatchedRepositoryTest(){
            FilmWatchedRepository filmWatchedRepository = mock(FilmWatchedRepository.class); //simuler le comportement d'un objet , ici le film to watch , il ne sera pas instancie mais mocké
            //film 1
            FilmWatched filmWatched = new FilmWatched();
            filmWatched.setId(1L);
            filmWatched.setTitle("Harry Potter");
            filmWatched.setDate_released("2001");
            when(filmWatchedRepository.findById(1L)).thenReturn(Optional.of(filmWatched));
            //film 2
            FilmWatched filmWatched2 = new FilmWatched();
            filmWatched2.setId(2L);
            filmWatched2.setTitle("Harry Potter 2");
            filmWatched2.setDate_released("2002");
            when(filmWatchedRepository.findById(2L)).thenReturn(Optional.of(filmWatched2));
            /*
            //liste films
            List<FilmWatched> listFilms = List.of(filmWatched, filmWatched2);
            when(filmWatchedRepository.findAll()).thenReturn(listFilms);
            return filmWatchedRepository;
        }
    }*/

    /*
    @Test
    void should_get_all_films_watched() throws Exception{
        mockMvc.perform(get("/films-watched"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title",is("Harry Potter")));

        //"{\"id\":1,\"title\":\"Harry Potter\",\"date_released\":\"2001\"}{\"id\":2,\"title\":\"Harry Potter 2\",\"date_released\":\"2002\"}"
    } */

    @Test
    void should_get_one_film_watched() throws Exception{
        mockMvc.perform(get("/films-watched/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title",is("Harry Potter")));
    }

    @Test
    void should_not_get_one_film_watched() throws Exception{
        mockMvc.perform(get("/films-watched/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_put_one_film_watched() throws Exception{
        mockMvc.perform(put("/films-watched/1")
                        .content("{\"id\":1,\"title\":\"Harry Potter\",\"date_released\":\"3000\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_put_one_film_watched() throws Exception{
        mockMvc.perform(put("/films-watched/3")
                        .content("{\"id\":1,\"title\":\"Harry Potter\",\"date_released\":\"3000\"}")
                        .contentType(MediaType.APPLICATION_JSON))       //specifie le type de contenu =json
                .andExpect(status().isBadRequest());        //badRequest comme dans la methode update de filmtowatchcontroller
    }

    //a revoir

    @Test
    void should_delete_one_film_watched() throws Exception{
        mockMvc.perform(delete("/films-watched/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_one_film_watched() throws Exception{
        mockMvc.perform(delete("/films-watched/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
