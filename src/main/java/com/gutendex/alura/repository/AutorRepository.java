package com.gutendex.alura.repository;

import com.gutendex.alura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository <Autor, Long> {

    Optional<Autor> findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE :year >= a.anioNacimiento AND (:year <= a.anioFallecimiento OR a.anioFallecimiento IS NULL)")
    List<Autor> findAutorsAliveInYear(Integer year);
}
