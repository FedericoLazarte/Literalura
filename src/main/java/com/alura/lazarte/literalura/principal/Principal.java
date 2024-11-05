package com.alura.lazarte.literalura.principal;

import com.alura.lazarte.literalura.model.*;
import com.alura.lazarte.literalura.repository.AutorRepository;
import com.alura.lazarte.literalura.repository.LibroRepository;
import com.alura.lazarte.literalura.service.ConsumoAPI;
import com.alura.lazarte.literalura.service.ConvertirDatos;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private static final String API_URL = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvertirDatos conversor = new ConvertirDatos();
    private Scanner teclado = new Scanner(System.in);
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private List<Libro> libros;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void app() {
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu();
            System.out.print("Ingrese la opción que desea realizar: ");
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibrosPorNombre();
                    break;
                case 2:
                    mostrarLibrosBuscados();
                    break;
                case 3:
                    listarAutoresDeLibrosBuscados();
                    break;
                case 4:
                    buscarLibrosPorIdioma();
                    break;
                case 5:
                    autoresVivosEnCiertaFecha();
                    break;
                case 6:
                    contarLibrosDeUnDeterminadoIdioma();
                    break;
                case 0:
                    System.out.println("Gracias por haber utilizado nuestro servicio");
                    System.exit(opcion);
                    break;
                default:
                    System.out.println("Opción inválida: debe escoger entre (0-2)");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("""
                \n1 -  Buscar Libros
                2 - Consultar Libros Buscados
                3 - Mostrar Autores de Libros Buscados
                4 - Buscar libro por idioma
                5 - Consultar autores vivos en el rango de ciertos años
                6 - Buscar cantidad de libros en cierto idioma, por ejemplo (es - en - etc)
                0 - Salir
                """);
    }

    private void buscarLibrosPorNombre() {
        System.out.print("\nIngresa el nombre del libro que deseas buscar: ");
        String nombreLibro = teclado.nextLine();
        RespuestaAPI respuesta = obtenerLibros(nombreLibro);
        // Me quedo solamente con la primera aparición de un libro
        Libro libro = new Libro(respuesta.resultados().get(0));

        List<DatosAutor> datosAutores = respuesta.resultados().get(0).autores();
        List<Autor> autores = datosAutores.stream()
                .map(Autor::new)
                .collect(Collectors.toList());
        autores.forEach(a -> a.setLibro(libro));
        libro.setAutores(autores);

        libroRepository.save(libro);
        System.out.println("\nLibro encontrado: " + libro);
    }

    private RespuestaAPI obtenerLibros(String libro) {
        String json = consumoAPI.obtenerDatos(API_URL + "?search=" + libro.replace(" ", "%20"));
        return conversor.obtenerDatos(json, RespuestaAPI.class);
    }

    private void mostrarLibrosBuscados() {
        libros = libroRepository.findAll();
        libros.forEach(System.out::println);
    }

    private void listarAutoresDeLibrosBuscados() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    private void buscarLibrosPorIdioma() {
        System.out.print("\nIngrese el idioma del libro que queriere ver (por ejemplo: en - para inglés, es - para español: ");
        String idioma = teclado.nextLine();
        List<Libro> librosIdioma = libroRepository.findByIdiomaContaining(idioma);
        System.out.println("\nLos libros en " + idioma + " son");
        librosIdioma.forEach(System.out::println);
    }

    private void autoresVivosEnCiertaFecha() {
        System.out.print("\nIngrese el año inicial: ");
        int anioInicial = teclado.nextInt();
        System.out.print("\nIngrese el año final: ");
        int anioFinal = teclado.nextInt();
        teclado.nextLine();
        try {
            validarAnios(anioInicial, anioFinal);
            List<Autor> autoresVivos = autorRepository.findAutoresVivosEntreAnios(anioInicial, anioFinal);
            if(autoresVivos.isEmpty())
                System.out.println("\nNo se encontraron autores vivos en los años especificados");
            else
                autoresVivos.forEach(System.out::println);
        } catch (IllegalArgumentException e) {
            System.out.println("\nError en los años ingresados: " + e);
        }
    }

    private void validarAnios(int inicio, int fin) {
        if(inicio < 0 || fin < 0)
            throw new IllegalArgumentException("\nLos años no puede ser negativos");
        if(inicio >= fin)
            throw new IllegalArgumentException("\nEl año de inicio debe ser menor si o si al año final");
    }

    private void contarLibrosDeUnDeterminadoIdioma() {
        System.out.print("\nIngrese el idioma de los libros que desea encontrar: ");
        String idioma = teclado.nextLine();
        int cantidadLibrosEnCiertoIdioma = libroRepository.countByIdioma(idioma);
        if(cantidadLibrosEnCiertoIdioma > 0)
            System.out.println("\nLa cantidad de libros que hay en el idioma especificado " + idioma + " es: " + cantidadLibrosEnCiertoIdioma);
        else
            System.out.println("\nNo se han encontrado libros en el idioma especificado");
    }
}
