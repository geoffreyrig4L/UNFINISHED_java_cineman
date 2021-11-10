package com.projet.api_cineman;

import com.projet.api_cineman.model.FilmToWatch;
import com.projet.api_cineman.repository.FilmToWatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(H2TestJpaConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FilmToWatchControllerTest {

    //pour que les tests fonctionnent : il faut que le getAll verifie les titres de tous sauf du dernier et que le delete supprime le dernier

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public FilmToWatchRepository filmToWatchRepository;

    @BeforeEach  // s execute avant chaque methode de test
    void insertInH2(){
        //les id sont generes automatiquements meme si on les modifies avec @GeneratedValue
        FilmToWatch film = new FilmToWatch();
        film.setTitle("Harry Potter");
        film.setDate_released("2001");
        filmToWatchRepository.save(film);
        FilmToWatch film2 = new FilmToWatch();
        film2.setTitle("Harry Potter 2");
        film2.setDate_released("2002");
        filmToWatchRepository.save(film2);
        FilmToWatch film10 = new FilmToWatch();
        film10.setTitle("Harry Potter 10");
        film10.setDate_released("2010");
        filmToWatchRepository.save(film10);

    }

    @Test
    void should_get_all_films_to_watch() throws Exception{
        mockMvc.perform(get("/films-to-watch?page=0&sortBy=id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title",is("Harry Potter")))
                .andExpect(jsonPath("$.content[1].title",is("Harry Potter 2")));
    }

    @Test
    void should_get_one_film_to_watch() throws Exception{
        mockMvc.perform(get("/films-to-watch/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title",is("Harry Potter")));
    }

    @Test
    void should_not_get_one_film_to_watch() throws Exception{
        mockMvc.perform(get("/films-to-watch/50"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_put_one_film_to_watch() throws Exception{
        mockMvc.perform(put("/films-to-watch/2")
                        .content("{\"id\":2,\"title\":\"Harry Potter 2\",\"date_released\":\"3000\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_put_one_film_to_watch() throws Exception{
        mockMvc.perform(put("/films-to-watch/50")
                        .content("{\"id\":1,\"title\":\"Harry Potter\",\"date_released\":\"3000\"}")
                        .contentType(MediaType.APPLICATION_JSON))       //specifie le type de contenu =json
                .andExpect(status().isBadRequest());        //badRequest comme dans la methode update de filmtowatchcontroller
    }

    @Test
    void should_delete_one_film_to_watch() throws Exception{
        mockMvc.perform(delete("/films-to-watch/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_one_film_to_watch() throws Exception{
        mockMvc.perform(delete("/films-to-watch/50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    //le delete passe avant du coup ça generait une erreur
}
