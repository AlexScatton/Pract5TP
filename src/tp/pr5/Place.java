package tp.pr5;

import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;


/**
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * It represents a place in the city. Places are connected by streets according to the 4 
 * compass directions, North, East, South and West. 
 * Every place has a name, a textual description about itself, a list of items contained 
 * in the place, and a tag to discover if the place is the space ship. 
 * The description and the list of items are displayed when the robot arrives at the place.
 * A place can represent the spaceship where the robot is safe. When the robot arrives 
 * at this place, the application is over.
 */
public class Place implements PlaceInfo {
	/**
	 * Attributes
	 */
	private String name;
	private String description;
	private boolean isSpaceShip;
	private ItemContainer contenedor;

	/**
	 * Place Constructor.
	 * Creates the place. Initially the list of items is empty
	 * @param nombre - Place name
	 * @param esnave - Is it a spaceship?
	 * @param descipcion - place description.
	 */
	public Place(java.lang.String nombre, boolean esnave, java.lang.String descipcion){
		this.name = nombre;
		this.description = descipcion;
		this.isSpaceShip = esnave;
		this.contenedor = new ItemContainer();
	}
	
	/**
	 * Is this place the space ship?
	 * @return true if the place represents a space ship
	 */
	public boolean isSpaceship(){
		return this.isSpaceShip;
	}
	
	/**
	 * Overrides toString method. Returns the place name, its description and the 
	 * list of items contained in the place
	 */
	public java.lang.String toString(){
		//declaraciones
		String tmp_mostrar = "";
		
		//si no tiene elementos el contenedor de place
		if(this.contenedor.numberOfItems() == 0){
			tmp_mostrar = "The place is empty. There are no objects to pick";
		//contiene elementos el contenedor de place
		}else{
			tmp_mostrar = "The place contains these objects:" + Interpreter.LINE_SEPARATOR 
					+ this.contenedor.toString();
		}
	
		return (this.name + Interpreter.LINE_SEPARATOR + this.description + Interpreter.LINE_SEPARATOR 
				+ tmp_mostrar);	
	}
	
	/**
	 * Tries to pick an item characterized by a given identifier from the place. 
	 * If the action was completed the item must be removed from the place.
	 * @param id - The identifier of the item
	 * @return The item of identifier id if it exists in the place. 
	 * Otherwise the method returns null
	 */
	public Item pickItem(java.lang.String id){
		return this.contenedor.pickItem(id);
	}
	
	/**
	 * Tries to add an item to the place. The operation can fail 
	 * (if the place already contains an item with the same name)
	 * @param it - The item to be added
	 * @return true if and only if the item can be added to the place, i.e., 
	 * the place does not contain an item with the same name
	 */
	public boolean addItem(Item it){
		//declaraciones
		boolean retorno = false;
		
		//buscamos si existe el objeto en places
		if(this.contenedor.getItem(it.getId()) == null){
			//si no lo ha encontrado lo añadimos al contenedor sino no
			return this.contenedor.addItem(it);
		}
		return retorno;
	}
	
	/**
	 * Drop an item in this place. The operation can fail, returning false
	 * @param item - The name of the item to be dropped.
	 * @return true if and only if the item is dropped in the place, i.e., 
	 * an item with the same identifier does not exists in the place
	 * */
	public boolean dropItem(Item it){
		//buscamos si existe un elemento en el contenedor y sino esta en el contenemos 
		//retornamos si se a podido añadir correctamente
			return this.contenedor.addItem(it);	
	}
	
	/**
	 * Checks if an item is in this place
	 * @param id - Identifier of an item
	 * @return true if and only if the place contains the item identified by id
	 * */
	public boolean existItem(String id){
		return this.contenedor.containsItem(id);
	}

	/**
	 * Getter method of the item name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Getter method of the item description .
	 */
	@Override
	public String getDesciption() {
		return this.description;
	}
	
}
