package modelo;

import java.util.ArrayList;

public class ListaPatrocinios {
    private ArrayList<Patrocinio> patrocinios = new ArrayList<>();

    public ArrayList<Patrocinio> getPatrocinios() {
        return patrocinios;
    }

    public void setPatrocinios(ArrayList<Patrocinio> patrocinios) {
        this.patrocinios = patrocinios;
    }

    public void agregarPatrocinio(Patrocinio patrocinio){
        patrocinios.add(patrocinio);
    }

}
