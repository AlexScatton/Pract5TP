package tp.pr5.cityLoader;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import tp.pr5.City;
import tp.pr5.Direction;
import tp.pr5.Place;
import tp.pr5.Street;
import tp.pr5.cityLoader.cityLoaderExceptions.WrongCityFormatException;
import tp.pr5.items.CodeCard;
import tp.pr5.items.Fuel;
import tp.pr5.items.Garbage;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * Class that upload the .txt file. It contents the map of the city for the robot.
 *
 */
public class CityLoaderFromTxtFile {

	private Place[] lugar;
	private City ciudad;
	/**
	 * Default constructor.
	 */
	public CityLoaderFromTxtFile(){

		this.lugar = new Place [10];
		this.ciudad = new City();
	}
	
	/**
	 * @param file - The input stream where the city is stored
	 * @return The city
	 * @throws java.io.IOException - When there is some format error in the file 
	 * (WrongCityFormatException) or some errors in IO operations
	 * */
	public City loadCity(InputStream file) 
			throws WrongCityFormatException, IOException{
		//declaraciones
		DataInputStream dis = new DataInputStream (file);
		String lectura_aux = "";
		
		try{
			if(this.centinelaFichero( dis.readLine(), "BeginCity")){
				lectura_aux = dis.readLine();
				
				while (!this.centinelaFichero( lectura_aux, "EndCity")){
					//////////////////////PLACES
					
					if(this.centinelaFichero( lectura_aux, "BeginPlaces")){
						this.loadPlaces(dis);
						
						//////////////////////STREETS
						if(this.centinelaFichero( dis.readLine(), "BeginStreets")){
							this.loadStreets(dis);
							
							//////////////////////ITEMS
							if(this.centinelaFichero( dis.readLine(), "BeginItems")){
								this.loadItems(dis);	
		
							}else{
								throw new WrongCityFormatException();
							}
						}else{
							throw new WrongCityFormatException();
						}
					}else{
						throw new WrongCityFormatException();	
					}
					
					lectura_aux = dis.readLine();
					
				}//cierra el while
				}else{
					throw new WrongCityFormatException();
				}
			//en caso de que suceda cualquier excepcion no abarcada las convertiremos 
			//en WrongCityFormatException para evitar problemas
		}catch(Exception e){
			throw new WrongCityFormatException();
		}
		return this.ciudad;
	}
	
	/**
	 * Returns the place where the robot will start the simulation
	 * @return The initial place
	 * */
	public Place getInitialPlace(){
		//retorna el lugar en el cual empezara la simulacion
		return this.lugar[0];
	}
	
	/**
	 * Private method, it load the places from the .txt file.
	 * @param dis
	 * @throws WrongCityFormatException
	 * @throws IOException
	 */
	private void loadPlaces(DataInputStream dis) 
			throws WrongCityFormatException, IOException{
		//declaraciones
		String lectura_aux = "" ;
		String words[];
		boolean nave = false;
		int valor_parse = 0;
		
		lectura_aux = dis.readLine();
		int contPlaces = 0;
		//mientras no se haya acabado la parte de places
		while(!this.centinelaFichero( lectura_aux, "EndPlaces")){
			words = lectura_aux.split(" ");
			
			try{				
				valor_parse = this.parseEntero(words[1]);
				
				//comprobamos el formato
				if( this.centinelaFichero( words[0], "place") && this.comparadorElemen(contPlaces, valor_parse)){
					
					//comprobamos si se encuentran una de estas cadenas o sino error de formato
					if(this.centinelaFichero( words[4], "noSpaceShip")){
						nave = false;
					}else if(this.centinelaFichero( words[4], "spaceShip")){
						nave = true;
					}else{
						throw new WrongCityFormatException();
					}
					
					//guardamos lugar
					this.lugar[contPlaces] = new Place(words[2], nave, this.guionesPorEspacios(words[3]));
				        
				}else{
					throw new WrongCityFormatException();
				}
			}catch(NumberFormatException e){
				throw new WrongCityFormatException();
			}
			//incremento y siguiente linea
			contPlaces++;
			lectura_aux = dis.readLine();
		}
	}
	
	/**
	 * Private method. It load the streets of the city from the .txt file.
	 * @param dis
	 * @throws WrongCityFormatException
	 * @throws IOException
	 */
	private void loadStreets(DataInputStream dis) 
			throws WrongCityFormatException, IOException{
		//declaraciones
		String lectura_aux = "" ;
		String words[];
		int contStreets = 0;
		int valor_parse = 0;
		
		lectura_aux = dis.readLine();
		
		while (!this.centinelaFichero(lectura_aux, "EndStreets")){
			words = lectura_aux.split(" ");
			
			//comprobamos formato
			try{
		
				valor_parse = this.parseEntero(words[1]);
				
				if( this.centinelaFichero(words[0], "street") && this.comparadorElemen(contStreets, valor_parse)){
					
					Place origen = null;
					Place destino = null;
					Direction dir = null;
					String llave = null;
					boolean puerta = false;
					
					valor_parse = this.parseEntero(words[3]);
					
					if(this.centinelaFichero(words[2], "place") && valor_parse <= this.lugar.length){
						origen = this.lugar[valor_parse];
					}else{
						throw new WrongCityFormatException();
					}
					
					valor_parse = this.parseEntero(words[6]);
					
					if(this.centinelaFichero(words[5], "place") && valor_parse <= this.lugar.length){
						destino = this.lugar[Integer.parseInt(words[6])];
					}else{
						throw new WrongCityFormatException();
					}
					
					//si contiene clave para abrir
					if(this.comparadorElemen(words.length, 9)){
						llave = words[8];
					}
					
					//comprobamos si se encuentran una de estas cadenas o sino error de formato
					if(this.centinelaFichero(words[7], "open")){
						puerta = true;
					}else if(this.centinelaFichero(words[7], "closed")){
						puerta = false;
					}else{
						throw new WrongCityFormatException();
					}
					
					//guardamos calle
					Street calle = new Street(origen, (dir.valueOf(words[4].toUpperCase())), destino, puerta, llave );
			        this.ciudad.addStreet(calle);
			        
				}else{
					throw new WrongCityFormatException();
				}
			}catch(NumberFormatException e){
				throw new WrongCityFormatException();
			}
			
			//incremento y sigueinte linea
			contStreets++;	
			lectura_aux = dis.readLine();
		}
	}
	
	/**
	 * Private method. It loads the items of every place from the .txt file.
	 * @param dis
	 * @throws WrongCityFormatException
	 * @throws IOException
	 */
	private void loadItems(DataInputStream dis) 
			throws WrongCityFormatException, IOException{
		//declaraciones
		String lectura_aux = "" ;
		String words[];
		int valor_parse_cont = 0;
		int valor_parse_cantidad = 0;
		int valor_parse_unidades = 0;
		int valor_parse_place = 0;
		
		lectura_aux = dis.readLine();
		int contItems = 0;
		
		while (!this.centinelaFichero(lectura_aux, "EndItems")){
			words = lectura_aux.split(" ");
			
			//comprobamos que cumple el formato
			try{								
				
				valor_parse_cont = this.parseEntero(words[1]);
		
				if( this.centinelaFichero(words[0], "fuel") && this.comparadorElemen(contItems, valor_parse_cont) && this.centinelaFichero(words[6], "place")){

					valor_parse_place = this.parseEntero(words[7]);
					
					valor_parse_unidades = this.parseEntero(words[4]);
					
					valor_parse_cantidad = this.parseEntero(words[5]);
					
					this.lugar[valor_parse_place].addItem(new Fuel(words[2], this.guionesPorEspacios(words[3]) , valor_parse_unidades, valor_parse_cantidad));
				}else if(this.centinelaFichero(words[0], "garbage") && this.comparadorElemen(contItems, valor_parse_cont) && this.centinelaFichero(words[5], "place")){

					valor_parse_place = this.parseEntero(words[6]);
					
					valor_parse_unidades = this.parseEntero(words[4]);
					
					this.lugar[valor_parse_place].addItem(new Garbage(words[2], this.guionesPorEspacios(words[3]) ,valor_parse_unidades));
				}else if(this.centinelaFichero(words[0], "codecard") && this.comparadorElemen(contItems, valor_parse_cont) && this.centinelaFichero(words[5], "place")){
					
					valor_parse_place = this.parseEntero(words[6]);
					
					this.lugar[valor_parse_place].addItem(new CodeCard(words[2], this.guionesPorEspacios(words[3]), words[4]));
				}else{
					throw new WrongCityFormatException();
				}
			}catch(NumberFormatException e){
				throw new WrongCityFormatException();
			}
			
			//incremento y siguiente linea
			contItems++;
			lectura_aux = dis.readLine();
		}
	}
	/**
	 * Private boolean method. It compares the contador and the id elem.
	 * @param contador
	 * @param id_elem
	 * @return true if they are the same. False otherwise.
	 */
	private boolean comparadorElemen(int contador, int id_elem){
		return contador == id_elem;
	}
	/**
	 * Private boolean method. It compares the centinela of .txt ignoring Capital Letters.
	 * @param lectura_aux
	 * @param centinela
	 * @return true if the centinela is correct. False if is not.
	 */
	private boolean centinelaFichero(String lectura_aux, String centinela){
		return lectura_aux.equalsIgnoreCase(centinela);
	}
	
	/**
	 * Private String method. It replaces the "-" with blanks.
	 * @param cadena
	 * @return an String replacing the "-" with blanks.
	 */
	private String guionesPorEspacios(String cadena){
		return cadena.replace( '_', ' ');
	}
	
	/**
	 * Private int method. We use it for parsing int numbers.
	 * @param num_parseo
	 * @return Integer.parseInt(int)
	 * @throws WrongCityFormatException
	 */
	private int parseEntero(String num_parseo)throws WrongCityFormatException{
		try{
			return Integer.parseInt(num_parseo);
		}catch(IllegalArgumentException e){
			throw new WrongCityFormatException();
		}
	}
}
