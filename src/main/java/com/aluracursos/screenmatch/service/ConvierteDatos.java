package com.aluracursos.screenmatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos {
    /*
    Es una clase proporcionada por la biblioteca Jackson (jackson-databind) que se utiliza para leer y escribir datos en formato JSON.
    Puede convertir objetos Java en cadenas JSON (serialización) y viceversa (deserialización)
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);//deserializa el json y lo convierte en un objeto del parametro 'clase'
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
