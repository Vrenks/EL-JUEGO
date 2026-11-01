/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package moneyball;

import controlador.ControladorMB;
import modelo.Club;
import modelo.Empresario;
import vista.VisualizadorMB;

/**
 *
 * @author vicen
 */
public class MONEYBALL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        VisualizadorMB vista = new VisualizadorMB(); 
        Empresario emp = new Empresario("", 100000, 0);
        Club club = new Club("", 45, 0, 0, "", 0, 0, 0);
        ControladorMB c = new ControladorMB(vista, emp, club);
        java.awt.EventQueue.invokeLater(() -> vista.setVisible(true));
    }
    
}
