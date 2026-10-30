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
    private int PartidosGanados;
    private int aficion;
    private int ranking;
    private String dificultad;
    private int camisetasVendidas;
    private int entradasVendidas;
    private int CantidadPatrocinadores;
    private ArrayList<Futbolista> jugadores;

    public Club(String nombre, int PartidosGanados, int aficion, int ranking, String dificultad, int camisetasVendidas, int entradasVendidas, int CantidadPatrocinadores) {
        this.setNombre(nombre);
        this.setPartidosGanados(PartidosGanados);
        this.setAficion(aficion);
        this.setranking(ranking);
        this.setDificultad(dificultad);
        this.setCamisetasVendidas(camisetasVendidas);
        this.setEntradasVendidas(entradasVendidas);
        this.setCantidadPatrocinadores(CantidadPatrocinadores);
        this.jugadores = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPartidosGanados() {
        return PartidosGanados;
    }

    public void setPartidosGanados(int partidosGanados) {
        PartidosGanados = partidosGanados;
    }

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
        this.camisetasVendidas = camisetasVendidas;
    }

    public int getEntradasVendidas() {
        return entradasVendidas;
    }

    public void setEntradasVendidas(int entradasVendidas) {
        this.entradasVendidas = entradasVendidas;
    }

    public int getCantidadPatrocinadores() {
        return CantidadPatrocinadores;
    }

    public void setCantidadPatrocinadores(int cantidadPatrocinadores) {
        CantidadPatrocinadores = cantidadPatrocinadores;
    }

    public ArrayList<Futbolista> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Futbolista> jugadores) {
        this.jugadores = jugadores;
    }


    public void ficharJugador(Futbolista jugador) {
        jugadores.add(jugador);
    }






    








    



    
}
