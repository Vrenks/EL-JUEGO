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

}