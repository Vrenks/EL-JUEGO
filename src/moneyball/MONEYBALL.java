/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package moneyball;

import controlador.ControladorMB;
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
        // TODO code application logic here
        VisualizadorMB vista = new VisualizadorMB(); 
        java.awt.EventQueue.invokeLater(() -> vista.setVisible(true));
        ControladorMB c = new ControladorMB(vista);
        
    }
    
}
