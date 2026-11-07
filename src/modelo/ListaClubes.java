package modelo;

import java.util.ArrayList;

public class ListaClubes {
    private ArrayList<Club> clubes = new ArrayList<>();

    public ArrayList<Club> getClubes() {
        return clubes;
    }

   public void setClubes(ArrayList<Club> clubes) {
        if (clubes != null) {
            this.clubes = clubes;
        }
    }
    //no agregar nulos ni duplicados
    public void agregarClub(Club club){
        if(club != null && club.getNombre() != null){
           boolean existe = false;
           for(Club c : clubes){
               if(c.getNombre().equalsIgnoreCase(club.getNombre())){
                   existe = true;
                   break;
               }
           }
           if(!existe){
               clubes.add(club);
           }
        }
        
    }

    public void borrarClub(Club club){
        if (club != null) {
            clubes.remove(club);
        }
    }
     // proteccion contra nulos
    public void modificarRival(Club rival){
        if(rival != null && rival.getNombre() != null){
            for(Club club: clubes){
                if(rival.getNombre().equalsIgnoreCase(club.getNombre())){
                    club.setPartidosJugados(rival.getPartidosJugados());
                }
            }
        }
    }
    
     //proteger conteo por si hay algun nulo
    public int getCantidad(){
        int contador = 0;
        for (Club club: clubes){
            if(club != null){
                contador++;
            }
            
        }
            
        return contador;
    }

}