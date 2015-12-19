package tp.pr5.items;

import tp.pr5.items.Item;
import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;

/**
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * The superclass of every type of item.
 * It contains the common information for all the items and 
 * it defines the interface that the items must match
 */
public abstract class Item {
	
	protected String id;
	protected String description;
	
	/**
	 * Item Constructor
	 * Builds an item from a given identifier and description
	 * @param id
	 * @param desciption
	 */
	public Item(String id, String desciption){
		this.id = id;
		this.description = desciption;
	}
	


	/**
	 * Checks if the item can be used. Subclasses must override this method
	 * @return true if the item can be used
	 */
	public abstract boolean canBeUsed();

	/**
	 * Try to use the item with a robot in a given place.
	 * It returns whether the action was completed. Subclasses must override this method
	 * @param r  - The robot that uses the item
	 * @param p - The Place where the item is used
	 * @return true if the action was completed. Otherwise, it returns false
	 */
	public abstract boolean use(RobotEngine r, NavigationModule nav);

	/**
	 * Return the item identifier
	 * @return A String item identifier
	 */
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 * Generates a String with the Item description
	 * @return A String item description.
	 */
	
	/****************************SI NO ENTRA AQUI QUITARLO sino comprobar referencias*****************************************/
	public java.lang.String toString(){
		return this.getDescription();
	}
	
	/**
	 * Method equals inherited from class Object.
	 * @param id
	 * @return true or false depending equals
	 */
	public boolean equals(Object item){
	/*	String var1 = this.getId().toLowerCase();
		String var2 = ((Item) item).getId().toLowerCase();
		boolean salir1 = false;
		boolean salir2 = false;
		
		//return(var1.compareToIgnoreCase(var2) == 0);
		if(item instanceof Item){
			salir1 = true;
		}
		if(var1.equalsIgnoreCase(var2)){
			salir2 = true;
		}
		return salir1 && salir2;*/
		String var1 = this.getId().toLowerCase();
		String var2 = ((Item) item).getId().toLowerCase();
		
		//return(var1.compareToIgnoreCase(var2) == 0);
		return( item instanceof Item && var1.equalsIgnoreCase(var2));
	}	
	
	
	/**
	 * Public method compare to the item a index to id object to localizace
	 * */
	public int compareToIgnoreCase(Item it) {
		String var1 = this.id.toLowerCase();
		String var2 = it.getId().toLowerCase();
		
		return(var1.compareToIgnoreCase(var2));
	}
	
	/**
	 * Public method compare to the item a index to id object to localizace
	 * */
	public int compareToIgnoreCase(String id2) {
		String var1 = this.id.toLowerCase();
		String var2 = id2.toLowerCase();
		
		return(var1.compareToIgnoreCase(var2));
	}
	
	/**
	 * Public method compare to the item a index to id object to localization
	 * */
	//caso ordenar
	public boolean compareToOrdenar(Item item){
		
		return (this.compareToIgnoreCase(item.getId()) <= 0);
		//return(var1.compareToIgnoreCase(var2) <= 0);
	}
	
	//Get y Set de Descripcion
	public String getDescription() {
		return this.description;
	}

	public String getMostrar(){
		return this.description;
	}

	
	/**
	 * Public method use it to show the item (id + description).
	 * @return
	 */
/*	public String Mostrar(){
		return this.description;
	}*/
}
