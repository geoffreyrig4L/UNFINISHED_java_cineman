package com.projet.api_cineman;

import com.projet.api_cineman.controller.FilmToWatchController;
import com.projet.api_cineman.model.FilmToWatch;
import com.projet.api_cineman.repository.FilmToWatchRepository;
import com.projet.api_cineman.service.FilmToWatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmToWatchTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private FilmToWatchRepository filmToWatchRepository;

    @Test
    void test_get_all_films_to_watch() throws Exception{
        mockMvc.perform(get("/films-to-watch"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title",is("Le loup de Wall Street")));    //$ designe la racine
                                                                                                    //[0] designe l'élément de la liste
                                                                                                    //title designe l'attribut
    }

    @Test
    void test_get_one_film_to_watch() throws Exception{
        mockMvc.perform(get("/films-to-watch/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title",is("Le loup de Wall Street")));
    }

    @Test
    void test_create_one_film_to_watch(){
        FilmToWatch film = new FilmToWatch();
        film.setDate_released("2011");
        film.setId(10L);
        film.setTitle("Melancholia");
        filmToWatchRepository.save(film);
        assertNotNull(filmToWatchRepository.findById(10L).get());
    }


    //pense a inserer un Id existant
    @Test
    void test_update_film_to_watch(){
        FilmToWatch filmToWatch = filmToWatchRepository.findById(3L).get();
        filmToWatch.setTitle("update");
        filmToWatch.setDate_released("3000");
        filmToWatchRepository.save(filmToWatch);
        assertEquals("update",filmToWatch.getTitle());
    }

    @Test
    void test_delete_film_to_watch(){
        filmToWatchRepository.deleteById(1L);
        assertThat(filmToWatchRepository.existsById(1L)).isFalse();
    }
}
