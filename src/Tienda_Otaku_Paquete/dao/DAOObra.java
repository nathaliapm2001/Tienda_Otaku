package Tienda_Otaku_Paquete.dao;

import Tienda_Otaku_Paquete.modelo.Obra;
import java.util.ArrayList;
import java.util.List;


public class DAOObra implements Repositorio {

    private List<Obra> obras;

    public DAOObra() {
        this.obras = new ArrayList<>();
    }

    @Override
    public void agregarObra(Obra obra) {
        obras.add(obra);
    }

    @Override
    public boolean eliminarObra(String idObra) {

        for (Obra obra : obras) {
            if (obra.getIdObra().equals(idObra)) {
                obras.remove(obra);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Obra> obtenerObras() {
        return new ArrayList<>(obras);
    }


    @Override
    public List<Obra> obtenerMejorValoradas() {
        List<Obra> mejorValoradas = new ArrayList<>();
        for (Obra obra : obras) {
            if (obra.getEstrellas() >= 4.5) { 
                mejorValoradas.add(obra);
            }
        }
        return mejorValoradas;
    }
}
