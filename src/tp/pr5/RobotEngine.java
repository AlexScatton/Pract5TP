package tp.pr5;

import tp.pr5.Observable;
import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.ItemContainer;

/**
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * This class represents the robot engine. It controls robot movements by processing 
 * the instructions introduced with the keyboard. 
 * The engine stops when the robot arrives at the space ship, runs out of fuel or 
 * receives a quit instruction.
 * The robot engine is also responsible for updating the fuel level and the recycled 
 * material according to the actions 
 * that the robot performs in the city.
 * The robot engine also contains an inventory where the robot stores the items that 
 * it collects from the city
 */
public class RobotEngine extends Observable<RobotEngineObserver>{
	
	/**
	 * Street attributes.
	 */
	private static int combustible;
	private static int basura;
	private ItemContainer materiales;
	private NavigationModule nav;
	private boolean exit;
	
	/**
	 * Creates the robot engine in an initial place, facing an initial direction and 
	 * with a city map. 
	 * Initially the robot has not any items or recycled material but it has an initial 
	 * amount of fuel(50).
	 * @param cityMap - The city where the robot wanders
	 * @param initialPlace - The place where the robot starts
	 * @param direction - The initial direction where the robot is facing.
	 */
	public RobotEngine(City cityMap, Place initialPlace, Direction direction ){	
		RobotEngine.combustible = 100;
		RobotEngine.basura = 0;
		this.materiales = new ItemContainer();
		this.nav = new NavigationModule(cityMap ,initialPlace);
		this.exit = false;
	}

	/**
	 * It executes an instruction. The instruction must be configured with the context 
	 * before executing it. It controls the end of the simulation. 
	 * If the execution of the instruction throws an exception, then the corresponding 
	 * message is printed
	 * @param c - The instruction to be executed
	 * */
	public void communicateRobot(Instruction c){

			try {
				//traspasamos los datos del robot a navigation
				c.configureContext(this, nav, materiales);
				try {
					//ejecucion de instrucciones
					c.execute();
					
				} catch (InstructionExecutionException e) {
					this.requestError(e.getMessage());
				}
			} catch (WrongInstructionFormatException e) {
			}
	}
	
	/**
	 * Public method that makes the request of an error. 
	 * It calls to raiseError method from RobotEngineObserver interface.
	 */
	public void requestError(String msg){
	    for(RobotEngineObserver obs: this.observers){
			obs.raiseError(msg);
		}
	}
	
	/**
	 * Public method that makes the request of start. 
	 * It calls to requestRobotStatus method from RobotEngineObserver interface.
	 */
	public void requestStart(){
	   this.nav.requestStartNavigation();
	   this.requestRobotStatus();
	}
	
	/**
	 * Public method that makes the request of Help instruction. 
	 * It calls to communicationHelp method from RobotEngineObserver interface.
	 */
	public void requestHelp(){
		for(RobotEngineObserver obs: this.observers){
			obs.communicationHelp(Interpreter.interpreterHelp());
		}
	}
	
	/**
	 * Public method that makes the request of the quit instruction. 
	 * It calls to communicationCompleted method from RobotEngineObserver interface.
	 */
	public void requestQuit(){
		this.exit = true;
		for(RobotEngineObserver obs: this.observers){
			obs.communicationCompleted();
		}
	}

	
	/**
	 * Returns the current fuel level of the robot.
	 * @return  The current fuel level of the robot
	 */
	public static int getFuel(){
		return combustible;
	}
	
	/**
	 * Returns the current weight of recycled material that the robot carries.
	 * @return The current recycled material of the robot
	 */
	public static int getRecycledMaterial(){
		return basura;
	}

	/**
	*Checks if the simulation is finished
	*/
	public boolean isOver(){
		return (RobotEngine.combustible <= 0 ||this.nav.atSpaceship() || this.exit);
	}

	/**
	 * Register a NavigationObserver to the model
	 */
	public void addNavigationObserver(NavigationObserver robotObserver){
		this.nav.addObserver(robotObserver);
	}
	
	/**
	 * Registers an EngineObserver to the model
	 */
	public void addEngineObserver(RobotEngineObserver observer){
		this.addObserver(observer);
	}
	
	/**
	*Registers an ItemContainerObserver to the model
	*/
	public void addItemContainerObserver(InventoryObserver c){
		this.materiales.addObserver(c);
	}
	
	/**
	 * It shows the prompt on console.
	 */
	public void robotPromtp() {
		System.out.print("WALL·E> ");
	}
	
	/**
	 * Shows "walle says"
	 * @return
	 */
	public static String WallSay(){
		return "WALL·E says: ";
	}

	/**
	 * Public method that makes the request of the robot status. 
	 * It calls to robotUpdate method from RobotEngineObserver interface.
	 */
	public void requestRobotStatus(){
		for(RobotEngineObserver obs: this.observers){
			obs.robotUpdate(RobotEngine.combustible, RobotEngine.basura);
		}
	}

	
	/**
	 * Public method that makes the request of Engine off of the robot. 
	 * If the robot arrives the spaceship or is out of fuel it calls to engineOff
	 * method from RobotEngineObserver interface.
	 */
	public void requestEngineOff() {
		if( this.nav.atSpaceship()){
			 for(RobotEngineObserver obs: this.observers){
				obs.engineOff(true);
			 }
		 }else if(RobotEngine.combustible <=0){
			 for(RobotEngineObserver obs: this.observers){
				obs.engineOff(false);
			 }	 
		 }
		// caso de no ser ninguno no se mostraria nada
	}

	/**
	 * Public method that makes the request of say something. 
	 * It calls to robotSays method from RobotEngineObserver interface.
	 */
	public void saySomething(String message) {
		  for(RobotEngineObserver obs: this.observers){
			  obs.robotSays(RobotEngine.WallSay()+ message);
		  }	
	}

	/**
	 * Increases the level of fuel of the robot.
	 * @param fuel
	 */
	public void addFuel(int fuel){
		RobotEngine.combustible = RobotEngine.combustible + fuel;
	}
	
	/**
	 * Increases the amount of recycled material of the robot.
	 * @param weight - Amount of recycled material
	 */
	public void addRecycledMaterial(int weight){
		RobotEngine.basura = RobotEngine.basura + weight;
	}
}
