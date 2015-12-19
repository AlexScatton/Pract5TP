package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.Street;

/**
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * An item that represents a code card. 
 * This item contains a code that can be employed to open and close streets.
 * It always remains in the robot inventory after using it.
 */
public class CodeCard extends Item {

	//atributos
	protected String code;
	
	/**
	 * Code card constructor with parameters
	 * @param id
	 * @param description
	 * @param code
	 */
	public CodeCard (String id, String description, String code){
		super(id, description);
		this.code = code;
	}
	
	/**
	 * A code card always can be used. 
	 * Since the robot has the code card it never loses it.
	 * @return true , because it always can be used
	 */
	public boolean canBeUsed(){
		return true;
	}
	
	/**
	 * The method to use a code card. 
	 * If the robot is in a place which contains a street in the direction he is looking at,
	 * then the street is opened or closed if the street code and the card code match.
	 * @param r - the robot engine employed to use the card.
	 * @param p - the place where the card is going to be used.
	 * @return true If the code card can complete the action of opening or closing a street. Otherwise it returns false.
	 */

	public boolean use(RobotEngine r, NavigationModule nav){
		//declaraciones
		boolean retorno = false;
		Street calle = nav.getHeadingStreet();
		 
		 if(calle != null){
			 if(!calle.isOpen()){
				 //proceso de eliminar puerta
				 retorno = calle.open(this);
			 }else{
				 //no hay puerta que abrir
				 retorno = calle.close(this);
			 }
		 }
		 return retorno;
	}
	
	/**
	 * Gets the code stored in the code card.    
	 * @return A String that represents the code.
	 */
	public String getCode(){
		return this.code;
	}
	
	/**
	 * Public method used to show the code card.
	 */
	public java.lang.String getDescription(){
		return super.getDescription();
	}
	
	public java.lang.String getMostrar(){
		return super.getDescription();
	}
	
}
