package modelo;

import java.util.ArrayList;

public class ListaPatrocinios {
    private ArrayList<Patrocinio> patrocinios = new ArrayList<>();

    public ArrayList<Patrocinio> getPatrocinios() {
        return patrocinios;
    }

    public void setPatrocinios(ArrayList<Patrocinio> patrocinios) {
        if (patrocinios != null) {
            this.patrocinios = patrocinios;
        }
    }

    public void agregarPatrocinio(Patrocinio patrocinio){
        // Validación: Evitar nulos y duplicados por nombre
        if (patrocinio != null && patrocinio.getNombre() != null) {
            boolean existe = false;
            for (Patrocinio p : patrocinios) {
                if (p.getNombre().equalsIgnoreCase(patrocinio.getNombre())) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                patrocinios.add(patrocinio);
            }
        }
    }

    public void borrarPatrocinio(Patrocinio patrocinio){
        if(patrocinio != null){
            patrocinios.remove(patrocinio);
        }
        
    }

  public Patrocinio buscarPatrocinio(String nombre){
        // Validación: Evitar nulos antes de comparar cadenas
        if (nombre != null && !nombre.trim().isEmpty()) {
            for (Patrocinio patrocinio: patrocinios){
                if (patrocinio != null && nombre.equalsIgnoreCase(patrocinio.getNombre())) {
                    return patrocinio;
                }
            }
        }
        return null;
    }
}