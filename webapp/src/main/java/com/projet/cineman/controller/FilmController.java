package com.projet.cineman.controller;

import com.projet.cineman.model.Film;
import com.projet.cineman.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

//le controller retourne une page HTML

@Controller //specifie que cette classe fait partie de la config de SringBoot et devient un bean
public class FilmController {

    @Autowired
    private FilmService service;

    //fourni des données à la vue
    @GetMapping("/")
    public String home(Model model){    //Srping fourni une instance de l'objet model automatiquement
        Iterable<Film> listFilm = service.getFilms();
        model.addAttribute("films",listFilm);   //ajoute a mon model un objet : (arg1) nom , (arg2) objet
        return "index";
    }
}
