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
    private boolean asociado;
    
    public Patrocinio(String nombre, int moralidad, int inversion, int partidosRestantes) {
        this.setNombre(nombre);
        this.setMoralidad(moralidad);
        this.setInversion(inversion);
        this.setPartidosRestantes(partidosRestantes);
        this.setAsociado(false);
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre.trim();
        }
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
        // Validación: La inversión económica no puede ser un número negativo
        if (inversion >= 0) {
            this.inversion = inversion;
        } else {
            this.inversion = 0;
        }
    }

    public int getPartidosRestantes() {
        return partidosRestantes;
    }

    public void setPartidosRestantes(int partidosRestantes) {
        // Validación: Los partidos restantes del contrato no pueden bajar de 0
        if (partidosRestantes >= 0) {
            this.partidosRestantes = partidosRestantes;
        } else {
            this.partidosRestantes = 0;
        }
    }

    public boolean isAsociado() {
        return asociado;
    }

    public void setAsociado(boolean asociado) {
        this.asociado = asociado;
    }

    

    /*public void empezarPatrocinio() {
        
    }*/

    /*public void terminarPatrocinio() {
        // Lógica para terminar el patrocinio
    }*/


    
    


}
