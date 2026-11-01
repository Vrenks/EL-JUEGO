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

    public void modificarRival(Club rival){
        for (Club club: clubes){
            if (rival.getNombre().equals(club.getNombre())){
                club.setPartidosJugados(rival.getPartidosJugados());
            }
        }
    }

    public int getCantidad(){
        int contador = 0;
        for (Club club: clubes)
            contador++;
        return contador;
    }

}