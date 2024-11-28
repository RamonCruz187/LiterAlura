package com.gutendex.alura.repository;

import com.gutendex.alura.model.Autor;
import com.gutendex.alura.model.Idioma;
import com.gutendex.alura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository <Libro, Long> {

    List<Libro> findAllByIdioma(Idioma idioma);

    boolean existsByTituloAndAutor(String titulo, Autor autor);
}
