package com.projet.api_cineman;

import com.projet.api_cineman.controller.FilmToWatchController;
import com.projet.api_cineman.model.FilmToWatch;
import com.projet.api_cineman.repository.FilmToWatchRepository;
import com.projet.api_cineman.service.FilmToWatchService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Array;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(H2TestJpaConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FilmToWatchControllerTest {

    @Autowired
    public MockMvc mockMvc;

/*
    @Test
    void should_get_all_films_to_watch() throws Exception{
        mockMvc.perform(get("/films-to-watch"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title",is("Harry Potter")));

        //"{\"id\":1,\"title\":\"Harry Potter\",\"date_released\":\"2001\"}{\"id\":2,\"title\":\"Harry Potter 2\",\"date_released\":\"2002\"}"
    }*/

    /*@BeforeEach
    void insertInH2(){

    }*/

    @Test
    void should_get_one_film_to_watch() throws Exception{
        mockMvc.perform(get("/films-to-watch/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title",is("Harry Potter")));
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
