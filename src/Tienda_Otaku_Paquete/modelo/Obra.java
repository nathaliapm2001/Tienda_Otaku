package Tienda_Otaku_Paquete.modelo;

import java.util.Objects;

public abstract class Obra {

    private String idObra;
    private String titulo;
    private Autor autor;
    private Genero genero;
    private String estado;
    private double estrellas;
    private int stock;

    public Obra(String idObra, String titulo, Autor autor, Genero genero, String estado, double estrellas, int stock) {
        this.idObra = idObra;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.estado = estado;
        this.estrellas = estrellas;
        this.stock = stock;
    }

    public abstract String mostrarDetalles();

    public String getIdObra() {
        return idObra;
    }

    public String getTitulo() {
        return titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public Genero getGenero() {
        return genero;
    }

    public String getEstado() {
        return estado;
    }

    public double getEstrellas() {
        return estrellas;
    }

    public int getStock() {
        return stock;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.idObra);
        hash = 53 * hash + Objects.hashCode(this.titulo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Obra other = (Obra) obj;
        if (!Objects.equals(this.idObra, other.idObra)) {
            return false;
        }
        return Objects.equals(this.titulo, other.titulo);
    }

}