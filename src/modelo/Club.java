/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

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
        if(partidosJugados >= 0 && partidosJugados <= this.PartidosGanados){
        this.partidosJugados = partidosJugados;
        } 
    }

    /* 
    public int getAficion() {
        return aficion;
    }

    public void setAficion(int aficion) {
        this.aficion = aficion;
    }

    public int getranking() {
        return ranking;
    }

    public void setranking(int ranking) {
        this.ranking = ranking;
    }
    */ 
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
           this.rendimiento = calcularRendimientoTotal(); //actualiza el atributo rendimiento
        }
        
    }
     //verificar existencia
    public void despedirJugador(Futbolista jugador) {
        if(jugador != null && jugadores.contains(jugador)){
            jugadores.remove(jugador);
            this.rendimiento = calcularRendimientoTotal(); //Actualiza el atributo rendimiento
        }
        
    }

    public Futbolista buscarJugador(String nombre){
        for (Futbolista futbolista: jugadores){
            if (futbolista.getNombre().equals(nombre))
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
    return Math.min(suma, 65); // Limita el máximo a 65
}
   
}

    






    








    



    

