package Tienda_Otaku_Paquete.datos;

import Tienda_Otaku_Paquete.dao.DAOObra;
import Tienda_Otaku_Paquete.modelo.Autor;
import Tienda_Otaku_Paquete.modelo.Genero;
import Tienda_Otaku_Paquete.modelo.Manga;
import Tienda_Otaku_Paquete.modelo.Manhwa;
import Tienda_Otaku_Paquete.modelo.Obra;
import Tienda_Otaku_Paquete.modelo.TiposGenero;

import java.io.*;
import java.util.List;

public class Fichero {
    private static final String FICHERO = "cache.txt";

    // Al iniciar el programa: lee la BD y vuelca al fichero
    public static void cargarDesdeDB() {
        try {
            SQLManga sqlManga = new SQLManga();
            SQLManhwa sqlManhwa = new SQLManhwa();

            BufferedWriter bw = new BufferedWriter(new FileWriter(FICHERO));

            for (Manga m : sqlManga.listarMangas()) {
                bw.write("MANGA|" + m.getIdObra() + "|" + m.getTitulo() + "|" +
                        m.getAutor().getNombre() + "|" + m.getAutor().getPais() + "|" +
                        m.getGenero().getGenero() + "|" + m.getGenero().getDescripcion() + "|" +
                        m.getEstado() + "|" + m.getEstrellas() + "|" +
                        m.getStock() + "|" + m.getVolumen());
                bw.newLine();
            }

            for (Manhwa mh : sqlManhwa.listarManhwas()) {
                bw.write("MANHWA|" + mh.getIdObra() + "|" + mh.getTitulo() + "|" +
                        mh.getAutor().getNombre() + "|" + mh.getAutor().getPais() + "|" +
                        mh.getGenero().getGenero() + "|" + mh.getGenero().getDescripcion() + "|" +
                        mh.getEstado() + "|" + mh.getEstrellas() + "|" +
                        mh.getStock() + "|" + mh.getCapitulo());
                bw.newLine();
            }

            bw.close();
            System.out.println("Cache actualizada desde la base de datos.");

        } catch (Exception e) {
            System.out.println("Error al leer la BD: " + e.getMessage());

        }
    }

    // Al salir: lee el fichero y actualiza la BD
    public static void guardarEnDB() {
        try {
            SQLManga sqlManga = new SQLManga();
            SQLManhwa sqlManhwa = new SQLManhwa();

            sqlManga.getConnection().createStatement().execute("DELETE FROM Manga");
            sqlManhwa.getConnection().createStatement().execute("DELETE FROM Manhwa");

            BufferedReader br = new BufferedReader(new FileReader(FICHERO));
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] p = linea.split("\\|");

                Autor autor = new Autor(p[3], p[4]);
                Genero genero = new Genero(TiposGenero.valueOf(p[5]), p[6]);

                if (p[0].equals("MANGA")) {
                    Manga manga = new Manga(p[1], p[2], autor, genero,
                            p[7], Double.parseDouble(p[8]),
                            Integer.parseInt(p[9]), Integer.parseInt(p[10]));
                    sqlManga.insertarManga(manga);

                } else if (p[0].equals("MANHWA")) {
                    Manhwa manhwa = new Manhwa(p[1], p[2], autor, genero,
                            p[7], Double.parseDouble(p[8]),
                            Integer.parseInt(p[9]), Integer.parseInt(p[10]));
                    sqlManhwa.insertarManhwa(manhwa);
                }
            }

            br.close();
            System.out.println("Base de datos actualizada desde el fichero.");

        } catch (Exception e) {
            System.out.println("Error al guardar en BD: " + e.getMessage());

        }
    }

    // cache.txt → DAOObra (para trabajar en memoria)
    public static DAOObra cargarEnMemoria() {
        DAOObra dao = new DAOObra();
        try {
            BufferedReader br = new BufferedReader(new FileReader(FICHERO));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] p = linea.split("\\|");
                Autor autor = new Autor(p[3], p[4]);
                Genero genero = new Genero(TiposGenero.valueOf(p[5]), p[6]);
                if (p[0].equals("MANGA")) {
                    dao.agregarObra(new Manga(p[1], p[2], autor, genero,
                            p[7], Double.parseDouble(p[8]),
                            Integer.parseInt(p[9]), Integer.parseInt(p[10])));
                } else if (p[0].equals("MANHWA")) {
                    dao.agregarObra(new Manhwa(p[1], p[2], autor, genero,
                            p[7], Double.parseDouble(p[8]),
                            Integer.parseInt(p[9]), Integer.parseInt(p[10])));
                }
            }
            br.close();
            System.out.println("Datos cargados en memoria.");
        } catch (Exception e) {
            System.out.println("Error al cargar en memoria: " + e.getMessage());

        }
        return dao;
    }

    // DAOObra (memoria) → cache.txt
    public static void guardarDesdeMemoria(List<Obra> obras) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FICHERO));
            for (Obra o : obras) {
                if (o instanceof Manga m) {
                    bw.write("MANGA|" + m.getIdObra() + "|" + m.getTitulo() + "|" +
                            m.getAutor().getNombre() + "|" + m.getAutor().getPais() + "|" +
                            m.getGenero().getGenero() + "|" + m.getGenero().getDescripcion() + "|" +
                            m.getEstado() + "|" + m.getEstrellas() + "|" +
                            m.getStock() + "|" + m.getVolumen());
                } else if (o instanceof Manhwa mh) {
                    bw.write("MANHWA|" + mh.getIdObra() + "|" + mh.getTitulo() + "|" +
                            mh.getAutor().getNombre() + "|" + mh.getAutor().getPais() + "|" +
                            mh.getGenero().getGenero() + "|" + mh.getGenero().getDescripcion() + "|" +
                            mh.getEstado() + "|" + mh.getEstrellas() + "|" +
                            mh.getStock() + "|" + mh.getCapitulo());
                }
                bw.newLine();
            }
            bw.close();
            System.out.println("Fichero actualizado desde memoria.");
        } catch (Exception e) {
            System.out.println("Error al guardar el fichero: " + e.getMessage());

        }
    }

}