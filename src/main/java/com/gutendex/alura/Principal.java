package com.gutendex.alura;

import com.gutendex.alura.model.*;
import com.gutendex.alura.repository.AutorRepository;
import com.gutendex.alura.repository.LibroRepository;
import com.gutendex.alura.service.ConsumoAPI;
import com.gutendex.alura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();

    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;

    public Principal(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    --------------------
                    Elija la opción a través de su número
                    1 - Buscar libro por título 
                    2 - listar libros registrados
                    3 - listar autores registrados
                    4 - listar autores vivos en un determinado año
                    5 - listar libros por idioma  
                    0 - Salir
                    """;
            System.out.println(menu);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();
            }catch (Exception e){
                System.out.println("\nOpción inválida, debes ingresar un número entre 0 y 5. Reiniciar la aplicación\n");
                break;
            }

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivosPorAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida, debes ingresar un número entre 0 y 5.");
            }
        }

    }

    private void buscarLibro() {
        System.out.println("\nIngresa el nombre del libro que deseas buscar");
        String busqueda = teclado.nextLine();
        var libroBuscado = consumoApi.obtenerDatos(URL_BASE + "?search=" + busqueda.replace(" ", "%20"));
        var libro = conversor.obtenerdatos(libroBuscado, ListaDeLibros.class);
        if (libro.libros().isEmpty()) {
            System.out.println("No se encontraron libros con ese nombre\n");
            return;
        }
        //selecciona el primer libro de la lista
        LibroConsultado libroConsultado = libro.libros().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontraron libros con ese nombre"));

        //buscar o crear el autor
        Autor autor = autorRepository.findByNombre(libroConsultado.autor().get(0).nombre())
                .orElseGet(()->{
                    Autor nuevoAutor = new Autor(libroConsultado);
                    return autorRepository.save(nuevoAutor);
                });

        //si el libro ya existe en la BD, no se guarda
        if (libroRepository.existsByTituloAndAutor(libroConsultado.titulo(), autor)) {
            System.out.println("Libro ya registrado\n");
        }else {
            Libro libro1 = new Libro(libroConsultado, autor);
            autor.agregarLibro(libro1);
            autorRepository.save(autor);
            System.out.println(libro1);
            System.out.println("Libro registrado con exito\n");
        }
    }

    private void listarLibros() {
        var libros = libroRepository.findAll();
        libros.forEach(System.out::println);
    }

    private void listarAutores() {
        var autores = autorRepository.findAll();
        autores.stream().map(a -> new AutorDTO(a)).forEach(System.out::println);
    }

    private void listarAutoresVivosPorAnio() {
        System.out.println("Ingresa el año");
        var anio = teclado.nextInt();
        var autores = autorRepository.findAutorsAliveInYear(anio);
        autores.stream().map(a -> new AutorDTO(a)).forEach(System.out::println);
    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
                Ingrese el idioma para buscar los libros:
                es- español 
                en- inglés
                fr- frances
                pt- português
                """);
        var idioma = teclado.nextLine();
        var libros = libroRepository.findAllByIdioma(Idioma.valueOf(idioma.toUpperCase()));
        libros.forEach(System.out::println);
    }
}
