package tp.pr5.console;

import java.util.List;
import tp.pr5.Direction;
import tp.pr5.Interpreter;
import tp.pr5.NavigationObserver;
import tp.pr5.PlaceInfo;
import tp.pr5.RobotEngine;
import tp.pr5.RobotEngineObserver;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.Item;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * Public class, it implements the Navigation, RobotEngine and Inventory Observers interfaces, this interfaces
 * have all the events that can occur. Responsible of console functionality.
 * All the overrides methods has their documentation in his interface.
 */
public class Console implements NavigationObserver, RobotEngineObserver, InventoryObserver{
	
	/**
	 * Default constructor
	 */
	public Console(){
		
	}

	@Override
	public void inventoryScanned(String inventoryDescription) {
		System.out.println(inventoryDescription);
	}

	@Override
	public void itemScanned(String description) {
		System.out.println(description);
	}

	@Override
	public void itemEmpty(String itemName) {
		System.out.println("What a pity! I have no more " + 
				itemName + " in my inventory");
	}

	@Override
	public void raiseError(String msg) {
		System.out.println(msg);
	}

	@Override
	public void communicationHelp(String help) {
		System.out.println(help);
	}

	@Override
	public void engineOff(boolean atShip) {
		if(atShip){
			this.robotSays(RobotEngine.WallSay()+ "I am at my spaceship. Bye Bye");
		}else{
			this.robotSays(RobotEngine.WallSay()+ "I run out of fuel. " +
					"I cannot move. Shutting down...");
		}
		System.exit(0);
	}

	@Override
	public void communicationCompleted() {

	}

	@Override
	public void robotUpdate(int fuel, int recycledMaterial) {
		System.out.println("      * My power is " + fuel + Interpreter.LINE_SEPARATOR +
				"      * My reclycled material is " + recycledMaterial);
	}

	@Override
	public void robotSays(String message) {
		System.out.println(message);
	}

	@Override
	public void headingChanged(Direction newHeading) {
		System.out.println("WALL·E is looking at direction " + newHeading);
	}

	@Override
	public void initNavigationModule(PlaceInfo initialPlace, Direction heading) {
		System.out.println( initialPlace.toString());
		System.out.println("WALL·E is looking at direction " + heading );
	}

	@Override
	public void robotArrivesAtPlace(Direction heading, PlaceInfo place) {
		System.out.println(RobotEngine.WallSay()+ "Moving in direction " + heading);
		//this.initNavigationModule(place, heading);
		System.out.println(place.toString());
	}

	@Override
	public void placeScanned(PlaceInfo placeDescription) {
		System.out.println(placeDescription.toString());
	}

	@Override
	public void placeHasChanged(PlaceInfo placeDescription) {
		//metodo que se utiliza mostrar que se a producido un cambio en el place en el pick o cualquier otro
		//System.out.println(placeDescription.toString());
	}

	@Override
	public void inventoryChange(List<Item> inventory) {
		// En caso de que queramos mostrar los elementos de la lista cuando se borre por el ooperate o drop
		//System.out.println(inventory.toString());
	}
}
