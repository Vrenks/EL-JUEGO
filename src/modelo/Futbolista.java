/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author vicen
 */
public class Futbolista {
    private String nombre;
    private int dorsal;
    private String posicion;
    private int rendimiento;
    private double sueldo;

    public Futbolista(String nombre, int dorsal, String posicion, int rendimiento, double sueldo){
        this.setNombre(nombre);
        this.setDorsal(dorsal);
        this.setPosicion(posicion);
        this.setRendimiento(rendimiento);
        this.setSueldo(sueldo);


    }


    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        if(nombre != null && !nombre.trim().isEmpty()){
           this.nombre = nombre; 
        }
        
    }
    public int getDorsal() {
        return dorsal;
    }
    public void setDorsal(int dorsal) {
        if(dorsal > 0){
            this.dorsal = dorsal;
        }
        
    }
    public String getPosicion() {
        return posicion;
    }
    public void setPosicion(String posicion) {
        if(posicion != null && !posicion.trim().isEmpty()){
            this.posicion = posicion;
        }
        
    }
    public int getRendimiento() {
        return rendimiento;
    }
    //validacion limites del rendimento del jugador de 1 a 10
    public void setRendimiento(int rendimiento) {
        if(rendimiento >= 1 && rendimiento <= 10){
           this.rendimiento = rendimiento;
        }else if(rendimiento > 10){
            this.rendimiento = 10;
        }else{
            this.rendimiento = 1;
        }
        
    }
    public double getSueldo() {
        return sueldo;
    }
    public void setSueldo(double sueldo) {
        if(sueldo >= 0){
            this.sueldo = sueldo;
        }
        
    }
 
}
