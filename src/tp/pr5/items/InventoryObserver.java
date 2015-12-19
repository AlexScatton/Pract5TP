package tp.pr5.items;

public interface InventoryObserver {
	/**
	 * Notifies that the container has changed
	 * */
	void inventoryChange(java.util.List<Item> inventory);
	
	/**
	 * Notifies that the user requests a SCAN instruction over the inventory.
	 * */
	void inventoryScanned(java.lang.String inventoryDescription);
	
	/**
	 * Notifies that the user wants to scan an item allocated in the inventory
	 * */
	void itemScanned(java.lang.String description);
	
	/**
	 * Notifies that an item is empty and it will be removed from the container. An invocation to the inventoryChange method will follow.
	 * */
	void itemEmpty(java.lang.String itemName);
}
