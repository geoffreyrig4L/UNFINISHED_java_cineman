package com.projet.cineman.model;

import lombok.Data;

import java.util.Date;

@Data //génère automatiquement les getters et setters
public class Film {
    private Integer id;
    private String title;
    private String dateReleased;
    private boolean watched;
}
