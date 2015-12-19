package tp.pr5;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import tp.pr5.cityLoader.CityLoaderFromTxtFile;
import tp.pr5.cityLoader.cityLoaderExceptions.WrongCityFormatException;
import tp.pr5.console.ConsoleController;
import tp.pr5.gui.GUIController;
import tp.pr5.gui.MainWindow;
/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * Main class of the application.
 */

public class Main {
	/**
	 * Metodo Main por el que comienza la ejecucion
	 */
	public static void main(String args[]) {
		//declaraciones
		InputStream in = null;
		
		try{			
			if(args.length == 0){
				System.err.println("Map file not specified");
				System.exit(1);
				
			}else if(args.length == 1 && instructHelp(args)){
				//acceso de -h
				/** MOSTRAR ESTO POR CONSOLA **/
				System.out.println("Execute this assignment with these parameters:");
				System.out.println("usage: tp.pr5.Main [-h] [-i <type>] [-m <mapfile>]");
				System.out.println(" -h,--help               Shows this help message");
				System.out.println(" -i,--interface <type>   The type of interface: console, swing or both");
				System.out.println(" -m,--map <mapfile>      File with the description of the city");
				System.exit(0);
								
			}else if(args.length == 4 && instructConsole(args)){				
				in = cargaPosFichero(args);
				
				//ejecucion consola
				metodoConsola(in);
				//System.exit(0);
				
			}else if(args.length == 4 && instructSwing(args)){
				in = cargaPosFichero(args);
				
				//funcionamiento de la practica anterior
				in = new FileInputStream(args[3]);
				//ejecucion Swing
				metodoSwing(in);
				//System.exit(0);
			}else if(args.length == 4 && instructBoth(args)){
				in = cargaPosFichero(args);
				
				//funcionamiento de la practica anterior
				in = new FileInputStream(args[3]);
				//ejecucion Both
				metodoBoth(in);
				//System.exit(0);
				
			}else{
				if(args.length == 4){
					comprobacionErrorEstruct4(args);
					System.exit(3);
				}else if(args.length == 2){
					comprobacionErrorEstruct2(args);
					System.exit(1);
				}else{
					System.err.println("Error Formated");
				}
				//System.exit(1);
			}
		}catch(FileNotFoundException e){
			if(capturaMapa(args[0])){
				System.err.println("Error reading the map file: " + args[1] + " (No existe el fichero o el directorio)");
			}else if (capturaMapa(args[2])){
				System.err.println("Error reading the map file: " + args[3] + " (No existe el fichero o el directorio)");
			}
			System.exit(2);
		}finally{
			try {
				if(in!=null)
					in.close();
			} catch (IOException e) {
					
			}
		}
	}
	
	/**
	 * Private method use it to run the application in the console.
	 * @param in
	 * @throws FileNotFoundException
	 */
	private static void metodoConsola(InputStream in) throws FileNotFoundException{
		try {
			//variables
			CityLoaderFromTxtFile cityload = new CityLoaderFromTxtFile();
			
			RobotEngine robot = new RobotEngine( cityload.loadCity(in), cityload.getInitialPlace(), Direction.NORTH );
			
			ConsoleController consola = new ConsoleController(robot);
			consola.startController();
			
		} catch (IOException e) {

		} catch (WrongCityFormatException e) {
			throw new FileNotFoundException();
		}
	}
	
	/**
	 * Private method use it to run the application in the SWING configuration.
	 * @param in
	 * @throws FileNotFoundException
	 */
	private static void metodoSwing(InputStream in) throws FileNotFoundException{
		try {
			//variables
			CityLoaderFromTxtFile cityload = new CityLoaderFromTxtFile();

			RobotEngine robot = new RobotEngine( cityload.loadCity(in), cityload.getInitialPlace(), Direction.NORTH );
		
			GUIController swing = new GUIController(robot);
			
			MainWindow ventana = new MainWindow(swing);
			swing.startController();
			
		} catch (IOException e) {

		} catch (WrongCityFormatException e) {
			throw new FileNotFoundException();
		}
	}
	
	
	/**
	 * Private method use it to run the application in the SWING and the CONSOLE configuration.
	 * @param in
	 * @throws FileNotFoundException
	 */
	private static void metodoBoth(InputStream in) throws FileNotFoundException{
		try {
			//variables
			CityLoaderFromTxtFile cityload = new CityLoaderFromTxtFile();

			RobotEngine robot = new RobotEngine( cityload.loadCity(in), cityload.getInitialPlace(), Direction.NORTH );
		
			ConsoleController consola = new ConsoleController(robot);
			GUIController swing = new GUIController(robot);
		
			MainWindow ventana  = new MainWindow(swing);
			swing.startController();
			
		} catch (IOException e) {

		} catch (WrongCityFormatException e) {
			throw new FileNotFoundException();
		}
	}
	
	/**
	 * Private method to capture the instruction HELP.
	 * @param args
	 * @return
	 */
	private static boolean instructHelp(String args[]){
		return capturaAyuda(args[0]);
	}
	
	/**
	 * Private method to use console instruct.
	 * @param args
	 * @return
	 */
	private static boolean instructConsole(String args[]){
		return ( ((capturaInterface(args[0]) && capturaConsola(args[1])) || (capturaInterface(args[2]) && capturaConsola(args[3])) ) );
	}
	
	/**
	 * Private method to use swing instruct.
	 * @param args
	 * @return
	 */
	private static boolean instructSwing(String args[]){
		return ( ((capturaInterface(args[0]) && capturaVista(args[1])) || (capturaInterface(args[2]) && capturaVista(args[3])) ) );
	}
	
	/**
	 * Private method to use Both instruct.
	 * @param args
	 * @return
	 */
	private static boolean instructBoth(String args[]){
		return ( ((capturaInterface(args[0]) && capturaBoth(args[1])) || (capturaInterface(args[2]) && capturaBoth(args[3])) ) );
	}
	
	/**
	 * Method to capture the interface in the configuration run.
	 * @param inter
	 * @return
	 */
	private static boolean capturaInterface(String inter){
		return inter.equalsIgnoreCase("-i") || inter.equalsIgnoreCase("--interface");
	}
	
	/**
	 * Method to capture help in the configuration run.
	 * @param help
	 * @return
	 */
	private static boolean capturaAyuda(String help){
		return help.equalsIgnoreCase("-h");
	}
	
	/**
	 * Method to capture the console in configuration run.
	 * @param estado
	 * @return
	 */
	private static boolean capturaConsola(String estado){
		return estado.equalsIgnoreCase("console");
	}
	
	/**
	 * Method to capture the swing in configuration run.
	 * @param estado
	 * @return
	 */
	private static boolean capturaVista(String estado){
		return estado.equalsIgnoreCase("swing");
	}
	
	/**
	 * Method to capture the swing and console in configuration run.
	 * @param estado
	 * @return
	 */
	private static boolean capturaBoth(String estado){
		return estado.equalsIgnoreCase("both");
	}
	
	/**
	 * Private method to capture the map in the configuration run.
	 * @param map
	 * @return
	 */
	private static boolean capturaMapa(String map){
		return map.equalsIgnoreCase("-m") || map.equalsIgnoreCase("--map");
	}

	/**
	 * Private method to upload the file in the run configuration.
	 * @param args
	 * @return
	 * @throws FileNotFoundException
	 */
	private static InputStream cargaPosFichero(String[] args) throws FileNotFoundException{
		//declaraciones
		InputStream in = null;
		
		if(capturaMapa(args[0])){
			in = new FileInputStream(args[1]);
		}else if (capturaMapa(args[2])){
			in = new FileInputStream(args[3]);
		}else{
			//throw new FileNotFoundException();
			System.err.println("Map file not specified");
		}
		return in;
	}
	
	/**
	 * Private method to avoid bad executions because the run configuration. Shows error messages.
	 * @param args
	 */
	private static void comprobacionErrorEstruct4(String[] args){
		if( !capturaMapa(args[0]) && !capturaMapa(args[2])){
			System.err.println("Map file not specified");
		}else if( capturaInterface(args[0]) || capturaInterface(args[2])){
			if( !capturaConsola(args[1]) && !capturaConsola(args[3]) || !capturaVista(args[1]) && !capturaVista(args[3]) || !capturaBoth(args[1]) && !capturaBoth(args[3])){
				System.err.println("Wrong type of interface");
			}
		}else if( !capturaInterface(args[0]) && !capturaInterface(args[2])){
			System.err.println("Interface not specified");
		}		
	}
	
	/**
	 * Private method to avoid bad executions because the run configuration. Shows error messages.
	 * @param args
	 */
	private static void comprobacionErrorEstruct2(String[] args){
		if( !capturaMapa(args[0])){
			System.err.println("Map file not specified");
		}else if( capturaInterface(args[0])){
			if( !capturaConsola(args[1]) || !capturaVista(args[1]) || !capturaBoth(args[1])){
				System.err.println("Wrong type of interface");
			}
		}else if( !capturaInterface(args[0]) ){
			System.err.println("Interface not specified");
		}		
	}
}

