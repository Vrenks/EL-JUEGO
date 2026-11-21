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
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
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
    private static final Path ARCHIVO_MEJOR_PUNTUACION = Paths.get(
            System.getProperty("user.home"), ".moneyball-highscore");
    private VisualizadorMB vista;
    private Empresario emp; 
    private Club club;
    private final AtomicInteger sesion = new AtomicInteger(1);
    private int highScore;
    
    public ControladorMB(VisualizadorMB vista, Empresario emp, Club club) {
        this(vista, emp, club, 0);
    }

    public ControladorMB(VisualizadorMB vista, Empresario emp, Club club, int highScore) {
        this.vista=vista;
        this.emp=emp;
        this.club=club;
        this.highScore=leerMejorPuntuacion(highScore);
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

        // eliminar boton: vista.getPanelDerrota().getBtnVolverAlInicio().addActionListener(e->vista.mostrarPantalla("INICIO"));
        vista.getPanelDerrota().getBtnSalir().addActionListener(e->System.exit(0));

        // eliminar boton: vista.getPanelVictoria().getBtnVolverAlInicio().addActionListener(e->vista.mostrarPantalla("INICIO"));
        vista.getPanelVictoria().getBtnSalir().addActionListener(e->System.exit(0));

        vista.getPanelGestion().getBtnEventos().addActionListener(e->{
            vista.getPanelEventos().getLblPuntuacion().setText(String.valueOf(emp.getPuntuacion()));
            vista.mostrarPantalla("EVENTOS");
        });
        vista.getPanelEventos().getBtnEventosAGestion().addActionListener(e->gestionar(emp));

        
        
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
            boolean formatoAnteriorConEventos = linea9 != null
                    && linea9.trim().matches("\\d+")
                    && (linea8 == null || !linea8.contains("|"));
            String linea10 = formatoAnteriorConEventos ? null : br.readLine();
            int cantidadEventos = formatoAnteriorConEventos
                    ? Integer.parseInt(linea9.trim())
                    : linea10 == null || linea10.trim().isEmpty() ? 0 : Integer.parseInt(linea10.trim());

            DefaultTableModel tablaPartidos = (DefaultTableModel) vista.getPanelSimulacion().getTablaPartido().getModel();
            DefaultTableModel tablaEventos = (DefaultTableModel) vista.getPanelEventos().getTablaEventos().getModel();
            tablaPartidos.setRowCount(0);
            tablaEventos.setRowCount(0);

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
            if (formatoAnteriorConEventos && linea8 != null && !linea8.isEmpty()){
                for (String registro : linea8.split(";")){
                    if (registro.isEmpty()){
                        continue;
                    }
                    String[] datosPartido = registro.split(",", 3);
                    if (datosPartido.length == 3){
                        tablaPartidos.addRow(new Object[]{Integer.parseInt(datosPartido[0]),
                            datosPartido[1], datosPartido[2]});
                    }
                }
            }
            int ultimaSesion = 0;
            for (int i = 0; i < cantidadEventos; i++){
                String registro = br.readLine();
                if (registro == null){
                    throw new IOException("El archivo de guardado está incompleto en el historial de eventos.");
                }
                String[] datosEvento = registro.split(";", 3);
                if (datosEvento.length < 3){
                    throw new IOException("El historial de eventos del archivo de guardado no es válido.");
                }
                int numeroSesion = Integer.parseInt(datosEvento[0]);
                ultimaSesion = Math.max(ultimaSesion, numeroSesion);
                tablaEventos.addRow(new Object[]{numeroSesion, datosEvento[1], datosEvento[2]});
            }
            if (!formatoAnteriorConEventos && linea10 != null){
                String lineaCantidadPartidos = br.readLine();
                if (lineaCantidadPartidos != null && !lineaCantidadPartidos.trim().isEmpty()){
                    int cantidadPartidosGuardados = Integer.parseInt(lineaCantidadPartidos.trim());
                    for (int i = 0; i < cantidadPartidosGuardados; i++){
                        String registro = br.readLine();
                        if (registro == null){
                            throw new IOException("El archivo de guardado está incompleto en el historial de partidos.");
                        }
                        String[] datosPartido = registro.split(";", 3);
                        if (datosPartido.length < 3){
                            throw new IOException("El historial de partidos del archivo de guardado no es válido.");
                        }
                        tablaPartidos.addRow(new Object[]{Integer.parseInt(datosPartido[0]),
                            datosPartido[1], datosPartido[2]});
                    }
                }
            }
            
            clubBase(club, nombreClub);
            club.opcionesFichaje(club, nombreClub, fichajes);
            clubes.crearClubesYPatrocinios(clubes, clubesCopia, nombreClub, patrocinios);
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
            if (!formatoAnteriorConEventos && linea8 != null && !linea8.isEmpty()){
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
            if (!formatoAnteriorConEventos && linea9 != null && !linea9.isEmpty()){
                fichajes.getFutbolistas().clear();
                for (String registro : linea9.split(";")){
                    String[] datos = registro.split("\\|", -1);
                    fichajes.agregarFutbolista(new Futbolista(datos[0], Integer.parseInt(datos[1]),
                            datos[2], Integer.parseInt(datos[3]), Double.parseDouble(datos[4])));
                }
            }else{
                for (int ciclo = 1; ciclo <= partidosJugados / 3; ciclo++){
                    club.actualizarMercadoFichajes(nombreClub, fichajes, ciclo);
                }
            }
            sesion.set(ultimaSesion + 1);
            agregarEvento("Carga", "Sesión cargada");

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
        clubes.crearClubesYPatrocinios(clubes, clubesCopia, nombreClub, patrocinios);
        club.opcionesFichaje(club, nombreClub, fichajes);
        String[] nombreLiga = dificultad.split("\\(");
        agregarEvento("Creación", nombreEmpresario + " ahora es presidente de " + nombreClub
                + " en la " + nombreLiga[0].trim() + ".");
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


        Futbolista f1 = new Futbolista(f1Nombre, 1, "Arquero", 5, 10000);
        Futbolista f2 = new Futbolista(f2Nombre, 2, "Central derecho", 4, 10000);
        Futbolista f3 = new Futbolista(f3Nombre, 3, "Lateral izquierdo", 3, 10000);
        Futbolista f4 = new Futbolista(f4Nombre, 4, "Lateral derecho", 5, 10000);
        Futbolista f5 = new Futbolista(f5Nombre, 5, "Mediocentro defensivo", 4, 10000);
        Futbolista f6 = new Futbolista(f6Nombre, 6, "Central izquierdo", 2, 10000);
        Futbolista f7 = new Futbolista(f7Nombre, 7, "Extremo derecho", 5, 10000);
        Futbolista f8 = new Futbolista(f8Nombre, 8, "Volante", 4, 10000);
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
            club.ventasRendimiento(diferenciaGoles, club, emp);
            agregarEvento("Simulación", club.getNombre() + " le ganó " + diferenciaGoles
                    + "-0 a " + rival.getNombre());

        }else if (rival.getRendimiento()>club.getRendimiento()){
            diferenciaRendimiento=rival.getRendimiento()-club.getRendimiento();
            diferenciaGoles = rand.nextInt(diferenciaRendimiento)+1;
            resultado = rival.getNombre()+": "+ "0-"+ diferenciaGoles;
            int castigo = diferenciaGoles*-10;
            club.ventasRendimiento(castigo, club, emp);
            agregarEvento("Simulación", club.getNombre() + " perdió 0-" + diferenciaGoles
                    + " con " + rival.getNombre());
        }
        else{ 
            diferenciaGoles = 0;
            resultado = "Empate: 0-0";
            agregarEvento("Simulación", club.getNombre() + " empató 0-0 con " + rival.getNombre());
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
            club.actualizarMercadoFichajes(club.getNombre(), fichajes, club.getPartidosJugados() / 3);
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
                    agregarEvento("Patrocinio", "El patrocinador '" + patrocinio.getNombre()
                            + "' abandonó el equipo. Compensación: $" + patrocinio.getInversion() / 2);
                    patrocinios.borrarPatrocinio(patrocinio);
                }
            }
        }
        if (club.getPartidosJugados()!=0 && club.getPartidosJugados()%3==0){
            int eleccion = vista.eleccion("Patrocinador(es) interesados. Presione YES para revisarlo(s) o NO para seguir en la simulacion.", "INFORMACION PATROCINIOS");
            if (eleccion!=0)
                return;
            patrocinar(patrocinios, emp, club);
            club.ventasMoralidad(club, emp);
        }
        if (club.getPartidosJugados()!=0&&club.getPartidosJugados()%4==0){
            int sueldos = 0;
            for (Futbolista futbolista: club.getJugadores()){
                sueldos+=futbolista.getSueldo();
            }
            vista.mostrarInformacion("Sueldos cobrados: $"+sueldos, "INFORMACION IMPORTANTE");
            agregarEvento("Sueldos", "Se cobraron $" + sueldos + " por los sueldos de los jugadores.");
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
        for (ActionListener listener : vista.getPanelPatrocinios().getBtnElegirPatrocinador().getActionListeners()){
            vista.getPanelPatrocinios().getBtnElegirPatrocinador().removeActionListener(listener);
        }
        revisarDerrota(emp);
        vista.getPanelPatrocinios().getLblPuntuacion2().setText(String.valueOf(emp.getPuntuacion()));
        Random rand = new Random();
        int numAleatorio1 = 0;
        int numAleatorio2 = 0;
        boolean condicion = true;
        while (condicion){
            numAleatorio1 = rand.nextInt(patrocinios.getPatrocinios().size());
            numAleatorio2 = rand.nextInt(patrocinios.getPatrocinios().size());
            if (!patrocinios.getPatrocinios().get(numAleatorio1).isAsociado()&&!patrocinios.getPatrocinios().get(numAleatorio2).isAsociado()&&numAleatorio1!=numAleatorio2)
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
                agregarEvento("Patrocinio", club.getNombre() + " eligió a " + patrocinio.getNombre()
                        + " como patrocinador. Inversión: " + patrocinio.getInversion()
                        + " Moralidad: " + patrocinio.getMoralidad());
                int moralidadPatrocinadorElegido = (int) vista.getPanelPatrocinios().getTablaPatrocinadores().getValueAt(fila, 2);
                int inversionPatrocinadorElegido = (int) vista.getPanelPatrocinios().getTablaPatrocinadores().getValueAt(fila, 1);
                emp.setMoralidad(emp.getMoralidad()+moralidadPatrocinadorElegido);
                emp.setPuntuacion(inversionPatrocinadorElegido+emp.getPuntuacion());
                vista.getPanelSimulacion().getLblPuntuacion().setText(String.valueOf(emp.getPuntuacion()));
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
        vista.getPanelCambiarJugador().getLblPuntuacion().setText(String.valueOf(emp.getPuntuacion()));
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
        agregarEvento("Fichaje", club.getNombre() + " fichó a " + jugador.getNombre()
                + ". Sueldo: " + jugador.getSueldo() + ", rendimiento: " + jugador.getRendimiento());
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
        agregarEvento("Despido", club.getNombre() + " despidió a " + jugador.getNombre()
                + " por decisión de " + emp.getNombre() + ".");
        ListarJugadores(club);
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
        vista.getPanelVictoria().getLblHighScore().setText(String.valueOf(highScore));
        if (club.getPartidosJugados()>=15){
            if (emp.getPuntuacion() > highScore){
                highScore = emp.getPuntuacion();
                vista.getPanelVictoria().getLblHighScore().setText(String.valueOf(highScore));
                try {
                    Files.writeString(ARCHIVO_MEJOR_PUNTUACION, String.valueOf(highScore));
                } catch (IOException e) {
                    vista.mostrarError("No se pudo guardar la mejor puntuación histórica: " + e.getMessage(),
                            "Error al guardar récord");
                }
            }
            File archivo = new File("memoryCard.txt");
            archivo.delete();
            vista.mostrarPantalla("VICTORIA");
        }
            
    }
    
    public void guardarPartida(Empresario emp, Club club, ListaClubes clubes, ListaPatrocinios patrocinios, ListaFichajes fichajes, String nombreArchivo) throws IOException{
        limpiarArchivo();
        agregarEvento("Guardado", "Sesión guardada");
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
        bw.newLine();
        bw.write(String.valueOf(emp.getPuntuacion()));
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
        DefaultTableModel tablaEventos = (DefaultTableModel) vista.getPanelEventos().getTablaEventos().getModel();
        bw.write(String.valueOf(tablaEventos.getRowCount()));
        bw.newLine();
        for (int i = 0; i < tablaEventos.getRowCount(); i++){
            bw.write(tablaEventos.getValueAt(i, 0) + ";"
                    + limpiarCampoGuardado(tablaEventos.getValueAt(i, 1).toString()) + ";"
                    + limpiarCampoGuardado(tablaEventos.getValueAt(i, 2).toString()));
            bw.newLine();
        }
        DefaultTableModel tablaPartidos = (DefaultTableModel) vista.getPanelSimulacion().getTablaPartido().getModel();
        bw.write(String.valueOf(tablaPartidos.getRowCount()));
        bw.newLine();
        for (int i = 0; i < tablaPartidos.getRowCount(); i++){
            bw.write(tablaPartidos.getValueAt(i, 0) + ";"
                    + limpiarCampoGuardado(tablaPartidos.getValueAt(i, 1).toString()) + ";"
                    + limpiarCampoGuardado(tablaPartidos.getValueAt(i, 2).toString()));
            bw.newLine();
        }
        bw.close();
    }

    private int leerMejorPuntuacion(int valorInicial){
        if (!Files.exists(ARCHIVO_MEJOR_PUNTUACION)){
            return valorInicial;
        }
        try {
            String contenido = Files.readString(ARCHIVO_MEJOR_PUNTUACION).trim();
            return contenido.isEmpty() ? valorInicial : Math.max(valorInicial, Integer.parseInt(contenido));
        } catch (IOException | NumberFormatException e) {
            vista.mostrarError("No se pudo leer la mejor puntuación histórica: " + e.getMessage(),
                    "Error al cargar récord");
            return valorInicial;
        }
    }

    private String limpiarCampoGuardado(String valor){
        return valor.replace(';', ',').replace('\n', ' ').replace('\r', ' ');
    }

    public int getHighScore(){
        return highScore;
    }

    public void setHighScore(int highScore){
        this.highScore = highScore;
    }

    public void limpiarArchivo(){
        Path ruta = Paths.get("memoryCard.txt");
        try {
            Files.writeString(ruta, ""); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void agregarEvento(String tipoEvento, String evento){
        DefaultTableModel tabla = (DefaultTableModel) vista.getPanelEventos().getTablaEventos().getModel();
        tabla.addRow(new Object[]{sesion.get(), limpiarCampoGuardado(tipoEvento), limpiarCampoGuardado(evento)});
    }


}
    




