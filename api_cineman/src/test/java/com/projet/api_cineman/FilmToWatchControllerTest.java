package com.projet.api_cineman;

import com.projet.api_cineman.model.FilmToWatch;
import com.projet.api_cineman.repository.FilmToWatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(H2TestJpaConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FilmToWatchControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public FilmToWatchRepository filmToWatchRepository;

    @BeforeEach  // s execute avant chaque methode de test
    void insertInH2(){
        FilmToWatch film = new FilmToWatch();
        film.setTitle("Harry Potter");
        film.setDate_released("2001");
        film.setId(1L);
        filmToWatchRepository.save(film);
        FilmToWatch film2 = new FilmToWatch();
        film2.setTitle("Harry Potter 2");
        film2.setDate_released("2002");
        film2.setId(2L);
        filmToWatchRepository.save(film2);

    }

    @Test
    void should_get_all_films_to_watch() throws Exception{
        mockMvc.perform(get("/films-to-watch"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title",is("Harry Potter")));
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
        mockMvc.perform(delete("/films-to-watch/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_one_film_to_watch() throws Exception{
        mockMvc.perform(delete("/films-to-watch/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
