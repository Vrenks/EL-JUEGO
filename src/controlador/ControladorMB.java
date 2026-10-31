/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;


import modelo.Club;
import modelo.Empresario;
import modelo.Futbolista;
import vista.VisualizadorMB;

/**
 *
 * @author vicen
 */
public class ControladorMB {
    
    private VisualizadorMB vista;
    private Empresario emp; 
    
    
    public ControladorMB(VisualizadorMB vista, Empresario emp) {
        this.vista=vista;
        this.emp=emp;

        
        vista.getPanelInicio().getBtnIniciarPartida().addActionListener(e->crear(emp));
        vista.getPanelCreacion().getBtnCreacionAGestion().addActionListener(e->gestionar());
        
        vista.getPanelGestion().getBtnGestionASimulacion().addActionListener(e->simular());
        vista.getPanelGestion().getBtnGestionAModificacion().addActionListener(e->modificar());
        vista.getPanelGestion().getBtnGestionAOpciones().addActionListener(e->opciones());
        vista.getPanelGestion().getBtnGestionAInformacion().addActionListener(e->informacion());
        
        vista.getPanelSimulacion().getBtnSimulacionAGestion().addActionListener(e->gestionar());
        vista.getPanelSimulacion().getBtnSimularDeNuevo().addActionListener(e->simular());
        vista.getPanelSimulacion().getBtnSimulacionAPatrocinios().addActionListener(e->patrocinio());
        
        
        vista.getPanelModificacionJugadores().getBtnModificacionAGestion().addActionListener(e->gestionar());
        vista.getPanelModificacionJugadores().getBtnModificacionAFichaje().addActionListener(e->fichar());
        vista.getPanelModificacionJugadores().getBtnModificacionAEditarJugador().addActionListener(e->editar());
        vista.getPanelModificacionJugadores().getBtnModificacionACambiarJugador().addActionListener(e->cambiar());
        
        vista.getPanelOpciones().getBtnOpcionesAGestion().addActionListener(e->gestionar());
        vista.getPanelOpciones().getBtnGuardarYSalir().addActionListener(e->System.exit(0));
        vista.getPanelOpciones().getBtnSalirSinGuardar().addActionListener(e->System.exit(0));
       
        vista.getPanelInformacion().getBtnInformacionAGestion().addActionListener(e->gestionar());
        
        vista.getPanelPatrocinios().getBtnPatrociniosAGestion().addActionListener(e->gestionar());
        
        vista.getPanelFichaje().getBtnFichajeAModificacion().addActionListener(e->modificar());
        
        vista.getPanelEditarJugador().getBtnEditarJugadorAModificacion().addActionListener(e->modificar());
        
        vista.getPanelCambiarJugador().getBtnCambiarJugadorAModificacion().addActionListener(e->modificar());
        
        
    }
    
    public void crear(Empresario emp){
        vista.mostrarPantalla("CREACION");
        String nombreEmpresario = vista.getPanelCreacion().getTxtNombreEmpresario().getText();
        String dificultad = vista.getPanelCreacion().getBtnElegirDificultad().getSelectedItem().toString();
        emp.setNombre(nombreEmpresario);
        String nombreClub;
        if (dificultad.equals("Premier League (Facil)")){
            nombreClub="Liverpool";
        } else if (dificultad.equals("Serie A (Normal)")){
            nombreClub="Inter";
        }
        else{
            nombreClub="Colo-Colo";
        }
        Club c = new Club(nombreClub, 0, 0, dificultad, 0, 0, 0);
        clubBase(c, nombreClub);

    }

    public void clubBase(Club club, String nombreClub){
        String f1Nombre, f2Nombre, f3Nombre, f4Nombre, f5Nombre, f6Nombre, f7Nombre, f8Nombre, f9Nombre;

        if (nombreClub.equals("Liverpool")){
            f1Nombre = "Alison Becker";
            f2Nombre = "Jeremy Jacquet";
            f3Nombre = "Milos Kerkez";
            f4Nombre = "Ronald Araujo";
            f5Nombre = "Ryan Gravenberch";
            f6Nombre = "Virgil Van Dijk";
            f7Nombre = "Cody Gakpo";
            f8Nombre = "Alexis Mac Allister";
            f9Nombre = "Alexander Isak";
        } else if(nombreClub.equals("Inter")){
            f1Nombre = "Josep Martínez";
            f2Nombre = "Benjamin Pavard";
            f3Nombre = "Federico Dimarco";
            f4Nombre = "Djed Spence";
            f5Nombre = "Nicolo Barella";
            f6Nombre = "Alessandro Bastoni";
            f7Nombre = "Ange-Yoan Bonny";
            f8Nombre = "Hakan Calhanoglu";
            f9Nombre = "Lautaro Martinez";
        }else{
            f1Nombre = "Vozinha";
            f2Nombre = "Jonathan Villagra";
            f3Nombre = "Erick Wiemberg";
            f4Nombre = "Joaquin Sosa";
            f5Nombre = "Tomas Alarcon";
            f6Nombre = "Arturo Vidal";
            f7Nombre = "Lautaro Pastran";
            f8Nombre = "Alvaro Madrid";
            f9Nombre = "Joaquin Correa";
        }


        Futbolista f1 = new Futbolista(f1Nombre, 1, "Arquero", 5, 10000);
        Futbolista f2 = new Futbolista(f2Nombre, 2, "Central derecho", 5, 10000);
        Futbolista f3 = new Futbolista(f3Nombre, 3, "Lateral izquierdo", 5, 10000);
        Futbolista f4 = new Futbolista(f4Nombre, 4, "Lateral derecho", 5, 10000);
        Futbolista f5 = new Futbolista(f5Nombre, 5, "Mediocentro defensivo", 5, 10000);
        Futbolista f6 = new Futbolista(f6Nombre, 6, "Central izquierdo", 5, 10000);
        Futbolista f7 = new Futbolista(f7Nombre, 7, "Extremo derecho", 5, 10000);
        Futbolista f8 = new Futbolista(f8Nombre, 8, "Volante", 5, 10000);
        Futbolista f9 = new Futbolista(f9Nombre, 9, "Delantero", 5, 10000);
        
        club.agregarJugador(f1);
        club.agregarJugador(f2);
        club.agregarJugador(f3);
        club.agregarJugador(f4);
        club.agregarJugador(f5);
        club.agregarJugador(f6);
        club.agregarJugador(f7);
        club.agregarJugador(f8);
        club.agregarJugador(f9);
        
        club.setRendimiento(45);
    }
    
    public void gestionar(){
        vista.mostrarPantalla("GESTION");
    }
    
    public void informacion(){
        vista.mostrarPantalla("INFORMACION");
    }
    
    public void simular(){
        vista.mostrarPantalla("SIMULACION");
    }
    
    public void patrocinio(){
        vista.mostrarPantalla("PATROCINIOS");
    }
    
    public void modificar(){
        vista.mostrarPantalla("MODIFICAR JUGADORES");
    }
    
    public void opciones(){
        vista.mostrarPantalla("OPCIONES");
    }
    
    public void fichar(){
        vista.mostrarPantalla("FICHAJE");
    }
    
    public void editar(){
        vista.mostrarPantalla("EDITAR");
    }
    
    public void cambiar(){
        vista.mostrarPantalla("CAMBIAR JUGADOR");
    }
    
    
}
