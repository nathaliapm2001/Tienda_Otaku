package Tienda_Otaku_Paquete.modelo;
public class Autor {
    
    private String nombre;
    private String pais;

    public Autor(String nombre, String pais) {
        this.nombre = nombre;
        this.pais = pais;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPais() {
        return pais;
    }

    @Override
    public String toString() {
        return "Autor [nombre=" + nombre + ", pais=" + pais + "]";
    }



    
}
