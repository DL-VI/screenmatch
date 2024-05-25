package com.aluracursos.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)//ignora los atributos del Json que no implementamos en el record
public record DatosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("Genre") String genero,
                         @JsonAlias("Plot") String sinopsis,
                         @JsonAlias("Poster") String poster,
                         @JsonAlias("Actors") String actores,
                         @JsonAlias("totalSeasons") Integer totalDeTemporadas,
                         //@JsonAlias identifica el atributo del Json y lo guardar en la variable que declaramos
                         @JsonAlias("imdbRating") String evaluacion){
    /*
    es una clase de datos inmutable en Java que utiliza anotaciones de Jackson
     para mapear propiedades de un objeto JSON a campos específicos.
     */
}
