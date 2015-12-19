package tp.pr5.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import tp.pr5.Observable;
import tp.pr5.Interpreter;
import tp.pr5.gui.RobotPanel;


/**
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * A container of items. It can be employed by any class that stores items. 
 * A container cannot store two items with the same identifier
 * It provides methods to add new items, access them and remove them from the container.
 */
public class ItemContainer extends Observable<InventoryObserver>{
	//declaraciones
	private List<Item> listaitem;
	
	/**
	 * Creates the empty container
	 */
	public ItemContainer(){		
		this.listaitem = new ArrayList<Item>();	
	}

	/**
	 * Returns the number of items contained
	 * @return number of items contained
	 */
	public int numberOfItems(){
		return this.listaitem.size();
	}
	
	/**
	 * Add an item to the container. The operation can fail, returning false.
	 * @param item - The name of the item to be added.
	 * @return true if and only if the item is added, i.e., 
	 * an item with the same identifier does not exists in the container.
	 */
	public boolean addItem(Item item){
		//declaraciones
		boolean retorno = false;
		int i = 0;
		
		if(!this.containsItem(item.id) ){
			//no encontrado
			Iterator<Item> it = listaitem.iterator();
			while (it.hasNext() && ((Item)it.next()).compareToOrdenar(item))
				i++;
	
			((ArrayList)this.listaitem).add(i, item);
			retorno = true;
		}
		return retorno;
	}
	
	/**
	*implements the interface comparator internally to the class Itemcontainer
	*/
	public class Compare implements Comparator<Object>{
		public Compare(){

		}
		/**
		 * compare items whit the id to localice
		 * */
		public int compare(Object arg0, Object arg1) {
			return((Item)arg0).compareToIgnoreCase(((String) arg1) );
		}
	}
	
	/**
	 * It removes an item from the list, checking if the size has changed.
	 * @param item - the item we want to delete.
	 * @return true if the size of the list has changed. Otherwise false.
	 */
	public boolean delItem(Item item){
		//Item objeto = null;

		int index =Collections.binarySearch(this.listaitem, item.getId(), new Compare());
		if(index >= 0){
		//	objeto = this.listaitem.get(index);
			this.listaitem.remove(index);
		}
		return item != null;
	}
	
	/**
	 * Returns the item from the container according to the item name
	 * @param id - Item name
	 * @return Item with that name or null if the container does not store an item with that name.
	 */
	public Item getItem(java.lang.String id){
		Item objeto = null;
		
	    int index = Collections.binarySearch(this.listaitem, id, new Compare());
		
	    if(index >= 0){
	    	objeto = this.listaitem.get(index);
	    }
		return objeto;
	}
	
	/**
	 * Returns and deletes an item from the inventory. This operation can fail, returning null
	 * @param id - Name of the item
	 * @return An item if and only if the item identified by id exists in the inventory. 
	 * Otherwise it returns null
	 */
	public Item pickItem(java.lang.String id){
		//declaraciones
		Item objeto = null;
		
		int index = Collections.binarySearch(this.listaitem, id, new Compare());
		
		if(index >= 0){
			objeto = this.listaitem.get(index);
			this.listaitem.remove(index);
		}
		return objeto;
	}
	
	/**
	 * Generates a String with information about the items contained in the container. 
	 * Note that the items must appear sorted but the item name.
	 */
	public java.lang.String toString(){
		//declaraciones
		String aux = "";
		Iterator<Item> it = this.listaitem.iterator();
		//mostramos los objetos del contenedor
		for(int i = 0; i < this.listaitem.size(); i++){
			aux = aux + ("   " + ((Item)it.next()).getId() );
			//if( i < this.listaitem.size()-1){
				aux = aux + Interpreter.LINE_SEPARATOR;
			//}
		}
		return aux;
	}
	
	/**
	 *Checks if the Item with this id exists in the container.
	 *@param id - Name of the item.
	 *@return true if the container as an item with that name.
	 * */
	public boolean containsItem(java.lang.String id){

		int index = Collections.binarySearch(this.listaitem, id, new Compare());
		return index >= 0;
		}
	
	/**
	 * Use an item, removing the item from the inventory if the item 
	 * can not be used any more
	 * @param item - to be used 
	 * */
	public void useItem(Item it){
		this.pickItem(it.getId());
	}

	/**
	 * Public method that makes the request of the item empty. It calls to 
	 * itemEmpty method from InventoryObserver interface.
	 */
	public void requestItemEmpty(String id) {
		  for(InventoryObserver obs: this.observers){
			  obs.itemEmpty(id);
		  }
	}

	/**
	 * Public method that makes the request of the inventory has changed. It calls to inventoryChange
	 * method from InventoryObserver interface.
	 */
	public void requestInventoryChange() {
		  for(InventoryObserver obs: this.observers){
			  obs.inventoryChange(this.listaitem);
		  }
	}

	/**
	 * Public method that makes the request inventory scanned. It calls to inventoryScanned
	 * method from InventoryObserver interface.
	 */
	public void requestInventoryScanned() {
		  for(InventoryObserver obs: this.observers){
			  obs.inventoryScanned(this.toString());
		  }
	}
	
	/**
	 * Public method that makes the request scan item. It calls to itemScanned
	 * method from InventoryObserver interface.
	 */
	public void requestScanItem(java.lang.String id) {
	    for(InventoryObserver obs: this.observers){
			 obs.itemScanned(id);
		}
    }
	
	/**
	 * Public method that makes the request of scan collection. It calls to inventoryChange
	 * method from InventoryObserver interface.
	 */
	public void requestScanCollection() {
	    for(InventoryObserver obs: this.observers){
	    	obs.inventoryChange(this.listaitem);
	    }
	}
}
