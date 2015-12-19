package tp.pr5;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * Public interface, it contains the event methods of the movement and localization of the Robot.
 *
 */
public interface NavigationObserver {
	
	/**
	 * Notifies that the robot heading has changed
	 * */
	void headingChanged(Direction newHeading);
	
	/**
	 * Notifies that the navigation module has been initialized
	 * */
	void initNavigationModule(PlaceInfo initialPlace, Direction heading);
	
	/**
	 * Notifies that the robot has arrived at a place
	 * */
	void robotArrivesAtPlace(Direction heading, PlaceInfo place);
	
	/**
	 * Notifies that the user requested a RADAR instruction
	 * */
	void placeScanned(PlaceInfo placeDescription);

	/**
	 * Notifies that the place where the robot stays has changed (because the robot picked or dropped an item)
	 * */
	void placeHasChanged(PlaceInfo placeDescription);
}
