package tp.pr5;


/**
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * This class represents the city where the robot is wandering. 
 * It contains information about the streets and the places in the city
 */
public class City {
	//Atributos
	private Street [] mapacity;	
	private int numElem;
	final static int SIZE_INI = 11;
	private int size;
	
	/**
	 * Default constructor
	 */
	public City (){
		this.mapacity = new Street[SIZE_INI];
		this.numElem = 0;
		this.size = SIZE_INI;
	}
	
	/**
	 * Constructor with parameter.
	 * Creates a city using an array of streets.
	 * @param cityMap
	 */
	public City (Street[] cityMap){
		this.mapacity = cityMap;
		this.numElem = this.mapacity.length;
		this.size = SIZE_INI;
	}
	
	/**
	 * Looks for the street that starts from the given place in the given direction.
	 * @param currentPlace
	 * @param currentHeading
	 * @return Street retorno : the street that starts from the given place in 
	 * the given direction. It returns null if there is not any street in this direction 
	 * from the given place
	 */
	public Street lookForStreet(Place currentPlace, Direction currentHeading){
		
		//declaraciones
		boolean encontrado = false;
		Street retorno = null;
		int i = 0;
		
		//busqueda de calle
		while( i < this.numElem && !encontrado){
			
			if(!(this.mapacity[i].comeOutFrom(currentPlace, currentHeading))){
				
			}else {
				encontrado = true;
				retorno = this.mapacity[i];
			}
			if(!encontrado){
				i++;
			}
		}
		return retorno;
		
	}
	
	 /**
	  * Method that returns an array of Streets
	  * @return Street[] mapacity
	  */
	public Street[] getStreet(){
		return this.mapacity;
	}
	
	/**
	 * Adds an street to the city
	 * @param street - The street to be added
	 */
	public void addStreet(Street street){
		//añade una calle a la ciudad

		//redimensionar
		if(this.size == this.numElem){
			this.redimensionar();
		}
		
		this.mapacity[this.numElem] = street;
		this.numElem++;
	}
	
	/**
	 * private method. We called it when the size of the Array is not enough.
	 * It creates a new array that is a copy of the original but bigger size.
	 */
	private void redimensionar(){

		this.size = this.size + City.SIZE_INI;
		Street[]lista_aux = new Street[this.size];
		for(int i  = 0; i< numElem; i++){
			lista_aux[i] = this.mapacity[i];
		}
		this.mapacity = lista_aux;
	 }

	/**
	 * Getter method that returns the number of elements.
	 * @return the number of elements
	 */
	public int getNumElem() {
		return numElem;
	}	
}
