package com.aluracursos.screenmatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {//clase que se encargara de consumir la Api de peliculas

    public String obtenerDatos(String url) {
        HttpClient client = HttpClient.newHttpClient();//se crea un cliente que se utilizara para enviar y recibir las solicitudes
        HttpRequest request = HttpRequest.newBuilder()//crea una solicitud utilizando la url
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;//variable de tipo generico para respresentar una respuesta http
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());//se envia la solicitud a traves del cliente
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return response.body();//retorna el cuerpo de la respuesta http como una cadena
    }
}
