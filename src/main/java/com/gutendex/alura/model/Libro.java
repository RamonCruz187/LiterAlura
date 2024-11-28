package com.gutendex.alura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private Integer descargas;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    public Libro() {}

    public Libro(LibroConsultado libroConsultado, Autor autor) {
        this.titulo = libroConsultado.titulo();
        this.autor = autor;
        this.idioma = Idioma.fromString(libroConsultado.idiomas().get(0));
        this.descargas = libroConsultado.descargas();
    }

    @Override
    public String toString() {
        return "\n=====LIBRO========" +
                "\n Título=" + titulo  +
                "\n Autor=" + autor.getNombre() +
                "\n Idioma=" + idioma +
                "\n Número de descargas=" + descargas +
                "\n==================";
    }
}
