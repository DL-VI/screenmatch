package com.aluracursos.screenmatch;

import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.service.ConsumoApi;
import com.aluracursos.screenmatch.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		String json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&apikey=22d0b088");
		System.out.println(json);

		ConvierteDatos convierteDatos = new ConvierteDatos();
		var datos = convierteDatos.obtenerDatos(json, DatosSerie.class);
		System.out.println(datos);
	}
}
