package com.alura.lazarte.literalura.repository;

import com.alura.lazarte.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :anioFin AND (a.fechaFallecimiento IS NULL OR a.fechaFallecimiento >= :anioInicio)")
    List<Autor> findAutoresVivosEntreAnios(@Param("anioInicio") int anioInicio, @Param("anioFin") int anioFin);
}
