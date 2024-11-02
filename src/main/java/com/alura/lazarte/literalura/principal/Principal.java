package com.alura.lazarte.literalura.principal;

import com.alura.lazarte.literalura.model.DatosLibro;
import com.alura.lazarte.literalura.model.Libro;
import com.alura.lazarte.literalura.model.RespuestaAPI;
import com.alura.lazarte.literalura.repository.LibroRepository;
import com.alura.lazarte.literalura.service.ConsumoAPI;
import com.alura.lazarte.literalura.service.ConvertirDatos;

import java.util.List;
import java.util.Scanner;

public class Principal {
    private static final String API_URL = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvertirDatos conversor = new ConvertirDatos();
    private Scanner teclado = new Scanner(System.in);
    private LibroRepository repositorio;

    public Principal(LibroRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void app() {
        int opcion = -1;
        while(opcion != 0) {
            mostrarMenu();
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch(opcion) {
                case 1:
                    buscarLibrosPorNombre();
                    break;
                case 2:
                    mostrarLibrosBuscados();
                    break;
                case 0:
                    System.out.println("Gracias por haber utilizado nuestro servicio");
                    break;
                default:
                    System.out.println("Opción inválida: debe escoger entre (0-2)");
            }
        }
    }

    private void buscarLibrosPorNombre() {
        System.out.println("Ingresa el nombre del libro que deseas buscar");
        String nombreLibro = teclado.nextLine();
        String json = consumoAPI.obtenerDatos(API_URL + "?search=" + nombreLibro.replace(" ", "%20"));
        RespuestaAPI respuesta = conversor.obtenerDatos(json, RespuestaAPI.class);
        for (DatosLibro l : respuesta.resultados()) {
            Libro libro = new Libro(l);
            repositorio.save(libro);
        }
    }


    private void mostrarLibrosBuscados() {
        List<Libro> libros = repositorio.findAll();
        libros.forEach(System.out::println);
    }

    private void mostrarMenu() {
        System.out.println("""
                    1 -  Buscar Libros
                    2 - Consultar Libros Buscados
                    0 - Salir
                """);
    }


}
