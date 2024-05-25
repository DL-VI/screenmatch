package com.aluracursos.screenmatch.model;

import java.util.OptionalDouble;

public class Serie {
    private String titulo;
    private Categoria genero;
    private String sinopsis;
    private String poster;
    private String actores;
    private Integer totalDeTemporadas;
    private Double evaluacion;


    public Serie(DatosSerie ds) {
        this.titulo = ds.titulo();
        this.genero = Categoria.fromString(ds.genero().split(",")[0].trim());
        this.sinopsis = ds.sinopsis();
        this.poster = ds.poster();
        this.actores = ds.actores();
        this.totalDeTemporadas = ds.totalDeTemporadas();
        this.evaluacion = OptionalDouble.of(Double.valueOf(ds.evaluacion())).orElse(0);

    }

    public Categoria getGenero() {
        return genero;
    }

    @Override
    public String toString() {
        return "\nGenero: " + genero +
                "\ntitulo: " + titulo +
                "\nsinopsis: " + sinopsis +
                "\nposter: " + poster +
                "\nactores: " + actores +
                "\ntotalDeTemporadas: " + totalDeTemporadas +
                "\nevaluacion: " + evaluacion;
    }
}
