package modelo;

import java.util.ArrayList;

public class ListaClubes {
    private ArrayList<Club> clubes = new ArrayList<>();

    public ArrayList<Club> getClubes() {
        return clubes;
    }

    public void setClubes(ArrayList<Club> clubes) {
        this.clubes = clubes;
    }

    public void agregarClub(Club club){
        clubes.add(club);
    }

    public void borrarClub(Club club){
        clubes.remove(club);
    }

    public Club buscarclub(String nombre){
        for (Club club: clubes){
            if (nombre.equals(club.getNombre()))
                return club;
        }
        return null;
    }

    public int getCantidad(){
        int contador = 0;
        for (Club club: clubes)
            contador++;
        return contador;
    }

}