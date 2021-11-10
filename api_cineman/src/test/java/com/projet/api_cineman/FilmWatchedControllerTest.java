package com.projet.api_cineman;

import com.projet.api_cineman.model.FilmWatched;
import com.projet.api_cineman.repository.FilmWatchedRepository;
import org.junit.jupiter.api.AfterEach;
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

@SpringBootTest
@AutoConfigureMockMvc
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //force le contexte spring boot a etre recharge apres ce test
//permet ainsi de ne pas creer de conflit apres une suppression   ELLE EST PRESENTE DANS LE H2TestJpaConfig DONT CETTE CLASSE HERITE
public class FilmWatchedControllerTest implements H2TestJpaConfig{

    //pour que les tests fonctionnent : il faut que le getAll verifie les titres de tous sauf du dernier et que le delete supprime le dernier

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public FilmWatchedRepository filmWatchedRepository;

           /*AUTRE METHODE PERMETTANT DE TESTER DANS UN MOCK = simulateur



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

    @BeforeEach   // s execute avant chaque methode de test
    void insertInH2(){
        FilmWatched film = new FilmWatched();
        film.setTitle("Harry Potter");
        film.setDate_released("2001");
        filmWatchedRepository.save(film);
        FilmWatched film2 = new FilmWatched();
        film2.setTitle("Harry Potter 2");
        film2.setDate_released("2002");
        filmWatchedRepository.save(film2);
        FilmWatched film10 = new FilmWatched();
        film10.setTitle("Harry Potter 10");
        film10.setDate_released("2010");
        filmWatchedRepository.save(film10);
    }

    @Test
    void should_get_all_films_watched() throws Exception{
        mockMvc.perform(get("/films-watched?page=0&sortBy=id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title",is("Harry Potter")))
                .andExpect(jsonPath("$.content[1].title",is("Harry Potter 2")))
                .andExpect(jsonPath("$.content[2].title",is("Harry Potter 10")));
    }

    @Test
    void should_get_one_film_watched() throws Exception{
        mockMvc.perform(get("/films-watched/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title",is("Harry Potter")));
    }

    @Test
    void should_not_get_one_film_watched() throws Exception{
        mockMvc.perform(get("/films-watched/50"))
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
        mockMvc.perform(put("/films-watched/50")
                        .content("{\"id\":1,\"title\":\"Harry Potter\",\"date_released\":\"3000\"}")
                        .contentType(MediaType.APPLICATION_JSON))       //specifie le type de contenu =json
                .andExpect(status().isBadRequest());        //badRequest comme dans la methode update de filmtowatchcontroller
    }

    @Test
    void should_delete_one_film_watched() throws Exception{
        mockMvc.perform(delete("/films-watched/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_one_film_watched() throws Exception{
        mockMvc.perform(delete("/films-watched/50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
