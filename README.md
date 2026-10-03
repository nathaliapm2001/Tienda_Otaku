# 🏯 Tienda Otaku

Este repositorio contiene un proyecto desarrollado en **Java** que simula la gestión de una tienda especializada en **manga y manhwa**.

El programa permite gestionar un catálogo de obras, controlar el stock, almacenar información y realizar diferentes consultas mediante un menú por consola.

---

## 👥 Miembros del grupo

- **Nathalia Piñera Molina**  
- **Ivan Mena Damian**

---

## 🎯 Objetivo del proyecto

El objetivo del proyecto es aplicar los conocimientos adquiridos durante el curso sobre:

- Programación orientada a objetos.
- Herencia y polimorfismo.
- Colecciones de Java.
- Gestión de ficheros.
- Persistencia de datos.
- Patrón DAO.
- Excepciones personalizadas.
- Organización del código mediante paquetes.
- Control de versiones con Git y GitHub.

---

## 🛒 Funcionalidades

El programa dispone de las siguientes opciones:

1. Mostrar el catálogo de obras disponibles.
2. Almacenar pedidos.
3. Eliminar Manga o Manhwa del catálogo.
4. Mostrar las obras mejor valoradas.
5. Mostrar obras según su estado de emisión.
6. Buscar obras por género.
7. Mostrar las novedades más recientes.
0. Salir del programa.

También permite controlar el **stock disponible**, validar los datos introducidos y almacenar la información.

---

## 🗂️ Estructura del proyecto

El proyecto está dividido en diferentes paquetes para separar las responsabilidades del programa.

### 🚀 `app`

Contiene la clase principal del programa.

`TiendaOtaku.java`

Desde esta clase se ejecuta el menú principal y se gestionan las diferentes opciones disponibles.

---

### 📚 `modelo`

Contiene las clases que representan los objetos principales de la tienda.

`Obra.java`  
`Manga.java`  
`Manhwa.java`  
`Autor.java`  
`Genero.java`  
`TiposGenero.java`

Las clases `Manga` y `Manhwa` representan los distintos tipos de obras disponibles en el catálogo.

---

### 🗄️ `dao`

Contiene las clases encargadas de gestionar y acceder a las obras almacenadas.

`DAOObra.java`  
`Repositorio.java`

Se utiliza el patrón **DAO (Data Access Object)** para separar la lógica de acceso a los datos de la lógica principal del programa.

---

### 💾 `datos`

Contiene las clases relacionadas con el almacenamiento y recuperación de información.

`Fichero.java`  
`SQLManga.java`  
`SQLManhwa.java`

Estas clases permiten guardar y recuperar los datos utilizados por el programa.

---

### ⚠️ `excepciones`

Contiene las excepciones personalizadas utilizadas para validar los datos introducidos.

`DatosInvalidosException.java`

Se realizan distintas comprobaciones, como por ejemplo:

- Las valoraciones deben estar entre **0 y 5 estrellas**.
- El stock no puede ser negativo.
- Los datos introducidos deben tener valores válidos.

---

## ⭐ Gestión de obras

Cada obra contiene información como:

- Título.
- Autor.
- País del autor.
- Género.
- Descripción.
- Estado de publicación.
- Valoración.
- Stock disponible.

Dependiendo del tipo de obra, el programa permite crear objetos de tipo **Manga** o **Manhwa**.

---

## 🧭 Menú principal

Al iniciar el programa se muestra un menú similar al siguiente:

```text
========= MENU =========

1. Mostrar catalogo de disponibles
2. Almacenar pedido
3. Eliminar del catalogo (Manga o Manhwa)
4. Mostrar los mejores valorados
5. Ver cual esta en emision o finalizado
6. Mostrar por generos
7. Mostrar las novedades
0. Salir