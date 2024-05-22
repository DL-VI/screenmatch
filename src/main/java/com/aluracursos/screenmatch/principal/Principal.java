package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporada;
import com.aluracursos.screenmatch.service.ConsumoApi;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private static final String URL_BASE = "https://www.omdbapi.com/?t=";
    private static final String API_KEY = "&apikey=22d0b088";
    private ConvierteDatos convierteDatos = new ConvierteDatos();


    public void mostrarMenu() {
        System.out.print("Nombre de la Serie: ");
        //busca los datos generales de las series
        var nombreSerie = scanner.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        var datos = convierteDatos.obtenerDatos(json, DatosSerie.class);
        //System.out.println("\n" + datos);

        //busca los datos de todas las temporadas
        List<DatosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
            json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + "&Season="+i+ API_KEY);
            temporadas.add(convierteDatos.obtenerDatos(json, DatosTemporada.class));
        }
        temporadas.forEach(System.out::println);

        //mostrar solo el titulo de los episodios para la temporada con funcion lambda
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }
    
}



