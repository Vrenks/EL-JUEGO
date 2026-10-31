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
    private int inversion;
    private int partidosRestantes;
    
    public Patrocinio(String nombre, int moralidad, int inversion, int partidosRestantes) {
        this.setNombre(nombre);
        this.setMoralidad(moralidad);
        this.setInversion(inversion);
        this.setPartidosRestantes(partidosRestantes);
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

    public int getInversion() {
        return inversion;
    }

    public void setInversion(int inversion) {
        this.inversion = inversion;
    }

    public int getPartidosRestantes() {
        return partidosRestantes;
    }

    public void setPartidosRestantes(int partidosRestantes) {
        this.partidosRestantes = partidosRestantes;
    }

    

    /*public void empezarPatrocinio() {
        
    }*/

    /*public void terminarPatrocinio() {
        // Lógica para terminar el patrocinio
    }*/


    
    


}
