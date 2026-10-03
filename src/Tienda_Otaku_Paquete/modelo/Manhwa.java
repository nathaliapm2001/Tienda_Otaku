package Tienda_Otaku_Paquete.modelo;


public class Manhwa extends Obra {

    private int capitulo;

    public Manhwa(String idObra, String titulo, Autor autor, Genero genero, String estado, double estrellas, int stock,
            int capitulo) {
        super(idObra, titulo, autor, genero, estado, estrellas, stock);
        this.capitulo = capitulo;
    }

    public int getCapitulo() {
        return capitulo;
    }

    @Override
    public String mostrarDetalles() {
        return "Manhwa: " + super.getTitulo() + "\n" +
                "Autor: " + super.getAutor().getNombre() + "\n" +
                "Genero: " + super.getGenero().getGenero() + " " + super.getGenero().getDescripcion() + "\n" +
                "Estado: " + super.getEstado() + "\n" +
                "Estrellas: " + super.getEstrellas() + "\n" +
                "Stock: " + super.getStock() + "\n" +
                "Capitulo: " + getCapitulo();
    }

}
