/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author vicen
 */
public class Club {

    private String nombre;
    private int rendimiento;
    private int PartidosGanados;
    private int partidosJugados;
    //todavía no: private int aficion;
    // todavía no: private int ranking;
    private String dificultad;
    private int camisetasVendidas;
    private int entradasVendidas;
    private int CantidadPatrocinadores;
    private boolean enfrentado;
    private ArrayList<Futbolista> jugadores;

    public Club(String nombre, int rendimiento, int PartidosGanados, int partidosJugados, String dificultad, int camisetasVendidas, int entradasVendidas, int CantidadPatrocinadores) {
        this.setNombre(nombre);
        this.setRendimiento(rendimiento);
        this.setPartidosGanados(PartidosGanados);
        this.setPartidosJugados(partidosJugados);
        //this.setAficion(aficion);
        //this.setranking(ranking);

        this.setDificultad(dificultad);
        this.setCamisetasVendidas(camisetasVendidas);
        this.setEntradasVendidas(entradasVendidas);
        this.setCantidadPatrocinadores(CantidadPatrocinadores);
        this.jugadores = new ArrayList<>();
        this.enfrentado = false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if(nombre != null && !nombre.trim().isEmpty()){
            this.nombre = nombre;
        }
        
    }

    public int getRendimiento(){
        return rendimiento;
    }

    public void setRendimiento(int rendimiento){
        if(rendimiento  > 65){
        this.rendimiento = 65; //tope maximo del equipo
        }else if (rendimiento < 0){
            this.rendimiento = 0; // minimo permitido
        }else{
            this.rendimiento = rendimiento; //valor intermedio valido
        }
            
          
    }
        

    public int getPartidosGanados() {
        return PartidosGanados;
    }
    //partidos mayor que 0 y no superaran el totald e partidos
    public void setPartidosGanados(int partidosGanados) {
        if(partidosGanados >= 0 && partidosGanados <= this.partidosJugados){
            PartidosGanados = partidosGanados;
        }
        
    }

    public int getPartidosJugados() {
        return partidosJugados;
    }
     //paritidos mayor que 0 y no puede ser menor que los partidos ganados
    public void setPartidosJugados(int partidosJugados) {
        if(partidosJugados >= 0 && partidosJugados >= this.PartidosGanados){
        this.partidosJugados = partidosJugados;
        } 
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public int getCamisetasVendidas() {
        return camisetasVendidas;
    }

    public void setCamisetasVendidas(int camisetasVendidas) {
        if(camisetasVendidas >= 0){
        this.camisetasVendidas = camisetasVendidas;
        }  
    }

    public int getEntradasVendidas() {
        return entradasVendidas;
    }

    public void setEntradasVendidas(int entradasVendidas) {
        if(entradasVendidas >= 0){
        this.entradasVendidas = entradasVendidas;
        } 
    }

    public int getCantidadPatrocinadores() {
        return CantidadPatrocinadores;
    }

    public void setCantidadPatrocinadores(int cantidadPatrocinadores) {
        if(cantidadPatrocinadores >= 0){
        CantidadPatrocinadores = cantidadPatrocinadores;
        } 
    }

    public ArrayList<Futbolista> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Futbolista> jugadores) {
        this.jugadores = jugadores;
    }
    //evitar nulos y duplicados
    public void agregarJugador(Futbolista jugador){
        if(jugador != null && !jugadores.contains(jugador)){
           jugadores.add(jugador); 
           this.rendimiento = calcularRendimientoTotal(); 
        }
        
    }
     //verificar existencia
    public void despedirJugador(Futbolista jugador) {
        if(jugador != null && jugadores.contains(jugador)){
            jugadores.remove(jugador);
            this.rendimiento = calcularRendimientoTotal(); 
        }
        
    }

    public Futbolista buscarJugadorNombre(String nombre){
        for (Futbolista futbolista: jugadores){
            if (futbolista.getNombre().equals(nombre))
                return futbolista;     
        }
        return null;
    }

    public Futbolista buscarJugadorDorsal(int dorsal){
        for (Futbolista futbolista: jugadores){
            if (futbolista.getDorsal()==dorsal)
                return futbolista;     
        }
        return null;
    }

    public boolean isEnfrentado() {
        return enfrentado;
    }

    public void setEnfrentado(boolean enfrentado) {
        this.enfrentado = enfrentado;
    }
    // metodo para calcualr rendimiento
   public int calcularRendimientoTotal() {
    int suma = 0;
        for (Futbolista f : jugadores) {
            suma += f.getRendimiento();
        }
        return Math.min(suma, 70); // Limita el máximo a 70
    }
    
    public void ventasRendimiento(int diferenciaGoles, Club club, Empresario emp){
        int camisetas = diferenciaGoles*300;
        club.setCamisetasVendidas(club.getCamisetasVendidas()+camisetas);
        emp.setPuntuacion(emp.getPuntuacion()+camisetas*50);
        int entradas = diferenciaGoles * 600;
        club.setEntradasVendidas(club.getEntradasVendidas()+entradas);
        emp.setPuntuacion(emp.getPuntuacion()+entradas*10);
    }

    public void ventasMoralidad(Club club, Empresario emp){
        int camisetas = emp.getMoralidad()*100;
        club.setCamisetasVendidas(club.getCamisetasVendidas()+camisetas);
        emp.setPuntuacion(emp.getPuntuacion()+camisetas*50);
        int entradas = emp.getMoralidad() * 300;
        club.setEntradasVendidas(club.getEntradasVendidas()+entradas);
        emp.setPuntuacion(emp.getPuntuacion()+entradas*10);
    }

    public void opcionesFichaje(Club club, String nombreClub, ListaFichajes fichajes){
        fichajes.getFutbolistas().clear();
        Futbolista[] opciones;
        if (nombreClub.equals("Liverpool")){
            opciones = new Futbolista[]{
                new Futbolista("Florian Wirtz", 10, "Mediapunta", 7, 40000),
                new Futbolista("Victor Munoz", 11, "Extremo izquierdo", 10, 70000),
                new Futbolista("Dominik Szoboslai", 12, "Mediapunta", 6, 25000),
                new Futbolista("Hugo Ekitike", 13, "Delantero", 4, 9000)
            };
        }else if (nombreClub.equals("Inter")){
            opciones = new Futbolista[]{
                new Futbolista("Pio Esposito", 10, "Mediapunta", 6, 50000),
                new Futbolista("Carlos Augusto", 11, "Extremo izquierdo", 10, 90000),
                new Futbolista("Petar Sucic", 12, "Volante", 4, 30000),
                new Futbolista("Henrikh Mkhtitaryan", 13, "Volante", 8, 60000)
            };
        }else{
            opciones = new Futbolista[]{
                new Futbolista("Leandro Hernandez", 10, "Mediapunta", 4, 60000),
                new Futbolista("Francisco Marchant", 11, "Extremo izquierdo", 8, 100000),
                new Futbolista("Ivan Roman", 12, "Central derrecho", 6, 80000),
                new Futbolista("Matias Fernandez", 14, "Volante", 10, 120000)
            };
        }
        club.agregarJugador(opciones[0]);
        club.agregarJugador(opciones[1]);
        fichajes.agregarFutbolista(opciones[2]);
        fichajes.agregarFutbolista(opciones[3]);
        for (int dorsal = 15; dorsal < 25; dorsal++){
            fichajes.agregarFutbolista(generarJugadorMercado(club, nombreClub, dorsal, fichajes, 0));
        }
    }

    public void actualizarMercadoFichajes(String nombreClub, ListaFichajes fichajes, int cicloMercado){
        for (Futbolista jugador : fichajes.getFutbolistas()){
            Random aleatorio = new Random(nombreClub.hashCode() * 31L
                    + jugador.getNombre().hashCode() + jugador.getDorsal() + cicloMercado * 1009L);
            int nuevoRendimiento = 4 + aleatorio.nextInt(5);
            if (nuevoRendimiento == jugador.getRendimiento()){
                nuevoRendimiento = nuevoRendimiento == 8 ? 4 : nuevoRendimiento + 1;
            }
            jugador.setRendimiento(nuevoRendimiento);
        }
    }

    private Futbolista generarJugadorMercado(Club club, String nombreClub, int dorsal, ListaFichajes fichajes, int cicloMercado){
        String[] nombres;
        String[] apellidos;
        String[] posiciones = {
            "Arquero", "Central derecho", "Lateral izquierdo", "Lateral derecho",
            "Mediocentro defensivo", "Central izquierdo", "Extremo derecho",
            "Volante", "Delantero", "Mediapunta", "Extremo izquierdo"
        };
        if (nombreClub.equals("Liverpool")){
            nombres = new String[]{"Oliver", "Harry", "Jack", "George", "Thomas", "Charlie", "James", "Oscar"};
            apellidos = new String[]{"Smith", "Jones", "Taylor", "Brown", "Wilson", "Davies", "Evans", "Roberts"};
        }else if (nombreClub.equals("Inter")){
            nombres = new String[]{"Luca", "Matteo", "Marco", "Lorenzo", "Andrea", "Federico", "Nicolo", "Davide"};
            apellidos = new String[]{"Rossi", "Romano", "Moretti", "Conti", "Ricci", "Marino", "Greco", "Gallo"};
        }else{
            nombres = new String[]{"Benjamin", "Vicente", "Matias", "Diego", "Joaquin", "Maximiliano", "Lucas", "Cristobal"};
            apellidos = new String[]{"Gonzalez", "Muñoz", "Rojas", "Diaz", "Perez", "Soto", "Contreras", "Silva"};
        }

        Random aleatorio = new Random(nombreClub.hashCode() * 31L + dorsal + cicloMercado * 1009L);
        int inicioNombre = aleatorio.nextInt(nombres.length);
        int inicioApellido = aleatorio.nextInt(apellidos.length);
        String nombre = null;
        for (int intento = 0; intento < nombres.length * apellidos.length; intento++){
            String candidato = nombres[(inicioNombre + intento) % nombres.length] + " "
                    + apellidos[(inicioApellido + intento / nombres.length) % apellidos.length];
            if (club.buscarJugadorNombre(candidato) == null && fichajes.buscarFutbolista(candidato) == null){
                nombre = candidato;
                break;
            }
        }
        if (nombre == null){
            throw new IllegalStateException("No se pudo generar un nombre de jugador disponible para " + nombreClub);
        }

        int rendimiento = 4 + aleatorio.nextInt(5);
        double sueldo = rendimiento * 10000.0;
        String posicion = posiciones[aleatorio.nextInt(posiciones.length)];
        return new Futbolista(nombre, dorsal, posicion, rendimiento, sueldo);
    }

   
}

    






    








    



    

