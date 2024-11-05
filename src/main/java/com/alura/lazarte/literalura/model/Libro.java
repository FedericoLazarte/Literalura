package com.alura.lazarte.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   // @Column(unique = true)
    private String titulo;
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autores;
   // private List<String> idiomas;
    private String idioma;
    private Integer descargas;

    public Libro() {}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idioma = datosLibro.idiomas().get(0);
        this.descargas = datosLibro.descargas();
    }

    public List<Autor> getAutores() {
        return new ArrayList<>(this.autores);
    }

    public void setAutores(List<Autor> autores) {
        autores.forEach(a -> a.setLibro(this));
        this.autores = autores;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIdiomas() {
        return idioma;
    }

    public Integer getDescargas() {
        return descargas;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\n----------------------- " + " Información Libro " + "----------------------")
                .append("\n\t\tTítulo del libro: " + getTitulo())
                .append("\n\t\tIdioma del libro: " + getIdiomas())
                .append("\n\t\tTotal de descargar realizadas: " + getDescargas());
        return sb.toString();
    }
}