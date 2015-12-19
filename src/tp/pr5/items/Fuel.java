package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;


/**
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * An item that represents fuel. 
 * This item can be used at least once and it provides power energy to the robot. 
 * When the item is used the configured number of times, 
 * then it must be removed from the robot inventory.
 */
public class Fuel extends Item {
	
	//atributos
	protected int power;
	protected int times;
	
	/**
	 * Fuel constructor
	 * @param id - Item id
	 * @param description - Item description
	 * @param power - The amount of power energy that this item provides the robot
	 * @param times - Number of times the robot can use the item
	 */
	public Fuel(java.lang.String id, java.lang.String description, int power, int times){
		super(id, description);
		this.power = power;
		this.times =  times;
	}
	
	/**
	 * Fuel can be used as many times as it was configured
	 * @return true if the item still can be used
	 */
	public boolean canBeUsed(){
		return (this.times > 0);
	}
	
	/**
	 * Using the fuel provides energy to the robot (if it can be used).
	 * @param r : robot that is going to use the fuel.
	 * @param p : place where the fuel is going to be used.
	 * @return true if the fuel has been used
	 */
	public boolean use(RobotEngine r, NavigationModule nav){
		//declaraciones
		boolean retorno = false;
		
		if(this.times > 0){
			//añadimos y restamos un uso
			r.addFuel(this.power);
			this.times -= 1; 
			
			retorno = true;
		}
		return retorno;
	}
	
	/**
	 * Method equals inherited from class Object.
	 * @param op - item
	 * @return true or false depending equals
	 */
	public boolean equals(Item op){
		//declaraciones
		boolean retorno = false;
		if (op.getClass()==Fuel.class){
			retorno = super.equals(op);
		}
		return retorno;
	}
	
	
	public java.lang.String getDescription(){
		return ( super.getDescription()) ;
	}
	
	/**
	 * Method use it to show the information about the FUEL.
	 */
	public java.lang.String getMostrar(){
		return ( super.getDescription()+ "// power = " + this.power 
				+ ", times = " + this.times );
	}
}
