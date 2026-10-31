/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;


import java.util.Random;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.Club;
import modelo.Empresario;
import modelo.Futbolista;
import modelo.Patrocinio;
import vista.VisualizadorMB;
import modelo.ListaPatrocinios;
import modelo.ListaClubes;

/**
 *
 * @author vicen
 */
public class ControladorMB {
    
    private VisualizadorMB vista;
    private Empresario emp; 
    private Club club;
    
    
    public ControladorMB(VisualizadorMB vista, Empresario emp, Club club) {
        this.vista=vista;
        this.emp=emp;
        this.club=club;
        ListaPatrocinios patrocinios = new ListaPatrocinios();
        ListaClubes clubes = new ListaClubes();
        
        
        vista.getPanelInicio().getBtnIniciarPartida().addActionListener(e->crear(emp, club, clubes, patrocinios));
        vista.getPanelCreacion().getBtnCreacionAGestion().addActionListener(e->gestionar());
        
        vista.getPanelGestion().getBtnGestionASimulacion().addActionListener(e->simular(0, patrocinios, club, clubes));
        vista.getPanelGestion().getBtnGestionAModificacion().addActionListener(e->modificar());
        vista.getPanelGestion().getBtnGestionAOpciones().addActionListener(e->opciones());
        vista.getPanelGestion().getBtnGestionAInformacion().addActionListener(e->informacion(club));
        
        vista.getPanelSimulacion().getBtnSimulacionAGestion().addActionListener(e->gestionar());
        vista.getPanelSimulacion().getBtnSimularDeNuevo().addActionListener(e->simular(0, patrocinios, club, clubes));
        //vista.getPanelSimulacion().getBtnSimulacionAPatrocinios().addActionListener(e->patrocinio(patrocinios, emp, club));
        
        
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
    
    public void crear(Empresario emp, Club club, ListaClubes clubes, ListaPatrocinios patrocinios){
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
        club.setNombre(nombreClub);
        club.setDificultad(nombreClub);
        clubBase(club, nombreClub);

        
        crearClubesYPatrocinios(clubes, nombreClub, patrocinios);
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

    }
    
    public void gestionar(){
        vista.mostrarPantalla("GESTION");
    }
    
    public void informacion(Club club){
        vista.mostrarPantalla("INFORMACION");
        DefaultTableModel tabla = (DefaultTableModel) vista.getPanelInformacion().getTablaInformacion().getModel();
        tabla.setRowCount(0);
        for(Futbolista futbolista: club.getJugadores()){
            tabla.addRow(new Object[]{futbolista.getNombre(), futbolista.getDorsal(), futbolista.getPosicion(), futbolista.getRendimiento(), futbolista.getSueldo()});
        }
    }
    
    public void simular(int partidosJugados, ListaPatrocinios patrocinios, Club club, ListaClubes clubes){
        


        vista.mostrarPantalla("SIMULACION");
        if (partidosJugados!=0 && partidosJugados%2==0)
            patrocinio(patrocinios, emp, club);
        club.setPartidosJugados(club.getPartidosJugados()+1);
    }
    
    public void patrocinio(ListaPatrocinios patrocinios, Empresario emp, Club club){
        Random rand = new Random();
        int numAleatorio1 = rand.nextInt(6);
        int numAleatorio2 = rand.nextInt(6);
        Patrocinio p1 = patrocinios.getPatrocinios().get(numAleatorio1);
        Patrocinio p2 = patrocinios.getPatrocinios().get(numAleatorio2);
        DefaultTableModel tabla = (DefaultTableModel) vista.getPanelPatrocinios().getTablaPatrocinadores().getModel();
        tabla.setRowCount(0);
        tabla.addRow(new Object[]{p1.getNombre(), p1.getMoralidad(), p1.getInversion()});
        tabla.addRow(new Object[]{p2.getNombre(), p2.getMoralidad(), p2.getInversion()});
        vista.mostrarPantalla("PATROCINIOS");
        int fila = vista.getPanelPatrocinios().getTablaPatrocinadores().getSelectedRow();
        String nombrePatrocinadorElegido = vista.getPanelPatrocinios().getTablaPatrocinadores().getValueAt(fila, 0).toString();
        int moralidadPatrocinadorElegido = (int) vista.getPanelPatrocinios().getTablaPatrocinadores().getValueAt(fila, 1);
        int inversionPatrocinadorElegido = (int) vista.getPanelPatrocinios().getTablaPatrocinadores().getValueAt(fila, 2);

        emp.setMoralidad(emp.getMoralidad()+moralidadPatrocinadorElegido);
        emp.setPuntuacion(inversionPatrocinadorElegido+emp.getPuntuacion());
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
    
    public void crearClubesYPatrocinios(ListaClubes clubes, String nombreClub, ListaPatrocinios patrocinios){
        Club c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15;
        if (nombreClub.equals("Liverpool")){
            c1= new Club("Arsenal", 62, 0, 0, "", 0, 0, 0); 
            c2= new Club("Manchester City", 60, 0, 0, "", 0, 0, 0); 
            c3= new Club("Aston Villa", 58, 0, 0,  "", 0, 0, 0); 
            c4= new Club("Chelsea", 57, 0, 0, "", 0, 0, 0);
            c5= new Club("Newcastle United", 56, 0, 0, "", 0, 0, 0);
            c6= new Club("Tottenham", 54, 0, 0, "", 0, 0, 0);
            c7= new Club("Manchester United", 53, 0, 0, "", 0, 0, 0); 
            c8= new Club("Crystal Palace", 52, 0, 0, "", 0, 0, 0); 
            c9= new Club("Everton", 52, 0, 0, "", 0, 0, 0); 
            c10= new Club("Brighton", 51, 0, 0, "", 0, 0, 0);
            c11= new Club("Fulham", 50, 0, 0, "", 0, 0, 0); 
            c12= new Club("Nottingham Forest", 48, 0, 0, "", 0, 0, 0); 
            c13= new Club("Coventry City", 47, 0, 0, "", 0, 0, 0); 
            c14= new Club("Bournemouth", 47, 0, 0, "", 0, 0, 0);  
            c15= new Club("Sunderland AFC", 45, 0, 0, "", 0, 0, 0);
        }else if(nombreClub.equals("Inter")){
            c1= new Club("Lazio", 62, 0, 0, "", 0, 0, 0); 
            c2= new Club("Juventus", 60, 0, 0, "", 0, 0, 0); 
            c3= new Club("Milan", 58, 0, 0, "", 0, 0, 0); 
            c4= new Club("Roma", 57, 0, 0, "", 0, 0, 0);
            c5= new Club("Como 1907", 56, 0, 0, "", 0, 0, 0);
            c6= new Club("Napoli", 56, 0, 0, "", 0, 0, 0);
            c7= new Club("Torino", 55, 0, 0, "", 0, 0, 0); 
            c8= new Club("Bolonia", 55, 0, 0, "", 0, 0, 0); 
            c9= new Club("Lecce", 52, 0, 0, "", 0, 0, 0); 
            c10= new Club("Atalanta", 51, 0, 0, "", 0, 0, 0);
            c11= new Club("Genoa", 50, 0, 0, "", 0, 0, 0); 
            c12= new Club("Cagliari", 50, 0, 0, "", 0, 0, 0); 
            c13= new Club("Venezia", 49, 0, 0, "", 0, 0, 0); 
            c14= new Club("Udinese", 49, 0, 0, "", 0, 0, 0);  
            c15= new Club("Fiorentina", 46, 0, 0, "", 0, 0, 0);
        }else{
            c1= new Club("U. de Chile", 64, 0, 0, "", 0, 0, 0); 
            c2= new Club("U. Catolica", 63, 0, 0, "", 0, 0, 0); 
            c3= new Club("Everton", 63, 0, 0, "", 0, 0, 0); 
            c4= new Club("Palestino", 61, 0, 0, "", 0, 0, 0);
            c5= new Club("Deportes Limache", 60, 0, 0, "", 0, 0, 0);
            c6= new Club("Coquimbo Unido", 60, 0, 0, "", 0, 0, 0);
            c7= new Club("Huachipato", 59, 0, 0, "", 0, 0, 0); 
            c8= new Club("La Serena", 57, 0, 0, "", 0, 0, 0); 
            c9= new Club("Deportes Concepcion", 57, 0, 0, "", 0, 0, 0); 
            c10= new Club("Audax Italiano", 56, 0, 0, "", 0, 0, 0);
            c11= new Club("O.Higgins", 55, 0, 0, "", 0, 0, 0); 
            c12= new Club("Cobresal", 54, 0, 0, "", 0, 0, 0); 
            c13= new Club("U. de Conce", 54, 0, 0, "", 0, 0, 0); 
            c14= new Club("Union La Calera", 53, 0, 0, "", 0, 0, 0);  
            c15= new Club("Ñublense", 51, 0, 0, "", 0, 0, 0);
        }

        clubes.agregarClub(c1);
        clubes.agregarClub(c2);
        clubes.agregarClub(c3);
        clubes.agregarClub(c4);
        clubes.agregarClub(c5);
        clubes.agregarClub(c6);
        clubes.agregarClub(c7);
        clubes.agregarClub(c8);
        clubes.agregarClub(c9);
        clubes.agregarClub(c10);
        clubes.agregarClub(c11);
        clubes.agregarClub(c12);
        clubes.agregarClub(c13);
        clubes.agregarClub(c14);
        clubes.agregarClub(c15);

        Patrocinio p1 = new Patrocinio("Apple", 3, 270000);
        Patrocinio p2 = new Patrocinio("UNICEF", 5, 80000);
        Patrocinio p3 = new Patrocinio("LATAM", 4, 150000);
        Patrocinio p4 = new Patrocinio("OpenAI", 2, 120000);
        Patrocinio p5 = new Patrocinio("Betano", 1, 200000);
        Patrocinio p6 = new Patrocinio("Tel Aviv Inver-sionistas", 0, 400000);
        
        patrocinios.agregarPatrocinio(p1);
        patrocinios.agregarPatrocinio(p2);
        patrocinios.agregarPatrocinio(p3);
        patrocinios.agregarPatrocinio(p4);
        patrocinios.agregarPatrocinio(p5);
        patrocinios.agregarPatrocinio(p6);

    }
    
}
