package com.projet.api_cineman;

import com.projet.api_cineman.controller.FilmToWatchController;
import com.projet.api_cineman.model.FilmToWatch;
import com.projet.api_cineman.repository.FilmToWatchRepository;
import com.projet.api_cineman.service.FilmToWatchService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmToWatchControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @TestConfiguration
    static class FilmToWatchControllerTestConfig{

        @Bean
        @Primary        //creer un autre repo qui est prioritaire sur le VRAI repo, les tests seront exeutés sur ce dernier
                    //l'objectif etant de teste sans modifier notre bdd
        public FilmToWatchRepository filmToWatchRepositoryTest(){
            FilmToWatchRepository filmToWatchRepository = mock(FilmToWatchRepository.class); //simuler le comportement d'un objet , ici le film to watch , il ne sera pas instancie mais mocké
            //film 1
            FilmToWatch filmToWatch = new FilmToWatch();
            filmToWatch.setId(1L);
            filmToWatch.setTitle("Harry Potter");
            filmToWatch.setDate_released("2001");
            when(filmToWatchRepository.findById(1L)).thenReturn(Optional.of(filmToWatch));
            //film 2
            FilmToWatch filmToWatch2 = new FilmToWatch();
            filmToWatch2.setId(2L);
            filmToWatch2.setTitle("Harry Potter 2");
            filmToWatch2.setDate_released("2002");
            when(filmToWatchRepository.findById(2L)).thenReturn(Optional.of(filmToWatch2));
            //liste films
            List<FilmToWatch> listFilms = List.of(filmToWatch, filmToWatch2);
            Page<FilmToWatch> page = new PageImpl<>(listFilms, null,3);         //instanciation page
            when(filmToWatchRepository.findAll(page)).thenReturn(listFilms);
            return filmToWatchRepository;
        }
    }

    @Test
    void should_get_all_films_to_watch() throws Exception{
        mockMvc.perform(get("/films-to-watch?page=0&sortBy=title"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title",is("Harry Potter")));
    }

    @Test
    void should_get_one_film_to_watch() throws Exception{
        mockMvc.perform(get("/films-to-watch/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title",is("Harry Potter")));
    }

    @Test
    void should_not_get_one_film_to_watch() throws Exception{
        mockMvc.perform(get("/films-to-watch/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_put_one_film_to_watch() throws Exception{
        mockMvc.perform(put("/films-to-watch/1")
                        .content("{\"id\":1,\"title\":\"Harry Potter\",\"date_released\":\"3000\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_put_one_film_to_watch() throws Exception{
        mockMvc.perform(put("/films-to-watch/2")
                        .content("{\"id\":1,\"title\":\"Harry Potter\",\"date_released\":\"3000\"}")
                        .contentType(MediaType.APPLICATION_JSON))       //specifie le type de contenu =json
                .andExpect(status().isBadRequest());        //badRequest comme dans la methode update de filmtowatchcontroller
    }

    //a revoir

    @Test
    void should_delete_one_film_to_watch() throws Exception{
        mockMvc.perform(delete("/films-to-watch/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_one_film_to_watch() throws Exception{
        mockMvc.perform(delete("/films-to-watch/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
