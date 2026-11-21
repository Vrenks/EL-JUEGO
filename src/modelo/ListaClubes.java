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
        for (Club c: clubes){
            if (c.getNombre().equals(club.getNombre())){
                clubes.remove(club);
                return;
            }
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

    public void crearClubesYPatrocinios(ListaClubes clubes, ListaClubes clubesCopia, String nombreClub, ListaPatrocinios patrocinios){
        Club c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15;
        if (nombreClub.equals("Liverpool")){
            c1= new Club("Arsenal", 62, 0, 0, "", 0, 0, 0); 
            c2= new Club("Manchester City", 60, 0, 0, "", 0, 0, 0); 
            c3= new Club("Aston Villa", 58, 0, 0,  "", 0, 0, 0); 
            c4= new Club("Chelsea", 57, 0, 0, "", 0, 0, 0);
            c5= new Club("Newcastle United", 56, 0, 0, "", 0, 0, 0);
            c6= new Club("Tottenham", 54, 0, 0, "", 0, 0, 0);
            c7= new Club("Manchester United", 53, 0, 0, "", 0, 0, 0); 
            c8= new Club("Crystal Palace", 52, 0, 0, "", 0, 0, 0); 
            c9= new Club("Everton", 52, 0, 0, "", 0, 0, 0); 
            c10= new Club("Brighton", 51, 0, 0, "", 0, 0, 0);
            c11= new Club("Fulham", 50, 0, 0, "", 0, 0, 0); 
            c12= new Club("Nottingham Forest", 48, 0, 0, "", 0, 0, 0); 
            c13= new Club("Coventry City", 47, 0, 0, "", 0, 0, 0); 
            c14= new Club("Bournemouth", 47, 0, 0, "", 0, 0, 0);  
            c15= new Club("Sunderland AFC", 45, 0, 0, "", 0, 0, 0);
        }else if(nombreClub.equals("Inter")){
            c1= new Club("Lazio", 62, 0, 0, "", 0, 0, 0); 
            c2= new Club("Juventus", 60, 0, 0, "", 0, 0, 0); 
            c3= new Club("Milan", 58, 0, 0, "", 0, 0, 0); 
            c4= new Club("Roma", 57, 0, 0, "", 0, 0, 0);
            c5= new Club("Como 1907", 56, 0, 0, "", 0, 0, 0);
            c6= new Club("Napoli", 56, 0, 0, "", 0, 0, 0);
            c7= new Club("Torino", 55, 0, 0, "", 0, 0, 0); 
            c8= new Club("Bolonia", 55, 0, 0, "", 0, 0, 0); 
            c9= new Club("Lecce", 52, 0, 0, "", 0, 0, 0); 
            c10= new Club("Atalanta", 51, 0, 0, "", 0, 0, 0);
            c11= new Club("Genoa", 50, 0, 0, "", 0, 0, 0); 
            c12= new Club("Cagliari", 50, 0, 0, "", 0, 0, 0); 
            c13= new Club("Venezia", 49, 0, 0, "", 0, 0, 0); 
            c14= new Club("Udinese", 49, 0, 0, "", 0, 0, 0);  
            c15= new Club("Fiorentina", 46, 0, 0, "", 0, 0, 0);
        }else{
            c1= new Club("U. de Chile", 66, 0, 0, "", 0, 0, 0); 
            c2= new Club("U. Catolica", 64, 0, 0, "", 0, 0, 0); 
            c3= new Club("Everton", 63, 0, 0, "", 0, 0, 0); 
            c4= new Club("Palestino", 63, 0, 0, "", 0, 0, 0);
            c5= new Club("Deportes Limache", 62, 0, 0, "", 0, 0, 0);
            c6= new Club("Coquimbo Unido", 62, 0, 0, "", 0, 0, 0);
            c7= new Club("Huachipato", 60, 0, 0, "", 0, 0, 0); 
            c8= new Club("La Serena", 59, 0, 0, "", 0, 0, 0); 
            c9= new Club("Deportes Concepcion", 58, 0, 0, "", 0, 0, 0); 
            c10= new Club("Audax Italiano", 56, 0, 0, "", 0, 0, 0);
            c11= new Club("O.Higgins", 56, 0, 0, "", 0, 0, 0); 
            c12= new Club("Cobresal", 56, 0, 0, "", 0, 0, 0); 
            c13= new Club("U. de Conce", 55, 0, 0, "", 0, 0, 0); 
            c14= new Club("Union La Calera", 54, 0, 0, "", 0, 0, 0);  
            c15= new Club("Ñublense", 53, 0, 0, "", 0, 0, 0);
        }

        clubes.agregarClub(c1);
        clubes.agregarClub(c2);
        clubes.agregarClub(c3);
        clubes.agregarClub(c4);
        clubes.agregarClub(c5);
        clubes.agregarClub(c6);
        clubes.agregarClub(c7);
        clubes.agregarClub(c8);
        clubes.agregarClub(c9);
        clubes.agregarClub(c10);
        clubes.agregarClub(c11);
        clubes.agregarClub(c12);
        clubes.agregarClub(c13);
        clubes.agregarClub(c14);
        clubes.agregarClub(c15);

        Club cc1, cc2, cc3, cc4, cc5, cc6, cc7, cc8, cc9, cc10, cc11, cc12, cc13, cc14, cc15;
            cc1 = new Club(c1.getNombre(), c1.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc2 = new Club(c2.getNombre(), c2.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc3 = new Club(c3.getNombre(), c3.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc4 = new Club(c4.getNombre(), c4.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc5 = new Club(c5.getNombre(), c5.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc6 = new Club(c6.getNombre(), c6.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc7 = new Club(c7.getNombre(), c7.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc8 = new Club(c8.getNombre(), c8.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc9 = new Club(c9.getNombre(), c9.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc10 = new Club(c10.getNombre(), c10.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc11 = new Club(c11.getNombre(), c11.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc12 = new Club(c12.getNombre(), c12.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc13 = new Club(c13.getNombre(), c13.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc14 = new Club(c14.getNombre(), c14.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc15 = new Club(c15.getNombre(), c15.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
        
        clubesCopia.agregarClub(cc1);
        clubesCopia.agregarClub(cc2);
        clubesCopia.agregarClub(cc3);
        clubesCopia.agregarClub(cc4);
        clubesCopia.agregarClub(cc5);
        clubesCopia.agregarClub(cc6);
        clubesCopia.agregarClub(cc7);
        clubesCopia.agregarClub(cc8);
        clubesCopia.agregarClub(cc9);
        clubesCopia.agregarClub(cc10);
        clubesCopia.agregarClub(cc11);
        clubesCopia.agregarClub(cc12);
        clubesCopia.agregarClub(cc13);
        clubesCopia.agregarClub(cc14);
        clubesCopia.agregarClub(cc15);

        Patrocinio p1 = new Patrocinio("Apple", 0, 270000, 6);
        Patrocinio p2 = new Patrocinio("UNICEF", 4, 80000, 6);
        Patrocinio p3 = new Patrocinio("LATAM", 2, 150000, 6);
        Patrocinio p4 = new Patrocinio("OpenAI", -1, 120000, 6);
        Patrocinio p5 = new Patrocinio("Betano", -3, 200000, 6);
        Patrocinio p6 = new Patrocinio("Tel Aviv Inver-sionistas", -5, 400000, 6);
        
        patrocinios.agregarPatrocinio(p1);
        patrocinios.agregarPatrocinio(p2);
        patrocinios.agregarPatrocinio(p3);
        patrocinios.agregarPatrocinio(p4);
        patrocinios.agregarPatrocinio(p5);
        patrocinios.agregarPatrocinio(p6);

    }

}