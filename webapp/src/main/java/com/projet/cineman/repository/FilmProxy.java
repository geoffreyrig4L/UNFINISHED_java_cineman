package com.projet.cineman.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.projet.cineman.CustomProperties;
import com.projet.cineman.model.Film;

import lombok.extern.slf4j.Slf4j;

//Cette classe permet de d'exécuter une requête HTTP
//il faut fournir le type de requête, le type d'objet qui sera retourné et l'URL

@Slf4j
@Component
public class FilmProxy {

    @Autowired
    private CustomProperties props;//récupère l'url

    public Film getFilm(int id) {
        String baseApiUrl = props.getApiUrl();
        String getFilmsUrl = baseApiUrl + "/films/" + id;

        RestTemplate restTemplate = new RestTemplate();     //accede à l'API fourni dans l'URL
        ResponseEntity<Film> response = restTemplate.exchange(    //fournit :
                getFilmsUrl, //l'url
                HttpMethod.GET, // le type de requête
                null, //défini un comportement par défaut
                new ParameterizedTypeReference<Film>(){} //le type d'objet retournée
        );

        log.debug("Get Films call " + response.getStatusCode().toString()); //affiche des message dans la console

        return response.getBody();  //on récupère notre objet Iterable<Film> grâce à la méthode getBody() de l’objet Response.
    }

    /**
     * Get all films
     * @return An iterable of all films
     */

    public Iterable<Film> getAllFilms() {      //complete l'URL de l'API avec le bon endpoint
        String baseApiUrl = props.getApiUrl();
        System.out.println("coucou : " +baseApiUrl);
        String getFilmsUrl = baseApiUrl + "/films";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Film>> response = restTemplate.exchange(    //fournit :
                getFilmsUrl, //l'url
                HttpMethod.GET, // le type de requête
                null, //défini un comportement par défaut
                new ParameterizedTypeReference<Iterable<Film>>() {} //le type d'objet retournée
        );

        log.debug("Get Films call " + response.getStatusCode().toString()); //affiche des message dans la console

        return response.getBody();  //on récupère notre objet Iterable<Film> grâce à la méthode getBody() de l’objet Response.
    }

    public Film createFilm(Film f){
        String baseApiUrl = props.getApiUrl();
        String createFilmUrl = baseApiUrl + "/film";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Film> request = new HttpEntity<Film>(f);     //represent une requete HTTP avec un header et un body , sera transmis dans le requestEntity
        ResponseEntity<Film> response = restTemplate.exchange(
                createFilmUrl,
                HttpMethod.POST,
                request,    //defini un comportement d'une requete HTTP
                Film.class);
        log.debug("Create Film call " +response.getStatusCode().toString());    //affiche des message dans la console

        return response.getBody();
    }

    public void deleteFilm(int id){
        String baseApiUrl = props.getApiUrl();
        String deleteFilmUrl = baseApiUrl + "/film/" +id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Void> response = restTemplate.exchange(
                deleteFilmUrl,
                HttpMethod.DELETE,
                null,
                Void.class);

        log.debug("Delete Film call "+response.getStatusCode().toString());
    }

    public Film updateFilm(Film f){
        String baseApiUrl = props.getApiUrl();
        String updateFilmUrl =  baseApiUrl +"/film/"+ f.getId();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Film> request = new HttpEntity<Film>(f);
        ResponseEntity<Film> response = restTemplate.exchange(
                updateFilmUrl,
                HttpMethod.PUT,
                request,
                Film.class);

        log.debug("Update Film call " +response.getStatusCode().toString());
        return response.getBody();
    }
}