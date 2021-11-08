package com.projet.api_cineman;

import com.projet.api_cineman.model.FilmWatched;
import com.projet.api_cineman.repository.FilmWatchedRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmWatchedTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private FilmWatchedRepository filmWatchedRepository;

    @Test
    public void test_get_all_films_watched() throws Exception{
        mockMvc.perform(get("/films-watched"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.films-watched.title",is("Zodiac")));
    }

    //pense a inserer un Id existant
    @Test
    void test_update_film_to_watch(){
        FilmWatched filmToWatch = filmWatchedRepository.findById(3L).get();
        filmToWatch.setTitle("update");
        filmToWatch.setDate_released("3000");
        filmWatchedRepository.save(filmToWatch);
        assertEquals("update",filmToWatch.getTitle());
    }

    @Test
    void test_delete_film_to_watch(){
        filmWatchedRepository.deleteById(1L);
        assertThat(filmWatchedRepository.existsById(1L)).isFalse();
    }
}
