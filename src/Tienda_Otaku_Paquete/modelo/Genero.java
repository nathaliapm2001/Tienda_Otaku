package Tienda_Otaku_Paquete.modelo;
public class Genero {
    
    private String descripcion;
    private TiposGenero genero;

    public Genero(TiposGenero genero, String descripcion) {
        this.genero = genero;
        this.descripcion = descripcion;
    }

    public TiposGenero getGenero() {
        return genero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "Genero [descripcion=" + descripcion + ", genero=" + genero + "]";
    }



    
}
