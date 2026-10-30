/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/** 
 *
 * @author vicen
 */
public class Empresario {
    private String nombre;
    private int puntuacion;
    private int moralidad;

    public Empresario(String nombre, int puntuacion, int moralidad) {
        this.setNombre(nombre);
        this.setPuntuacion(puntuacion);
        this.setMoralidad(moralidad);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int getMoralidad() {
        return moralidad;
    }

    public void setMoralidad(int moralidad) {
        this.moralidad = moralidad;
    }
    
    public void elegirPatrocinio(){
        //pene
    }
    
    public void ficharJugador(){
        
    }
    
    
    
}
