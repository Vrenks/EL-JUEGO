/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author vicen
 */
public class Patrocinio {
    private String nombre;
    private int moralidad;
    private double inversion;
    
    public Patrocinio(String nombre, int moralidad, double inversion) {
        this.setNombre(nombre);
        this.setMoralidad(moralidad);
        this.setInversion(inversion);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getMoralidad() {
        return moralidad;
    }

    public void setMoralidad(int moralidad) {
        this.moralidad = moralidad;
    }

    public double getInversion() {
        return inversion;
    }

    public void setInversion(double inversion) {
        this.inversion = inversion;
    }

    public void empezarPatrocinio() {
        // Lógica para empezar el patrocinio
    }

    public void terminarPatrocinio() {
        // Lógica para terminar el patrocinio
    }


    
    


}
