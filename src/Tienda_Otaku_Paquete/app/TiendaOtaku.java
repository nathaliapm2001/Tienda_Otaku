package Tienda_Otaku_Paquete.app;

import java.util.List;
import java.util.Scanner;
import Tienda_Otaku_Paquete.datos.SQLManga;
import Tienda_Otaku_Paquete.datos.SQLManhwa;
import Tienda_Otaku_Paquete.excepciones.DatosInvalidosException;
import Tienda_Otaku_Paquete.modelo.Autor;
import Tienda_Otaku_Paquete.modelo.Genero;
import Tienda_Otaku_Paquete.modelo.Manga;
import Tienda_Otaku_Paquete.modelo.Manhwa;
import Tienda_Otaku_Paquete.modelo.Obra;
import Tienda_Otaku_Paquete.modelo.TiposGenero;
import Tienda_Otaku_Paquete.datos.Fichero;
import Tienda_Otaku_Paquete.dao.DAOObra;

public class TiendaOtaku {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            new SQLManga();
            new SQLManhwa();
            System.out.println("Base de datos lista.");
        } catch (Exception e) {
            System.out.println("Error al iniciar la BD: " + e.getMessage());
            return;
        }

        Fichero.cargarDesdeDB();
        DAOObra dao = Fichero.cargarEnMemoria();

        String opcion;
        do {
            System.out.println("======MENU========");
            System.out.println("""
                    1.Mostrar catalogo de disponibles
                    2.Almacenar pedido
                    3.Eliminar del catalogo(Manga o Manhwa)
                    4.Mostrar los mejores valorados
                    5.Ver cual esta en emision o finalizado
                    6.Mostrar por generos
                    7.Mostrar las novedades (mas recientes)
                    0.Salir
                        """);
            opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    mostrarCatalogo(dao);

                    break;

                case "2":
                    agregarObra(dao);
                    break;

                case "3":
                    eliminarObra(dao);
                    break;

                case "4":
                    mostrarMejorValoradas(dao);
                    break;

                case "5":
                    mostrarPorEstado(dao);
                    break;

                case "6":
                    mostrarPorGenero(dao);
                    break;

                case "7":
                    mostrarNovedades(dao);
                    break;

                case "0":
                    Fichero.guardarDesdeMemoria(dao.obtenerObras());
                    Fichero.guardarEnDB();
                    System.out.println("Finalizacion del dia");

                    break;

                default:
                    System.out.println("Error: Opcion Invalida");
                    break;
            }

        } while (!opcion.equals("0"));

    }

    // 1. Catálogo (solo con stock > 0)
    private static void mostrarCatalogo(DAOObra dao) {
        List<Obra> obras = dao.obtenerObras();
        System.out.println("--- CATÁLOGO DISPONIBLE ---");
        boolean hayStock = false;
        for (Obra o : obras) {
            if (o.getStock() > 0) {
                System.out.println(o.mostrarDetalles());
                System.out.println("---");
                hayStock = true;
            }
        }
        if (!hayStock) {
            System.out.println("No hay obras en stock.");
        }
    }

    // 2. Añadir obra
    private static void agregarObra(DAOObra dao) {
        try {
            System.out.println("¿Tipo de obra? (1=Manga / 2=Manhwa)");
            String tipo = sc.nextLine();

            System.out.print("ID: ");
            String id = sc.nextLine();
            System.out.print("Título: ");
            String titulo = sc.nextLine();
            System.out.print("Autor: ");
            String autorNombre = sc.nextLine();
            System.out.print("País del autor: ");
            String autorPais = sc.nextLine();
            System.out
                    .print("""
                            Tipo género:
                            BL, SHONEN, SEINEN, TRANSMIGRATION, ISEKAI, COMEDY, DRAMA, TERROR, ACCION, ROMANCE, DEPORTES, SHOJO, MECHA, FANTASIA, JOSEI, ESCOLAR, SLICEOFLIFE, AVENTURA

                                """);
            String tipoGenero = sc.nextLine().toUpperCase();
            System.out.print("Descripción género: ");
            String descGenero = sc.nextLine();
            System.out.print("Estado (activo/finalizado): ");
            String estado = sc.nextLine();
            System.out.print("Estrellas (0-5): ");
            double estrellas = Double.parseDouble(sc.nextLine());
            System.out.print("Stock: ");
            int stock = Integer.parseInt(sc.nextLine());

            if (estrellas < 0 || estrellas > 5) {
                throw new DatosInvalidosException(
                        "Las estrellas deben estar entre 0 y 5 (valor introducido: " + estrellas + ").");
            }
            if (stock < 0) {
                throw new DatosInvalidosException(
                        "El stock no puede ser negativo (valor introducido: " + stock + ").");
            }

            Autor autor = new Autor(autorNombre, autorPais);
            Genero genero = new Genero(TiposGenero.valueOf(tipoGenero), descGenero);

            if (tipo.equals("1")) {
                System.out.print("Volúmenes: ");
                dao.agregarObra(new Manga(id, titulo, autor, genero, estado, estrellas, stock,
                        Integer.parseInt(sc.nextLine())));
            } else {
                System.out.print("Capítulos: ");
                dao.agregarObra(new Manhwa(id, titulo, autor, genero, estado, estrellas, stock,
                        Integer.parseInt(sc.nextLine())));
            }
            System.out.println("Obra añadida.");

        } catch (Exception e) {
            System.out.println("Error al añadir: " + e.getMessage());
        }
    }

    // 3. Eliminar
    private static void eliminarObra(DAOObra dao) {
        System.out.print("ID de la obra a eliminar: ");
        String id = sc.nextLine();
        if (dao.eliminarObra(id)) {
            System.out.println("Obra eliminada.");
        } else {
            System.out.println("No se encontró ninguna obra con ese ID.");
        }
    }

    // 4. Mejor valoradas (>= 4.5)
    private static void mostrarMejorValoradas(DAOObra dao) {
        List<Obra> obras = dao.obtenerMejorValoradas();
        System.out.println("--- MEJOR VALORADAS (max 5) ---");
        if (obras.isEmpty()) {
            System.out.println("Ninguna.");
            return;
        }
        for (Obra o : obras) {
            System.out.println(o.mostrarDetalles());
            System.out.println("---");
        }
    }

    // 5. Por estado
    private static void mostrarPorEstado(DAOObra dao) {
        System.out.print("Estado (activo / finalizado): ");
        String estado = sc.nextLine();
        System.out.println("--- OBRAS: " + estado.toUpperCase() + " ---");
        boolean encontrado = false;
        for (Obra o : dao.obtenerObras()) {
            if (o.getEstado().equalsIgnoreCase(estado)) {
                System.out.println(o.mostrarDetalles());
                System.out.println("---");
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No hay obras con ese estado.");
        }
    }

    // 6. Por género
    private static void mostrarPorGenero(DAOObra dao) {
        System.out
                .print("""
                        Género:
                        BL, SHONEN, SEINEN, TRANSMIGRATION, ISEKAI, COMEDY, DRAMA, TERROR, ACCION, ROMANCE, DEPORTES, SHOJO, MECHA, FANTASIA, JOSEI, ESCOLAR, SLICEOFLIFE, AVENTURA

                            """);
        String genero = sc.nextLine().toUpperCase();
        System.out.println("--- OBRAS: " + genero + " ---");
        boolean encontrado = false;
        for (Obra o : dao.obtenerObras()) {
            if (o.getGenero().getGenero().name().equalsIgnoreCase(genero)) {
                System.out.println(o.mostrarDetalles());
                System.out.println("---");
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No hay obras de ese género.");
        }
    }

    // 7. Novedades (últimas 5 añadidas)
    private static void mostrarNovedades(DAOObra dao) {
        List<Obra> obras = dao.obtenerObras();
        System.out.println("--- NOVEDADES (últimas 5) ---");

        int contador = 0;
        for (int i = obras.size() - 1; i >= 0 && contador < 5; i--) {
            System.out.println(obras.get(i).mostrarDetalles());
            System.out.println("---");
            contador++;
        }
    }
}
