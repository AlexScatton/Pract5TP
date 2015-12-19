package tp.pr5;

import tp.pr5.Observable;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.items.Item;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 *
 */
public class NavigationModule extends Observable<NavigationObserver>{
	//declaraciones
	private City city;
	private Place inicial;
	private Direction direction;
	
	/**
	 * Constructor with a city and an Initial place.
	 * @param aCity
	 * @param initialPlace
	 */
	public NavigationModule(City aCity, Place initialPlace){
		this.city = aCity;
		this.inicial = initialPlace;
		this.direction = Direction.NORTH;
	}
	
	/**
	 * Checks if the robot has arrived at a spaceship
	 * @return true if an only if the current place is the spaceship
	 * */
	public boolean atSpaceship(){
		return this.inicial.isSpaceship();
	}
	
	/**
	 * Drop an item in the current place. It assumes 
	 * that there is no other item with the same name/id there. Otherwise, 
	 * the behaviour is undefined.
	 * @param item - The name of the item to be dropped.
	 * */  
	public void dropItemAtCurrentPlace (Item it){
		this.inicial.dropItem(it);
	}

	/**
	 * Checks if there is an item with a given id in this place
	 * @param id - Identifier of the item we are looking for
	 * @return true if and only if an item with this id is in the current place
	 * */
	public boolean findItemAtCurrentPlace(String id){
		return this.inicial.existItem(id);
	}

	/**
	 * Returns the street opposite the robot
	 * @return The street which the robot is facing, or null, if there is not any 
	 * street in this direction
	 * */
	public Direction getCurrentHeading(){
		//retorna la direccion a la que mira el robot.
		return direction;
	}
	
	/**
	 * Returns the current place where the robot is (for testing purposes)
	 * @return The current place
	 * */
	public Place getCurrentPlace(){
		//retorna el place donde se encuentra el robot
		return this.inicial; 
	}
	
	/**
	 * Returns the street opposite the robot
	 * @return The current place
	 * */
	public Street getHeadingStreet(){
		//retorno si en esa direccion del lugar continua la calle con otro lugar
		return this.city.lookForStreet(this.inicial, this.direction);
	}

	/**
	 * Initializes the current heading according to the parameter
	 * @param heading - New direction for the robot
	 * */
	public void initHeading(Direction heading){
		this.direction = heading;
	}
	
	/**
	 * The method tries to move the robot following the current direction. 
	 * If the movement is not possible because there is no street, or there is a street 
	 * which is closed, then it throws an exception. Otherwise the current place is 
	 * updated according to the movement
	 * @throws InstructionExecutionException - An exception with a message about 
	 * the encountered problem
	 * */
	public void move()throws InstructionExecutionException {
		//declaraciones
		Street calle = this.getHeadingStreet();

		//no hay calle
		if(calle == null){
			throw new InstructionExecutionException("There is no street in " +
					"direction " + this.direction );
		}else{
			//si la calle esta abierta
			if(calle.isOpen()){
				this.inicial = calle.nextPlace(this.inicial);
			}else{
				throw new InstructionExecutionException("Arrggg, there is " +
						"a street but it is closed!");
			}
		}		
	}
	
	/**
	 * Tries to pick an item characterized by a given identifier from the current place. 
	 * If the action was completed the item is removed from the current place.
	 * @param id - The identifier of the item
	 * @return The item of identifier id if it exists in the place. Otherwise the method 
	 * returns null
	 * */
	public Item pickItemFromCurrentPlace(String id){
		// cogemos en el place el objeto por ID
		return this.inicial.pickItem(id);
	}
	
	/**
	 * Updates the current direction of the robot according to the rotation
	 * @param rotation - left or right
	 * */
	public void rotate (Rotation rotation){
		//actualiza el valor de direction en cuanto a la rotacion.		
		if( Rotation.LEFT == rotation){
			this.direction = this.direction.GirarIZQ();
			
			//si rotamos a la der
		}else if (Rotation.RIGHT == rotation){
			this.direction = this.direction.GirarDER();
		}
	}

	/**
	 * Prints the information (description + inventory) of the current place
	 */
	public void scanCurrentPlace(){
		for(NavigationObserver obs:this.observers){
		    obs.placeScanned(this.inicial);
		}
	}

	/**
	 * Public method that makes the request of the start navigation. It calls to initNavigationModule
	 * method from NavigationObserver interface.
	 */
	public void requestStartNavigation() {
		for(NavigationObserver obs: this.observers){
			obs.initNavigationModule(this.inicial, this.direction);
		}
	}

	/**
	 * Public method that makes the request of the place has changed, if the robot pick or drop an item. 
	 * It calls to placeHasChanged method from NavigationObserver interface.
	 */
	public void requestPlaceHasChanged() {
		for(NavigationObserver obs: this.observers){
			obs.placeHasChanged(this.inicial);
		}
	}

	/**
	 * Public method that makes the request of the heading changed of the robot. 
	 * It calls to headingChanged method from NavigationObserver interface.
	 */
	public void requestHeadingChanged() {	  
		for(NavigationObserver obs: this.observers){
			obs.headingChanged(this.direction);
		}
	}

	/**
	 * Public method that makes the request of the robot arrives to some place. 
	 * It calls to robotArrivesAtPlace method from NavigationObserver interface.
	 */
	public void requestRobotArrivesAtPlace() {
		for(NavigationObserver obs: this.observers){ 
			obs.robotArrivesAtPlace(this.direction, this.inicial);
		}
	}
}

