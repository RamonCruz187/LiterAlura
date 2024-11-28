package com.gutendex.alura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record ListaDeLibros(
        @JsonAlias("results") List<LibroConsultado> libros
) {
}
