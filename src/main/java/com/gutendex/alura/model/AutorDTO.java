package com.gutendex.alura.model;

import java.util.List;
import java.util.stream.Collectors;

public class AutorDTO {

    private String nombre;
    private Integer anioNacimiento;
    private Integer anioFallecimiento;
    private List<String> libros;


    public AutorDTO(Autor autor) {
        this.nombre = autor.getNombre();
        this.anioNacimiento = autor.getAnioNacimiento();
        this.anioFallecimiento = autor.getAnioFallecimiento();
        this.libros = autor.getLibro().stream().map(Libro::getTitulo).toList();
    }

    @Override
    public String toString() {
        return
                "\nAutor: " + nombre +
                "\nFecha de Nacimiento: " + anioNacimiento +
                "\nFecha de Fallecimiento: " + anioFallecimiento +
                "\nLibros: " + libros;
    }
}


