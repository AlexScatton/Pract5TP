package tp.pr5;

import tp.pr5.items.CodeCard;


/**
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * A street links two places A and B in one direction. 
 * If it is defined as Street(A,NORTH,B) it means that Place B is at NORTH of Place A. 
 * Streets are two-way streets, i.e. if B is at NORTH of A then A is at SOUTH of B.
 * Some streets are closed and a code (contained in a code card) is needed to open them.
 */
public class Street {
	/**
	 * Atributos de la clase Street*/
	private Place source;
	private Direction direction;
	private Place target;
	private boolean door;
	private String code;
	
	/**
	 * Creates an open street and it have not any code to open or close it
	 * @param source2 - Source place
	 * @param direction2 - Represents how is placed the target place 
	 * with respect to the source place.
	 * @param target2 - Target place
	 */
	public Street(Place source2, Direction direction2, Place target2){
		this.source = source2;
		this.direction = direction2;
		this.target = target2;
		this.door = true;
		this.code = null;
	}
	
	/**
	 * Creates a street that has a code to open and close it
	 * @param source - Source place
	 * @param direction - Represents how is placed the target place with respect 
	 * to the source place.
	 * @param target - Target place
	 * @param isOpen - Determines is the street is opened or closed
	 * @param code - The code that opens and closes the street
	 */
	public Street(Place source, Direction direction, Place target, boolean isOpen, java.lang.String code) {
		this.source = source;
		this.direction = direction;
		this.target = target;
		this.door = isOpen;
		this.code = code;
	}
	
	/**
	 * Checks if the street comes out from a place in a given direction. 
	 * Remember that streets are two-way
	 * @param place - The place to check
	 * @param whichDirection - Direction used.
	 * @return true if the street comes out from the input Place.
	 */
	public boolean comeOutFrom(Place place, Direction whichDirection){
		//declaraciones
		boolean retorno = false;
		
		if( place.equals(this.source)){
			retorno = (whichDirection.equals(this.direction));
		
		}else if(place.equals(this.target)){	
			//comprobamos si el destino es donde estamos entonces comparamos 
			//las rutas del mapa alreves
			retorno = this.direction.equals(whichDirection.opposite());	
			
		}
		return retorno;
	}
	
	/**
	 * Returns the place of the other side from the place whereAmI. 
	 * This method does not consider whether the street is open or closed.
	 * @param whereAmI - The place where I am.
	 * @return It returns the Place at the other side of the street 
	 * (even if the street is closed). Returns null if whereAmI does not belong to the street.
	 */
	public Place nextPlace(Place whereAmI){
		Place retorno = null;
		if(this.source == whereAmI){
			retorno = this.target;
		}else if(this.target == whereAmI){
			retorno = this.source;
		}
		return retorno;
	}

	/**
	 * Checks if the street is open or closed
	 * @return true, if the street is open, and false when the street is closed
	 */
	public boolean isOpen() {
		return this.door;
	}
	
	/**
	 * Returns A String with the code.
	 * @return A String with the code.
	 */
	public String getCode() {
		return this.code;
	}
	
	/**
	 * It open or close the door of the Street.
	 * @param door - boolean that indicates if the door is open or close.
	 */
	public void setDoor(boolean door) {
		this.door = door;
	}

	/**
	 * Tries to open a street using a code card. Codes must match in order to complete this action
	 * @param card - A code card to open the street
	 * @return true if the action has been completed
	 */
	public boolean open(CodeCard card) {
		 //declaraciones
		boolean retorno = false;
		
		 if(card.getCode().equalsIgnoreCase(this.getCode())){
			 //se hace refenrencia a calle para usar el metodo OPEN
			 this.setDoor(true);
			 retorno = true;
		 }
		 return retorno;
	}

	/**
	 * Tries to close a street using a code card. Codes must match in order to complete this action
	 * @param card - A code card to close the street
	 * @return  true if the action has been completed
	 */
	public boolean close(CodeCard card) {
		//declaraciones
		boolean retorno = false;
		 if(card.getCode().equalsIgnoreCase(this.getCode())){
			 //se hace refenrencia a calle para usar el metodo OPEN
			 this.setDoor(false);
			 retorno = true;
		 }
		 return retorno;
	}
}
