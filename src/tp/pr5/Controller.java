package tp.pr5;

import tp.pr5.items.InventoryObserver;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * An abstract class Controller. Defines the basic functionality common to the two controllers.
 *
 */
public abstract class Controller {
	protected RobotEngine engine;
	
	/**
	 * Constructor that uses the model
	 * */
	public Controller(RobotEngine game){
		this.engine = game;
	}
	
	/**
	 * Abstract method that runs the game. This method is different whether the application runs the game on console or on a Swing Window
	 * */
	public abstract void startController();
	
	/**
	 * Registers a GameObserver to the model*/
	public void registerEngineObserver(RobotEngineObserver gameObserver){
		this.engine.addEngineObserver(gameObserver);
	}
	
	/**
	 * Registers a MapObserver to the model
	 * */
	public void registerItemContainerObserver(InventoryObserver containerObserver){
		this.engine.addItemContainerObserver(containerObserver);
	}
	
	/**
	 * Registers a PlayerObserver to the model
	 * */
	public void registerRobotObserver(NavigationObserver playerObserver){
		this.engine.addNavigationObserver(playerObserver);
	}
}
