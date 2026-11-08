/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.table.DefaultTableModel;
import modelo.Club;
import modelo.Empresario;
import modelo.Futbolista;
import modelo.ListaClubes;
import modelo.ListaFichajes;
import modelo.ListaPatrocinios;
import modelo.Patrocinio;
import vista.VisualizadorMB;

/**
 *
 * @author vicen
 */
public class ControladorMB {
    
    private static final int MAX_JUGADORES_PLANTILLA = 11;
    private VisualizadorMB vista;
    private Empresario emp; 
    private Club club;
    
    
    public ControladorMB(VisualizadorMB vista, Empresario emp, Club club) {
        this.vista=vista;
        this.emp=emp;
        this.club=club;
        ListaPatrocinios patrocinios = new ListaPatrocinios();
        ListaClubes clubes = new ListaClubes();
        ListaFichajes fichajes = new ListaFichajes();
        ListaClubes clubesCopia = new ListaClubes();
        
        vista.getPanelInicio().getBtnContinuarPartida().addActionListener(e->{
            continuarPartida(emp, club, clubes, clubesCopia, patrocinios, fichajes, "memoryCard.txt");
        });
        vista.getPanelInicio().getBtnIniciarPartida().addActionListener(e->vista.mostrarPantalla("CREACION"));
        vista.getPanelCreacion().getBtnCreacionAGestion().addActionListener(e->{crear(emp, club, clubes, clubesCopia, patrocinios, fichajes);
                                                                                gestionar(emp);});

        
        vista.getPanelGestion().getBtnGestionASimulacion().addActionListener(e->simular(patrocinios, fichajes, club, clubes, clubesCopia, emp));
        vista.getPanelGestion().getBtnGestionAModificacion().addActionListener(e->modificar(emp));
        vista.getPanelGestion().getBtnGestionAOpciones().addActionListener(e->opciones());
        vista.getPanelGestion().getBtnGestionAInformacion().addActionListener(e->informacion(club, emp));
        vista.getPanelGestion().getBtnRivales().addActionListener(e->informacionRivales(clubes, emp));

        vista.getInformacionRivales().getBtnVolver().addActionListener(e->gestionar(emp));
        
        vista.getPanelSimulacion().getBtnSimulacionAGestion().addActionListener(e->gestionar(emp));
        vista.getPanelSimulacion().getBtnSimularDeNuevo().addActionListener(e->simular(patrocinios, fichajes, club, clubes, clubesCopia, emp));
        
        
        
        vista.getPanelModificacionJugadores().getBtnModificacionAGestion().addActionListener(e->gestionar(emp));
        vista.getPanelModificacionJugadores().getBtnModificacionAFichaje().addActionListener(e->ficharJugador(fichajes, emp));
        vista.getPanelModificacionJugadores().getBtnModificacionACambiarJugador().addActionListener(e->cambiar(emp));
        vista.getPanelFichaje().getBtnFichar().addActionListener(e->ficharJugador(fichajes, emp));
        vista.getPanelCambiarJugador().getBtnCambiar().addActionListener(e->despedirJugador());
        
        vista.getPanelOpciones().getBtnOpcionesAGestion().addActionListener(e->gestionar(emp));
        vista.getPanelOpciones().getBtnGuardarYSalir().addActionListener(e->{try {guardarPartida(emp, club, clubes, patrocinios, fichajes, "memoryCard.txt");
                                                                                  System.exit(0);
            } catch (IOException e1) {
            e1.printStackTrace();
            }
        });
           
        vista.getPanelOpciones().getBtnSalirSinGuardar().addActionListener(e->System.exit(0));
       
        vista.getPanelInformacion().getBtnInformacionAGestion().addActionListener(e->gestionar(emp));
        
        vista.getPanelFichaje().getBtnFichajeAModificacion().addActionListener(e->modificar(emp));
        
        vista.getPanelCambiarJugador().getBtnCambiarJugadorAModificacion().addActionListener(e->modificar(emp));

        vista.getPanelDerrota().getBtnVolverAlInicio().addActionListener(e->vista.mostrarPantalla("INICIO"));
        vista.getPanelDerrota().getBtnSalir().addActionListener(e->System.exit(0));

        vista.getPanelVictoria().getBtnVolverAlInicio().addActionListener(e->vista.mostrarPantalla("INICIO"));
        vista.getPanelVictoria().getBtnSalir().addActionListener(e->System.exit(0));

        
        
    }

    public void continuarPartida(Empresario emp, Club club, ListaClubes clubes, ListaClubes clubesCopia, ListaPatrocinios patrocinios, ListaFichajes fichajes, String nombreArchivo){
        try {
            cargarArchivo(emp, club, clubes, clubesCopia, patrocinios, fichajes, nombreArchivo);
            gestionar(emp);
        } catch (Exception e) {
            vista.mostrarError(e.getMessage() != null ? e.getMessage() : e.toString(), "ERROR DE CARGA");
        }
        
    }

    public void cargarArchivo(Empresario emp, Club club, ListaClubes clubes, ListaClubes clubesCopia, ListaPatrocinios patrocinios, ListaFichajes fichajes, String nombreArchivo) throws IOException{
        File f = new File(nombreArchivo);
        if (!f.exists())
            throw new IllegalArgumentException("ERROR: archivo '"+nombreArchivo+"' no encontrado o no se pudo abrir.");
        try(BufferedReader br = new BufferedReader(new FileReader(f))){
            String [] linea1 = br.readLine().split(";"); 
            String [] linea2 = br.readLine().split(";"); 
            String [] linea3 = br.readLine().split(";"); 
            String [] linea4 = br.readLine().split(";"); 
            String [] linea5 = br.readLine().split(";"); 
            String [] linea6 = br.readLine().split(";"); 
            String [] linea7 = br.readLine().split(";"); 
            String linea8 = br.readLine();
            String linea9 = br.readLine();

            String nombreEmpresario = linea1[0];
            String nombreClub = linea2[0];
            int rendimiento = Integer.parseInt(linea2[1]);
            int partidosGanados = Integer.parseInt(linea2[2]);
            int partidosJugados = Integer.parseInt(linea2[3]);
            int camisetasVendidas = Integer.parseInt(linea2[4]);
            int entradasVendidas = Integer.parseInt(linea2[5]);
            int cantidadPatrocinadores = Integer.parseInt(linea2[6]);
            int dorsales[] = new int[linea3.length];
            for (int i = 0; i<linea3.length; i++){
                dorsales[i]=Integer.parseInt(linea3[i]);
            }
            String nombrePatrocinadores[] = new String[linea4.length/3];
            int partidosRestantes[] = new int[linea4.length/3];
            boolean asociacion[] = new boolean[linea4.length/3];
            for (int i = 0; i<linea4.length; i+=3){
                int j = i/3;
                nombrePatrocinadores[j]=linea4[i];
                partidosRestantes[j]=Integer.parseInt(linea4[i+1]);
                asociacion[j]=Boolean.parseBoolean(linea4[i+2]);
            }
            int moralidad = Integer.parseInt(linea5[0]);
            int puntuacion = Integer.parseInt(linea6[0]);
            int rivalesJugados[]= new int[15];
            for (int i = 0; i<15; i++){
                rivalesJugados[i]=Integer.parseInt(linea7[i]);
            }
            
            clubBase(club, nombreClub);
            opcionesFichaje(club, nombreClub, fichajes);
            crearClubesYPatrocinios(clubes, clubesCopia, nombreClub, patrocinios);
            emp.setNombre(nombreEmpresario);
            emp.setPuntuacion(puntuacion);
            emp.setMoralidad(moralidad);
            club.setNombre(nombreClub);
            club.setRendimiento(rendimiento);
            club.setPartidosJugados(partidosJugados);
            club.setPartidosGanados(partidosGanados);
            club.setCamisetasVendidas(camisetasVendidas);
            club.setEntradasVendidas(entradasVendidas);
            club.setCantidadPatrocinadores(cantidadPatrocinadores);
            club.setDificultad(nombreClub);
            ArrayList<Futbolista> titulares = new ArrayList<>(club.getJugadores());
            club.getJugadores().clear();
            if (linea8 != null && !linea8.isEmpty()){
                for (String registro : linea8.split(";")){
                    String[] datos = registro.split("\\|", -1);
                    Futbolista jugador = new Futbolista(datos[0], Integer.parseInt(datos[1]),
                            datos[2], Integer.parseInt(datos[3]), Double.parseDouble(datos[4]));
                    club.agregarJugador(jugador);
                    fichajes.borrarFutbolista(jugador);
                }
            }else{
                for (int dorsal : dorsales){
                    Futbolista jugador = null;
                    for (Futbolista t : titulares){
                        if (t.getDorsal() == dorsal){
                            jugador = t;
                            break;
                        }
                    }
                    if (jugador == null){
                        jugador = fichajes.buscarJugadorDorsal(dorsal);
                        if (jugador != null)
                            fichajes.borrarFutbolista(jugador);
                    }
                    if (jugador != null)
                        club.agregarJugador(jugador);
                }
            }
            club.setRendimiento(rendimiento);
            if (linea9 != null && !linea9.isEmpty()){
                fichajes.getFutbolistas().clear();
                for (String registro : linea9.split(";")){
                    String[] datos = registro.split("\\|", -1);
                    fichajes.agregarFutbolista(new Futbolista(datos[0], Integer.parseInt(datos[1]),
                            datos[2], Integer.parseInt(datos[3]), Double.parseDouble(datos[4])));
                }
            }else{
                for (int ciclo = 1; ciclo <= partidosJugados / 3; ciclo++){
                    actualizarMercadoFichajes(nombreClub, fichajes, ciclo);
                }
            }

            for (Patrocinio p : new ArrayList<>(patrocinios.getPatrocinios())){
                int indice = -1;
                for (int i = 0; i < nombrePatrocinadores.length; i++){
                    if (p.getNombre().equalsIgnoreCase(nombrePatrocinadores[i])){
                        indice = i;
                        break;
                    }
                }
                if (indice == -1 || partidosRestantes[indice] <= 0){
                    patrocinios.borrarPatrocinio(p);
                }else{
                    p.setPartidosRestantes(partidosRestantes[indice]);
                    p.setAsociado(asociacion[indice]);
                }
            }

            for (int i = 0; i<15; i++){
                Club temp = clubes.getClubes().get(i);
                int jugados = rivalesJugados[i];
                temp.setPartidosJugados(jugados);
                if (jugados == 1){
                    for (int j = 0; j < clubesCopia.getClubes().size(); j++){
                        Club copia = clubesCopia.getClubes().get(j);
                        if (copia.getNombre().equalsIgnoreCase(temp.getNombre())){
                            clubesCopia.borrarClub(copia);
                            break;
                        }
                    }
                }
            }
        }
    }
    
    
    public void crear(Empresario emp, Club club, ListaClubes clubes, ListaClubes clubesCopia, ListaPatrocinios patrocinios, ListaFichajes fichajes){
        String nombreEmpresario = vista.getPanelCreacion().getTxtNombreEmpresario().getText().trim();
        //validacion para que el nombre no este vacio
        if(nombreEmpresario.isEmpty()){
            vista.mostrarError("El nombre del empresario no puede estar vacio", "Error al crear");
            return;
        }
        String dificultad = vista.getPanelCreacion().getBtnElegirDificultad();
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
        crearClubesYPatrocinios(clubes, clubesCopia, nombreClub, patrocinios);
        opcionesFichaje(club, nombreClub, fichajes);
        //transicion exitosa
        gestionar(emp);
    }
    

    //a futuro, pasar esta funcion a Club.java
    public void clubBase(Club club, String nombreClub){
        club.getJugadores().clear();
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


        Futbolista f1 = new Futbolista(f1Nombre, 1, "Arquero", 6, 10000);
        Futbolista f2 = new Futbolista(f2Nombre, 2, "Central derecho", 6, 10000);
        Futbolista f3 = new Futbolista(f3Nombre, 3, "Lateral izquierdo", 6, 10000);
        Futbolista f4 = new Futbolista(f4Nombre, 4, "Lateral derecho", 6, 10000);
        Futbolista f5 = new Futbolista(f5Nombre, 5, "Mediocentro defensivo", 6, 10000);
        Futbolista f6 = new Futbolista(f6Nombre, 6, "Central izquierdo", 6, 10000);
        Futbolista f7 = new Futbolista(f7Nombre, 7, "Extremo derecho", 6, 10000);
        Futbolista f8 = new Futbolista(f8Nombre, 8, "Volante", 6, 10000);
        Futbolista f9 = new Futbolista(f9Nombre, 9, "Delantero", 6, 10000);
        
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
    
    public void gestionar(Empresario emp){
        revisarDerrota(emp);
        vista.getPanelGestion().getLblPuntuacion().setText(String.valueOf(emp.getPuntuacion()));
        vista.mostrarPantalla("GESTION");
    }
    
    public void informacion(Club club, Empresario emp){
        revisarDerrota(emp);
        vista.getPanelInformacion().getLblPuntuacion().setText(String.valueOf(emp.getPuntuacion()));
        vista.mostrarPantalla("INFORMACION");
        DefaultTableModel tabla = (DefaultTableModel) vista.getPanelInformacion().getTablaInformacion().getModel();
        tabla.setRowCount(0);
        for(Futbolista futbolista: club.getJugadores()){
            tabla.addRow(new Object[]{futbolista.getNombre(), futbolista.getDorsal(), futbolista.getPosicion(), futbolista.getRendimiento(), futbolista.getSueldo()});
        }
    }
    
    public void simular(ListaPatrocinios patrocinios, ListaFichajes fichajes, Club club, ListaClubes clubes, ListaClubes clubesCopia, Empresario emp){
        // si la puntuacion es menor o igual a 0 se bloquea la simulacion
        revisarDerrota(emp);
        revisarVictoria(club, emp);
  
        Club rival;
        do{
            Random rivalRand = new Random();
            rival = clubesCopia.getClubes().get(rivalRand.nextInt(clubesCopia.getCantidad()));
        }while (rival.isEnfrentado());

        Random rand = new Random();
        String resultado;
        int diferenciaRendimiento, diferenciaGoles;
        if (rival.getRendimiento()<club.getRendimiento()){
            diferenciaRendimiento=club.getRendimiento()-rival.getRendimiento();
            diferenciaGoles = rand.nextInt(diferenciaRendimiento)+1;
            resultado = club.getNombre()+": "+ diferenciaGoles + "-0";
            club.setPartidosGanados(club.getPartidosGanados()+1);
            ventasRendimiento(diferenciaGoles, club, emp);

        }else if (rival.getRendimiento()>club.getRendimiento()){
            diferenciaRendimiento=rival.getRendimiento()-club.getRendimiento();
            diferenciaGoles = rand.nextInt(diferenciaRendimiento)+1;
            resultado = rival.getNombre()+": "+ "0-"+ diferenciaGoles;
            ventasRendimiento(diferenciaGoles*-1, club, emp);
        }
        else{ 
            diferenciaGoles = 0;
            resultado = "Empate: 0-0";
        }

        rival.setPartidosJugados(rival.getPartidosJugados()+1);
        clubes.modificarRival(rival);
        clubesCopia.borrarClub(rival);

        DefaultTableModel tabla = (DefaultTableModel) vista.getPanelSimulacion().getTablaPartido().getModel();
        tabla.addRow(new Object[]{club.getPartidosJugados()+1, rival.getNombre(), resultado});
        vista.getPanelSimulacion().getLblPuntuacion().setText(String.valueOf(emp.getPuntuacion()));
        vista.mostrarPantalla("SIMULACION");
        club.setPartidosJugados(club.getPartidosJugados()+1);
        if (club.getPartidosJugados() % 3 == 0){
            actualizarMercadoFichajes(club.getNombre(), fichajes, club.getPartidosJugados() / 3);
            vista.mostrarInformacion("El rendimiento de los jugadores disponibles en el mercado cambió.",
                    "Mercado de fichajes");
        }
        for (Patrocinio patrocinio : new ArrayList<>(patrocinios.getPatrocinios())){
            if (patrocinio.isAsociado()){
                patrocinio.setPartidosRestantes(patrocinio.getPartidosRestantes()-1);
                if (patrocinio.getPartidosRestantes()<=0){
                    club.setCantidadPatrocinadores(club.getCantidadPatrocinadores()-1);
                    emp.setPuntuacion(emp.getPuntuacion()-(patrocinio.getInversion()/2));
                    vista.mostrarInformacion("El patrocinador '"+patrocinio.getNombre()+"' ha abandonado el equipo. Fue compensado con $"+patrocinio.getInversion()/2, "INFORMACION IMPORTANTE");
                    patrocinios.borrarPatrocinio(patrocinio);
                }
            }
        }
        if (club.getPartidosJugados()!=0 && club.getPartidosJugados()%3==0){
            int eleccion = vista.eleccion("Patrocinador(es) interesados. Presione YES para revisarlo(s) o NO para seguir en la simulacion.", "INFORMACION PATROCINIOS");
            if (eleccion!=0)
                return;
            patrocinar(patrocinios, emp, club);
            ventasMoralidad(club, emp);
        }
        if (club.getPartidosJugados()!=0&&club.getPartidosJugados()%4==0){
            int sueldos = 0;
            for (Futbolista futbolista: club.getJugadores()){
                sueldos+=futbolista.getSueldo();
            }
            vista.mostrarInformacion("Sueldos cobrados: $"+sueldos, "INFORMACION IMPORTANTE");
        }
        vista.getPanelSimulacion().getLblPuntuacion().setText(String.valueOf(emp.getPuntuacion()));
        revisarDerrota(emp);
        revisarVictoria(club, emp);
    }

    public void ListarJugadores(Club club){
        DefaultTableModel tablaModificar = (DefaultTableModel) vista.getPanelModificacionJugadores().getTablaJugadores().getModel();
        DefaultTableModel tablaDespedir = (DefaultTableModel) vista.getPanelCambiarJugador().getTablaCambiar().getModel();
        llenarTablaJugadores(tablaModificar, club);
        llenarTablaJugadores(tablaDespedir, club);
    }

    private void llenarTablaJugadores(DefaultTableModel tabla, Club club){
        tabla.setRowCount(0);
        for(Futbolista futbolista: club.getJugadores()){
            tabla.addRow(new Object[]{futbolista.getNombre(), futbolista.getDorsal(), futbolista.getPosicion(), futbolista.getRendimiento(), futbolista.getSueldo()});
        }
    }
    
    public void patrocinar(ListaPatrocinios patrocinios, Empresario emp, Club club){
        revisarDerrota(emp);
        vista.getPanelPatrocinios().getLblPuntuacion2().setText(String.valueOf(emp.getPuntuacion()));
        Random rand = new Random();
        int numAleatorio1 = 0;
        int numAleatorio2 = 0;
        boolean condicion = true;
        while (condicion){
            numAleatorio1 = rand.nextInt(patrocinios.getPatrocinios().size());
            numAleatorio2 = rand.nextInt(patrocinios.getPatrocinios().size());
            if (!patrocinios.getPatrocinios().get(numAleatorio1).isAsociado()||!patrocinios.getPatrocinios().get(numAleatorio2).isAsociado())
                break;
        }
        Patrocinio p1;
        Patrocinio p2;
        DefaultTableModel tabla;
        if (numAleatorio1==numAleatorio2){
            p1 = patrocinios.getPatrocinios().get(numAleatorio1);
            tabla = (DefaultTableModel) vista.getPanelPatrocinios().getTablaPatrocinadores().getModel();
            tabla.setRowCount(0);
            tabla.addRow(new Object[]{p1.getNombre(), p1.getInversion(), p1.getMoralidad()});
        }else {
            p1 = patrocinios.getPatrocinios().get(numAleatorio1);
            p2 = patrocinios.getPatrocinios().get(numAleatorio2);
            tabla = (DefaultTableModel) vista.getPanelPatrocinios().getTablaPatrocinadores().getModel();
            tabla.setRowCount(0);
            tabla.addRow(new Object[]{p1.getNombre(), p1.getInversion(), p1.getMoralidad()});
            tabla.addRow(new Object[]{p2.getNombre(), p2.getInversion(), p2.getMoralidad()});
        }
        vista.getPanelPatrocinios().getBtnElegirPatrocinador().addActionListener(e->{
            int fila = vista.getPanelPatrocinios().getTablaPatrocinadores().getSelectedRow();
            //validacion para ver que se haya seleccionado una fila
            if(fila == -1){
                vista.mostrarInformacion("Debe seleccionar un patrocinador de la lista","Patrocinios");
            }
            if (fila!=-1){
                String nombrePatrocinadorElegido = vista.getPanelPatrocinios().getTablaPatrocinadores().getValueAt(fila, 0).toString();
                Patrocinio patrocinio=patrocinios.buscarPatrocinio(nombrePatrocinadorElegido);
                patrocinio.setAsociado(true);
                int moralidadPatrocinadorElegido = (int) vista.getPanelPatrocinios().getTablaPatrocinadores().getValueAt(fila, 2);
                int inversionPatrocinadorElegido = (int) vista.getPanelPatrocinios().getTablaPatrocinadores().getValueAt(fila, 1);
                emp.setMoralidad(emp.getMoralidad()+moralidadPatrocinadorElegido);
                emp.setPuntuacion(inversionPatrocinadorElegido+emp.getPuntuacion());
                club.setCantidadPatrocinadores(club.getCantidadPatrocinadores()+1);
                vista.mostrarPantalla("SIMULACION");
            }  
        });
        revisarDerrota(emp);
        vista.getPanelPatrocinios().getLblPuntuacion2().setText(String.valueOf(emp.getPuntuacion()));;
        vista.mostrarPantalla("PATROCINIOS");
    }
    
    public void modificar(Empresario emp){
        revisarDerrota(emp);
        ListarJugadores(club);
        vista.getPanelModificacionJugadores().getLblPuntuacion().setText(String.valueOf(emp.getPuntuacion()));
        vista.mostrarPantalla("MODIFICAR CLUB");
    }
    
    public void opciones(){
        revisarDerrota(emp);
        vista.getPanelOpciones().getLblPuntuacion2().setText(String.valueOf(emp.getPuntuacion()));
        vista.mostrarPantalla("OPCIONES");
    }
    
    public void cambiar(Empresario emp){
        ListarJugadores(club);
        vista.mostrarPantalla("CAMBIAR JUGADOR");

    }

    public void limpiarTabla(DefaultTableModel tabla){
        tabla.setRowCount(0);
    }

    public void listarFichajes(ListaFichajes fichajes){
        DefaultTableModel tabla = (DefaultTableModel) vista.getPanelFichaje().getListaFichajes().getModel();
        limpiarTabla(tabla);
        for (Futbolista futbolista : fichajes.getFutbolistas()) {
            Object[] fila = {futbolista.getNombre(), futbolista.getDorsal(), futbolista.getPosicion(), 
                             futbolista.getRendimiento(), futbolista.getSueldo()};
            tabla.addRow(fila);
        }
        
    }


    public void ficharJugador(ListaFichajes fichajes, Empresario emp){
        revisarDerrota(emp);
        vista.getPanelFichaje().getLblPuntuacion2().setText(String.valueOf(emp.getPuntuacion()));
        vista.mostrarPantalla("FICHAJE");
        listarFichajes(fichajes);
        DefaultTableModel tabla = (DefaultTableModel) vista.getPanelFichaje().getListaFichajes().getModel();
        // limpia listeners anteriores
        for (java.awt.event.ActionListener al : vista.getPanelFichaje().getBtnFichar().getActionListeners()) {
        vista.getPanelFichaje().getBtnFichar().removeActionListener(al);
        }
        vista.getPanelFichaje().getBtnFichar().addActionListener(e->{ //INICIO LISTENER (TERMINADO EN LINEA 365)
        if (club.getJugadores().size() >= MAX_JUGADORES_PLANTILLA){
            vista.mostrarError("La plantilla ya tiene 11 jugadores. Despide a uno antes de fichar.","Limite de equipo");
            return;
        }
        int fila = vista.getPanelFichaje().getFilaSeleccionada();
        if (fila == -1){
            vista.mostrarInformacion("Selecciona un jugador disponible para fichar.", "Fichaje");
            return;
        }
        Object[] datos = new Object[tabla.getColumnCount()];
        for (int columna = 0; columna < datos.length; columna++){
            datos[columna] = tabla.getValueAt(fila, columna);
            if (datos[columna] == null){
                vista.mostrarError("Los datos del jugador seleccionado están incompletos.", "Error de fichaje");
                return;
            }
        }
        Futbolista jugador = new Futbolista(datos[0].toString(),((Number) datos[1]).intValue(),
                                            datos[2].toString(),((Number) datos[3]).intValue(),((Number) datos[4]).doubleValue());
        if (club.buscarJugadorNombre(jugador.getNombre()) != null){
            vista.mostrarInformacion("Ese jugador ya forma parte del club.", "Fichaje");
            return;
        }
        //validacion dorsal duplicado
        for(Futbolista f : club.getJugadores()){
            if(f.getDorsal() == jugador.getDorsal()){
                vista.mostrarError("El dorsal # "+ jugador.getDorsal()+"ya esta ocupado","Dorsal en uso" );
                return;
            }
        }
           
        //validacion fondos insuficientes
        if(emp.getPuntuacion() < jugador.getSueldo()){
            vista.mostrarError("No tienes sufucientes fondos para incorporar a este jugador", "Fondos insuficientes");
            return;
        }
        club.agregarJugador(jugador);
        fichajes.borrarFutbolista(jugador);
        //borrado visual de la tabla
        tabla.removeRow(fila);
        revisarDerrota(emp);
        vista.mostrarInformacion("Jugador fichado con exito", "Fichaje exitoso");
        modificar(emp);
        
        }); //FINAL LISTENER (INICIADO EN lÍNEA 342)
    }

    public void despedirJugador(){
        javax.swing.JTable tabla = vista.getPanelCambiarJugador().getTablaCambiar();
        int fila = tabla.getSelectedRow();
        if (fila == -1){
            vista.mostrarInformacion("Selecciona un jugador para despedir.", "Despedir jugador");
            return;
        }
        //validacion para que el equipo quede con almenos 7
        if(club.getJugadores().size() <= 7){
            vista.mostrarError("No puedes despedir a mas jugadores. Tu plantilla minima debe ser de 7 futbolistas", "Limite de plantilla");
        }

        int filaModelo = tabla.convertRowIndexToModel(fila);
        String nombreJugador = tabla.getModel().getValueAt(filaModelo, 0).toString();
        Futbolista jugador = club.buscarJugadorNombre(nombreJugador);
        if (jugador == null){
            vista.mostrarError("No se encontró el jugador seleccionado en el club.", "Error");
            ListarJugadores(club);
            return;
        }

        if (!vista.confirmarDespedidaJugador(jugador.getNombre())){
            return;
        }

        club.despedirJugador(jugador);
        ListarJugadores(club);
    }
    
    public void crearClubesYPatrocinios(ListaClubes clubes, ListaClubes clubesCopia, String nombreClub, ListaPatrocinios patrocinios){
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
            c1= new Club("U. de Chile", 66, 0, 0, "", 0, 0, 0); 
            c2= new Club("U. Catolica", 64, 0, 0, "", 0, 0, 0); 
            c3= new Club("Everton", 63, 0, 0, "", 0, 0, 0); 
            c4= new Club("Palestino", 63, 0, 0, "", 0, 0, 0);
            c5= new Club("Deportes Limache", 62, 0, 0, "", 0, 0, 0);
            c6= new Club("Coquimbo Unido", 62, 0, 0, "", 0, 0, 0);
            c7= new Club("Huachipato", 60, 0, 0, "", 0, 0, 0); 
            c8= new Club("La Serena", 59, 0, 0, "", 0, 0, 0); 
            c9= new Club("Deportes Concepcion", 58, 0, 0, "", 0, 0, 0); 
            c10= new Club("Audax Italiano", 56, 0, 0, "", 0, 0, 0);
            c11= new Club("O.Higgins", 56, 0, 0, "", 0, 0, 0); 
            c12= new Club("Cobresal", 56, 0, 0, "", 0, 0, 0); 
            c13= new Club("U. de Conce", 55, 0, 0, "", 0, 0, 0); 
            c14= new Club("Union La Calera", 54, 0, 0, "", 0, 0, 0);  
            c15= new Club("Ñublense", 53, 0, 0, "", 0, 0, 0);
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

        Club cc1, cc2, cc3, cc4, cc5, cc6, cc7, cc8, cc9, cc10, cc11, cc12, cc13, cc14, cc15;
            cc1 = new Club(c1.getNombre(), c1.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc2 = new Club(c2.getNombre(), c2.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc3 = new Club(c3.getNombre(), c3.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc4 = new Club(c4.getNombre(), c4.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc5 = new Club(c5.getNombre(), c5.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc6 = new Club(c6.getNombre(), c6.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc7 = new Club(c7.getNombre(), c7.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc8 = new Club(c8.getNombre(), c8.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc9 = new Club(c9.getNombre(), c9.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc10 = new Club(c10.getNombre(), c10.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc11 = new Club(c11.getNombre(), c11.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc12 = new Club(c12.getNombre(), c12.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc13 = new Club(c13.getNombre(), c13.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc14 = new Club(c14.getNombre(), c14.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
            cc15 = new Club(c15.getNombre(), c15.getRendimiento(), 0, 0, nombreClub, 0, 0, 0);
        
        clubesCopia.agregarClub(cc1);
        clubesCopia.agregarClub(cc2);
        clubesCopia.agregarClub(cc3);
        clubesCopia.agregarClub(cc4);
        clubesCopia.agregarClub(cc5);
        clubesCopia.agregarClub(cc6);
        clubesCopia.agregarClub(cc7);
        clubesCopia.agregarClub(cc8);
        clubesCopia.agregarClub(cc9);
        clubesCopia.agregarClub(cc10);
        clubesCopia.agregarClub(cc11);
        clubesCopia.agregarClub(cc12);
        clubesCopia.agregarClub(cc13);
        clubesCopia.agregarClub(cc14);
        clubesCopia.agregarClub(cc15);

        Patrocinio p1 = new Patrocinio("Apple", 0, 270000, 6);
        Patrocinio p2 = new Patrocinio("UNICEF", 4, 80000, 6);
        Patrocinio p3 = new Patrocinio("LATAM", 2, 150000, 6);
        Patrocinio p4 = new Patrocinio("OpenAI", -1, 120000, 6);
        Patrocinio p5 = new Patrocinio("Betano", -3, 200000, 6);
        Patrocinio p6 = new Patrocinio("Tel Aviv Inver-sionistas", -5, 400000, 6);
        
        patrocinios.agregarPatrocinio(p1);
        patrocinios.agregarPatrocinio(p2);
        patrocinios.agregarPatrocinio(p3);
        patrocinios.agregarPatrocinio(p4);
        patrocinios.agregarPatrocinio(p5);
        patrocinios.agregarPatrocinio(p6);

    }
    
    public void ventasRendimiento(int diferenciaGoles, Club club, Empresario emp){
        int camisetas = diferenciaGoles*300;
        club.setCamisetasVendidas(club.getCamisetasVendidas()+camisetas);
        emp.setPuntuacion(emp.getPuntuacion()+camisetas*50);
        int entradas = diferenciaGoles * 600;
        club.setEntradasVendidas(club.getEntradasVendidas()+entradas);
        emp.setPuntuacion(emp.getPuntuacion()+entradas*10);
    }

    public void ventasMoralidad(Club club, Empresario emp){
        int camisetas = emp.getMoralidad()*100;
        club.setCamisetasVendidas(club.getCamisetasVendidas()+camisetas);
        emp.setPuntuacion(emp.getPuntuacion()+camisetas*50);
        int entradas = emp.getMoralidad() * 300;
        club.setEntradasVendidas(club.getEntradasVendidas()+entradas);
        emp.setPuntuacion(emp.getPuntuacion()+entradas*10);
    }

    public void opcionesFichaje(Club club, String nombreClub, ListaFichajes fichajes){
        fichajes.getFutbolistas().clear();
        Futbolista[] opciones;
        if (nombreClub.equals("Liverpool")){
            opciones = new Futbolista[]{
                new Futbolista("Florian Wirtz", 10, "Mediapunta", 7, 40000),
                new Futbolista("Victor Munoz", 11, "Extremo izquierdo", 10, 70000),
                new Futbolista("Dominik Szoboslai", 12, "Mediapunta", 6, 25000),
                new Futbolista("Hugo Ekitike", 13, "Delantero", 4, 9000)
            };
        }else if (nombreClub.equals("Inter")){
            opciones = new Futbolista[]{
                new Futbolista("Pio Esposito", 10, "Mediapunta", 6, 50000),
                new Futbolista("Carlos Augusto", 11, "Extremo izquierdo", 10, 90000),
                new Futbolista("Petar Sucic", 12, "Volante", 4, 30000),
                new Futbolista("Henrikh Mkhtitaryan", 13, "Volante", 8, 60000)
            };
        }else{
            opciones = new Futbolista[]{
                new Futbolista("Leandro Hernandez", 10, "Mediapunta", 4, 60000),
                new Futbolista("Francisco Marchant", 11, "Extremo izquierdo", 8, 100000),
                new Futbolista("Ivan Roman", 12, "Central derrecho", 6, 80000),
                new Futbolista("Matias Fernandez", 14, "Volante", 10, 120000)
            };
        }
        club.agregarJugador(opciones[0]);
        club.agregarJugador(opciones[1]);
        fichajes.agregarFutbolista(opciones[2]);
        fichajes.agregarFutbolista(opciones[3]);
        for (int dorsal = 15; dorsal < 25; dorsal++){
            fichajes.agregarFutbolista(generarJugadorMercado(club, nombreClub, dorsal, fichajes, 0));
        }
    }

    private void actualizarMercadoFichajes(String nombreClub, ListaFichajes fichajes, int cicloMercado){
        for (Futbolista jugador : fichajes.getFutbolistas()){
            Random aleatorio = new Random(nombreClub.hashCode() * 31L
                    + jugador.getNombre().hashCode() + jugador.getDorsal() + cicloMercado * 1009L);
            int nuevoRendimiento = 4 + aleatorio.nextInt(5);
            if (nuevoRendimiento == jugador.getRendimiento()){
                nuevoRendimiento = nuevoRendimiento == 8 ? 4 : nuevoRendimiento + 1;
            }
            jugador.setRendimiento(nuevoRendimiento);
        }
    }

    private Futbolista generarJugadorMercado(Club club, String nombreClub, int dorsal, ListaFichajes fichajes, int cicloMercado){
        String[] nombres;
        String[] apellidos;
        String[] posiciones = {
            "Arquero", "Central derecho", "Lateral izquierdo", "Lateral derecho",
            "Mediocentro defensivo", "Central izquierdo", "Extremo derecho",
            "Volante", "Delantero", "Mediapunta", "Extremo izquierdo"
        };
        if (nombreClub.equals("Liverpool")){
            nombres = new String[]{"Oliver", "Harry", "Jack", "George", "Thomas", "Charlie", "James", "Oscar"};
            apellidos = new String[]{"Smith", "Jones", "Taylor", "Brown", "Wilson", "Davies", "Evans", "Roberts"};
        }else if (nombreClub.equals("Inter")){
            nombres = new String[]{"Luca", "Matteo", "Marco", "Lorenzo", "Andrea", "Federico", "Nicolo", "Davide"};
            apellidos = new String[]{"Rossi", "Romano", "Moretti", "Conti", "Ricci", "Marino", "Greco", "Gallo"};
        }else{
            nombres = new String[]{"Benjamin", "Vicente", "Matias", "Diego", "Joaquin", "Maximiliano", "Lucas", "Cristobal"};
            apellidos = new String[]{"Gonzalez", "Muñoz", "Rojas", "Diaz", "Perez", "Soto", "Contreras", "Silva"};
        }

        Random aleatorio = new Random(nombreClub.hashCode() * 31L + dorsal + cicloMercado * 1009L);
        int inicioNombre = aleatorio.nextInt(nombres.length);
        int inicioApellido = aleatorio.nextInt(apellidos.length);
        String nombre = null;
        for (int intento = 0; intento < nombres.length * apellidos.length; intento++){
            String candidato = nombres[(inicioNombre + intento) % nombres.length] + " "
                    + apellidos[(inicioApellido + intento / nombres.length) % apellidos.length];
            if (club.buscarJugadorNombre(candidato) == null && fichajes.buscarFutbolista(candidato) == null){
                nombre = candidato;
                break;
            }
        }
        if (nombre == null){
            throw new IllegalStateException("No se pudo generar un nombre de jugador disponible para " + nombreClub);
        }

        int rendimiento = 4 + aleatorio.nextInt(5);
        double sueldo = rendimiento * 10000.0;
        String posicion = posiciones[aleatorio.nextInt(posiciones.length)];
        return new Futbolista(nombre, dorsal, posicion, rendimiento, sueldo);
    }

    public void listarRivales(ListaClubes clubes){
        DefaultTableModel tabla = (DefaultTableModel) vista.getInformacionRivales().getTablaRivales().getModel();
        tabla.setRowCount(0);
        for(Club club: clubes.getClubes()){
            tabla.addRow(new Object[]{club.getNombre(), club.getPartidosJugados(), club.getRendimiento()});
        }
    }

    public void informacionRivales(ListaClubes clubes, Empresario emp){
        revisarDerrota(emp);
        listarRivales(clubes);
        vista.getInformacionRivales().getLblPuntuacion().setText(String.valueOf(emp.getPuntuacion()));
        vista.mostrarPantalla("INFORMACION RIVALES");
    }

    public void revisarDerrota(Empresario emp){
        if (emp.getPuntuacion()<=0){
            File archivo = new File("memoryCard.txt");
            archivo.delete();
            vista.mostrarPantalla("DERROTA");
        }
    }

    public void revisarVictoria(Club club, Empresario emp){
        revisarDerrota(emp);
        vista.getPanelVictoria().getLblPuntuacion().setText(String.valueOf(emp.getPuntuacion()));
        if (club.getPartidosJugados()>=15){
            File archivo = new File("memoryCard.txt");
            archivo.delete();
            vista.mostrarPantalla("VICTORIA");
        }
            
    }
    
    public void guardarPartida(Empresario emp, Club club, ListaClubes clubes, ListaPatrocinios patrocinios, ListaFichajes fichajes, String nombreArchivo) throws IOException{
        limpiarArchivo();
        BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo));
        bw.write(emp.getNombre());
        bw.newLine();
        bw.write(club.getNombre()+";"+club.getRendimiento()+";"+club.getPartidosGanados()+";"+club.getPartidosJugados()+";"+club.getCamisetasVendidas()+";"+club.getEntradasVendidas()+";"+club.getCantidadPatrocinadores());
        bw.newLine();
        for (int i = 0; i<club.getJugadores().size()-1; i++){
            Futbolista temporal = club.getJugadores().get(i);
            bw.write(temporal.getDorsal()+";");
        }
        Futbolista ultimo = club.getJugadores().get(club.getJugadores().size()-1);
        bw.write(String.valueOf(ultimo.getDorsal()));
        bw.newLine();
        for (int i = 0; i<patrocinios.getPatrocinios().size()-1; i++){
            Patrocinio temp = patrocinios.getPatrocinios().get(i);
            bw.write(temp.getNombre()+";"+temp.getPartidosRestantes()+";"+temp.isAsociado()+";");
        }
        Patrocinio fin = patrocinios.getPatrocinios().get(patrocinios.getPatrocinios().size()-1);
        bw.write(fin.getNombre()+";"+fin.getPartidosRestantes()+";"+fin.isAsociado());
        bw.newLine();
        bw.write(String.valueOf(emp.getMoralidad()));
        System.out.println(emp.getMoralidad());
        bw.newLine();
        bw.write(String.valueOf(emp.getPuntuacion()));
        System.out.println(emp.getPuntuacion());
        bw.newLine();
        for (int i = 0; i<clubes.getClubes().size()-1; i++){
            bw.write(clubes.getClubes().get(i).getPartidosJugados()+";");        
        }
        Club last = clubes.getClubes().get(clubes.getClubes().size()-1);
        bw.write(String.valueOf(last.getPartidosJugados()));
        bw.newLine();
        for (int i = 0; i < club.getJugadores().size(); i++){
            Futbolista jugador = club.getJugadores().get(i);
            if (i > 0){
                bw.write(";");
            }
            bw.write(jugador.getNombre()+"|"+jugador.getDorsal()+"|"+jugador.getPosicion()+"|"
                    +jugador.getRendimiento()+"|"+jugador.getSueldo());
        }
        bw.newLine();
        for (int i = 0; i < fichajes.getFutbolistas().size(); i++){
            Futbolista jugador = fichajes.getFutbolistas().get(i);
            if (i > 0){
                bw.write(";");
            }
            bw.write(jugador.getNombre()+"|"+jugador.getDorsal()+"|"+jugador.getPosicion()+"|"
                    +jugador.getRendimiento()+"|"+jugador.getSueldo());
        }
        bw.newLine();
        bw.close();
    }

    public void limpiarArchivo(){
        Path ruta = Paths.get("memoryCard.txt");
        try {
            Files.writeString(ruta, ""); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
    
