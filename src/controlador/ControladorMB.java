/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;


import vista.VisualizadorMB;

/**
 *
 * @author vicen
 */
public class ControladorMB {
    
    private VisualizadorMB vista;
    
    
    public ControladorMB(VisualizadorMB vista) {
        this.vista=vista;
        
        vista.getPanelInicio().getBtnIniciarPartida().addActionListener(e->crear());
        vista.getPanelCreacion().getBtnCreacionAGestion().addActionListener(e->gestionar());
        
        vista.getPanelGestion().getBtnGestionACreacion().addActionListener(e->crear());
        vista.getPanelGestion().getBtnGestionASimulacion().addActionListener(e->simular());
        vista.getPanelGestion().getBtnGestionAModificacion().addActionListener(e->modificar());
        vista.getPanelGestion().getBtnGestionAOpciones().addActionListener(e->opciones());
        vista.getPanelGestion().getBtnGestionAResolucion().addActionListener(e->resolucionar());
        
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
       
        vista.getPanelResolucion().getBtnResolucionAGestion().addActionListener(e->gestionar());
        
        vista.getPanelPatrocinios().getBtnPatrociniosAGestion().addActionListener(e->gestionar());
        
        vista.getPanelFichaje().getBtnFichajeAModificacion().addActionListener(e->modificar());
        
        vista.getPanelEditarJugador().getBtnEditarJugadorAModificacion().addActionListener(e->modificar());
        
        vista.getPanelCambiarJugador().getBtnCambiarJugadorAModificacion().addActionListener(e->modificar());
        
        
    }
    
    public void crear(){
        vista.mostrarPantalla("CREACION");
    }
    
    public void gestionar(){
        vista.mostrarPantalla("GESTION");
    }
    
    public void resolucionar(){
        vista.mostrarPantalla("PROBLEMAS");
    }
    
    public void simular(){
        vista.mostrarPantalla("SIMULACION");
    }
    
    public void patrocinio(){
        vista.mostrarPantalla("PATROCINIOS");
    }
    
    public void listar(){
        vista.mostrarPantalla("LISTAS INFORMACION");
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
