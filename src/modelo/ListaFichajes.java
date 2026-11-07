package modelo;

import java.util.ArrayList;

public class ListaFichajes {
    private ArrayList<Futbolista> futbolistas = new ArrayList<>();

    public ArrayList<Futbolista> getFutbolistas() {
        return futbolistas;
    }
      
    public void setFutbolistas(ArrayList<Futbolista> futbolistas) {
        if (futbolistas != null) {
            this.futbolistas = futbolistas;
        }
    }
    
    
    // contro delduplicados y evitar nulos
    public void agregarFutbolista(Futbolista futbolista){
        if (futbolista != null && futbolista.getNombre() != null) {
            boolean existe = false;
            for (Futbolista f : futbolistas) {
                if (f.getNombre().equalsIgnoreCase(futbolista.getNombre())) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                futbolistas.add(futbolista);
            }
        }
    }

   public void borrarFutbolista(Futbolista futbolista){
        if (futbolista != null) {
            futbolistas.remove(futbolista);
        }
    }

    public Futbolista buscarFutbolista(String nombre){
        // Validación: Evitar nulos antes de comparar cadenas
        if (nombre != null && !nombre.trim().isEmpty()) {
            for (Futbolista futbolista: futbolistas){
                if (futbolista != null && nombre.equalsIgnoreCase(futbolista.getNombre())) {
                    return futbolista;
                }
            }
        }
        return null;
    }
}
