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
        this.nombre = nombre;
    }
    public int getDorsal() {
        return dorsal;
    }
    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }
    public String getPosicion() {
        return posicion;
    }
    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }
    public int getRendimiento() {
        return rendimiento;
    }
    public void setRendimiento(int rendimiento) {
        this.rendimiento = rendimiento;
    }
    public double getSueldo() {
        return sueldo;
    }
    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }



}
