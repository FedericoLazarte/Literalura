package com.alura.lazarte.literalura.repository;

import com.alura.lazarte.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByIdiomaContaining(String idioma);
    int countByIdioma(String idioma);
    List<Libro> findTop10ByOrderByDescargasDesc();
}
