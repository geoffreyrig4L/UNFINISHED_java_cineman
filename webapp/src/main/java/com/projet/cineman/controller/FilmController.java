package com.projet.cineman.controller;

import com.projet.cineman.model.Film;
import com.projet.cineman.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

//le controller retourne une page HTML

@Controller //specifie que cette classe fait partie de la config de SringBoot et devient un bean
public class FilmController {

    @Autowired
    private FilmService service;

    //fourni des données à la vue
    @GetMapping("/")
    public String home(Model model){    //Srping fourni une instance de l'objet model automatiquement
        Iterable<Film> listFilm = service.getAllFilms();
        model.addAttribute("films",listFilm);   //ajoute a mon model un objet : (arg1) nom , (arg2) objet
        return "home";
    }

    @GetMapping("/createFilm")
    public String createFilm(Model model) {
        Film film = new Film();
        model.addAttribute("film", film);
        return "formNewFilm";
    }

    @GetMapping("/updateFilm/{id}")
    public String updateFilm(@PathVariable("id") final int id, Model model) {
        Film film = service.getFilm(id);
        model.addAttribute("film", film);
        return "formUpdateFilm";
    }

    @PostMapping("/saveFilm")
    public ModelAndView saveFilm(@ModelAttribute Film film) {
        if(film.getId() != null) {

            Film current = service.getFilm(film.getId());
            film.setTitle(current.getTitle());
            film.setDateReleased(current.getDateReleased());
        }
        service.saveFilm(film);
        return new ModelAndView("redirect:/");
    }

    //donnée transmise par formulaire

    @GetMapping("/deleteFilm/{id}")
    public ModelAndView deleteFilm(@PathVariable("id") final int id) {
        service.deleteFilm(id);
        return new ModelAndView("redirect:/");
    }

//    @PostMapping("/saveFilm")
//    public ModelAndView saveFilm(@ModelAttribute Film f) {
//        service.saveFilm(f);
//        return new ModelAndView("redirect:/");
//    }
//
//    @PostMapping("/createFilm")
//    public ModelAndView createFilm(@ModelAttribute Film f){
//        service.createFilm(f);
//        return new ModelAndView("redirect:/");
//    }
}
