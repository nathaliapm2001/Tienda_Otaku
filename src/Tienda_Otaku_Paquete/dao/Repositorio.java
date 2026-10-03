package Tienda_Otaku_Paquete.dao;

import Tienda_Otaku_Paquete.modelo.Obra;
import java.util.List;

public interface Repositorio {

    public void agregarObra(Obra obra);

    public boolean eliminarObra(String idObra);

    public List<Obra> obtenerObras();

    public List<Obra> obtenerMejorValoradas();
}
