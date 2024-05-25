package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporada;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.service.ConsumoApi;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
        System.out.println("\n" + datos);
        //busca los datos de todas las temporadas
        List<DatosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
            json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + "&Season=" + i + API_KEY);
            temporadas.add(convierteDatos.obtenerDatos(json, DatosTemporada.class));
        }
        //temporadas.forEach(System.out::println);

        //mostrar solo el titulo de los episodios para la temporada con funcion lambda
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        //convertir toda la informacion a una lista del tipo DatoEpisodio
        List<DatosEpisodio> datosEpisodios = temporadas.stream().flatMap(t -> t.episodios().stream()).collect(Collectors.toList());
        datosEpisodios.forEach(System.out::println);

        System.out.println("Top 5");
        //top 5 episodios
        datosEpisodios.stream().filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))//filtramos que no tengo el N/A
                .peek(e -> System.out.println("Primer fuktro (N/A)" + e))
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())//ordena la lista de menor a mayor de acuerda a la evaluacion
                .peek(e -> System.out.println("Segundo ordenacion (M>m)" + e))
                .map(e -> e.titulo().toUpperCase())
                .peek(e-> System.out.println("Tercer filtro Mayusculas (m>M" + e))
                .limit(5)
                .forEach(System.out::println);//inverte la lista de mayor a menor y se limita a las 5 primeras


        //convirtiendo los datos a una lista de tipo Episodio;
        List<Episodio> episodios = temporadas.stream().flatMap(t -> t.episodios().stream()
                .map(d -> new Episodio(t.numero(), d))).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        //busqueda de los episodios a partir de un año especifico
        System.out.println("\nIndica apartir de que año quieres ver las peliculas:");
        var fecha = scanner.nextInt();
        scanner.nextLine();

        LocalDate fechaBusqueda = LocalDate.of(fecha, 1, 1);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream().filter(e -> e.getFechaLanczamiento() != null && e.getFechaLanczamiento().isAfter(fechaBusqueda))
                .forEach(e-> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                ", Episodio:" + e.getTitulo() +
                                ", Fecha lanzamiento:" + e.getFechaLanczamiento().format(dtf)
                ));

        //buscar y mostrar un episodio por su nombre
        System.out.println("\nIngrese el nombre de la pelicula: ");
        var nombrePelicula = scanner.nextLine();

        Optional<Episodio> optional = episodios.stream().filter(e -> e.getTitulo().toUpperCase().contains(nombrePelicula.toUpperCase())).findFirst();

        if (optional.isPresent())
            System.out.println(optional.get());
        else
            System.out.println("No se encontra la pelicula.");


        Map<Integer, Double> evaluacionTemporadas = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getEvaluacion)));

        System.out.println(evaluacionTemporadas);

        //estadiscticas
        DoubleSummaryStatistics ets = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
        System.out.println(ets);
    }
}