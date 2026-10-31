package modelo;

import java.util.ArrayList;

public class ListaFichajes {
    private ArrayList<Futbolista> futbolistas = new ArrayList<>();

    public ArrayList<Futbolista> getFutbolistas() {
        return futbolistas;
    }

    public void setFutbolistas(ArrayList<Futbolista> futbolistas) {
        this.futbolistas = futbolistas;
    }

    public void agregarFutbolista(Futbolista futbolista){
        futbolistas.add(futbolista);
    }

    public void borrarFutbolista(Futbolista futbolista){
        futbolistas.remove(futbolista);
    }

    public Futbolista buscarFutbolista(String nombre){
        for (Futbolista futbolista: futbolistas){
            if (nombre.equals(futbolista.getNombre()))
                return futbolista;
        }
        return null;
    }
}
