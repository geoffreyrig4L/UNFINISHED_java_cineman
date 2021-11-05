package com.projet.cineman;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data //getter et setter
@Configuration  //specifie que la classe est un bean, elle est utilise pour la config de l'app
@ConfigurationProperties(prefix="com.projet.cineman")   //mettre le code en parallele avec application.properties //scannera les proprietes commençant par com.projet.cineman
public class CustomProperties {

    private String apiUrl;  //correspond à apiUrl dans application.properties ligne 18
}
