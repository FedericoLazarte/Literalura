# Literalura

**Desarrollado por:** Federico Lazarte
**Correo electrónico:** fedelazarte2015@gmail.com

Les presento el proyecto `Literalura`, es una aplicación de consola la cual interactúa con el catálogo de libros de la API [Gutendex](https://gutendex.com/). La aplicación permite buscar y consultar libros, autores,
idiomas y realizar análisis sobre el contenido disponible en la base de datos.

---

## Tabla de Contenidos

1. [Descripción General](#descripción-general)
2. [Requerimientos](#requerimientos)
3. [Uso](#uso)
4. [Clases y Funcionalidades Principales](#clases-y-funcionalidades-principales)

---

## Descripción General

La aplicación `Literalura` permite:
- Buscar y almacenar libros de la API Gutendex.
- Consultar Libros buscados.
- Filtrar libros por idioma y contar la cantidad de libros disponibles en un idioma específico.
- Consultar autores que estaban vivos en un rango de años especificado por el usuario.
- Consultar el top 10 de libros más descargados
- Consultar las distintas estadísticas en función de las descargas, desde el promedio, al libro menos y más descargado, y el total de descargas.
- Consultar autores en base a su nombre que se encuentren almacenados en la base de datos.
## Requerimientos

- **Java**: Versión 17 o superior
- **Spring Boot**: Utilizado para configurar el proyecto y la conexión con la base de datos.
- **Base de Datos**: La configuración depende de la base de datos deseada (ej., PostgreSQL, MySQL). Debe configurarse en el archivo `application.properties`.
- **Dependencias**: Manejo de datos JSON mediante Jackson para la conversión de datos.

## Uso

Al ejecutar la aplicación, se desplegará un menú de opciones en la consola. Para seleccionar una opción, ingresa el número correspondiente.

## Clases y Funcionalidades Principales

### 1. **Principal**
   - **Función**: Controla la interacción del usuario y la ejecución de las distintas funcionalidades de la aplicación.
   - **Métodos Principales**:
     - `buscarLibrosPorNombre()`: Permite buscar libros en la API de Gutendex y guardar la primera aparición en la base de datos.
     - `mostrarLibrosBuscados()`: Lista todos los libros almacenados en la base de datos.
     - `listarAutoresDeLibrosBuscados()`: Muestra los autores de todos los libros guardados.
     - `buscarLibrosPorIdioma()`: Permite buscar libros en la base de datos por idioma específico.
     - `autoresVivosEnCiertaFecha()`: Consulta y muestra autores vivos en un rango de años especificado.
     - `contarLibrosDeUnDeterminadoIdioma()`: Cuenta y muestra el número de libros disponibles en un idioma específico.
     - `buscarTop10Libros()`: Consulta el top 10 de libros más descargados.
     - `estadisticasLibros()`: Consulta las estadisticas en base a las descargas de los libros.
     - `buscarAutorPorNombre()`: Consulta mediante un nombre de autor si este se encuentra almacenado.
### 2. **ConsumoAPI**
   - **Función**: Realiza la solicitud HTTP a la API de Gutendex y recupera la información en formato JSON.
   - **Método Principal**:
     - `obtenerDatos(String url)`: Realiza una solicitud GET a la API y devuelve el JSON de respuesta como cadena.

### 3. **ConvertirDatos**
   - **Función**: Convierte el JSON recibido en una estructura de objetos Java.
   - **Método Principal**:
     - `obtenerDatos(String json, Class<T> clase)`: Deserializa el JSON en una instancia de la clase especificada.

### 4. **Repositorios**
   - **AutorRepository**: Define consultas específicas para los datos de autores, incluyendo búsqueda de autores vivos en ciertos años.
   - **LibroRepository**: Proporciona métodos para contar libros por idioma y buscar libros en un idioma específico.

### 5. **Modelos**
   - **Autor**: Representa a un autor de libro, con atributos como nombre, fecha de nacimiento y fallecimiento.
   - **Libro**: Representa a un libro, con atributos como título, idioma y lista de autores.
   - **DatosLibro, DatosAutor, RespuestaAPI**: Clases utilizadas para mapear la respuesta JSON de la API.
