package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;


/**
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * The garbage is a type of item that generates recycled material after using it. 
 * The garbage can be used only once. After using it, 
 * it must be removed from the robot inventory
 */
public class Garbage extends Item {
	
	//atributo
	protected int recycled;
	protected int times;
	
	/**
	 * Garbage constructor
	 * @param id - Item id
	 * @param description -  Item description
	 * @param recycledMaterial - The amount of recycled material that the item generates
	 */
	public Garbage(java.lang.String id, java.lang.String description, int recycledMaterial){
		super(id, description);
		this.recycled = recycledMaterial;
		this.times = 1;
	}
	
	/**
	 * Garbage can be used only once.
	 * @return true if the item has not been used yet.
	 */
	public boolean canBeUsed(){	
		return this.times > 0;
	}
	
	/**
	 * The garbage generates recycled material for the robot that uses it
	 * @param r - the robot that uses the item
     * @param p - The place where the item is used
     * @return true if the garbage was transformed in recycled material
	 */
	public boolean use(RobotEngine r, NavigationModule nav){
		r.addRecycledMaterial(this.recycled);
		this.times--;
		return this.times == 0;
	}
	
	/**
	 * Method equals inherited from class Object.
	 * @param op
	 * @return true or false depending equals
	 */
	public boolean equals(Item op){
		//declaraciones
		boolean retorno = false;
		
		if (op.getClass()==Garbage.class){
			retorno = super.equals(op);
		}
		return retorno;
	}
	
	/**
	 * Method used to show the garbage + power.
	 */
	public java.lang.String getDescription(){
		return( super.getDescription());
	}
	
	public java.lang.String getMostrar(){
		return( super.getDescription() + "// power = " + this.recycled);
	}
}
