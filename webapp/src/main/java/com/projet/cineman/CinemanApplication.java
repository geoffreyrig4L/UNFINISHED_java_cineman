package com.projet.cineman;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//si ça ne marche pas = supprimer l'implementation commandLinerunner avec la methode et l attribut

@SpringBootApplication
public class CinemanApplication implements CommandLineRunner {

	@Autowired
	private CustomProperties props;

	public static void main(String[] args) {
		SpringApplication.run(CinemanApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(props.getApiUrl());			//permet d'afficher l'url dans la console
	}
}
