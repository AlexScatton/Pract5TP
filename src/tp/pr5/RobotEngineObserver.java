package tp.pr5;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 *Public Interface, it has the methods we need to be know about the "high level" events. Robot say something,
 *change fuel or item recycled, any error command, help or quit instructions and if the game is over.
 */
public interface RobotEngineObserver {
	
	/**
	 * The robot engine informs that it has raised an error
	 * */
	void raiseError(java.lang.String msg);
	
	/**
	 * The robot engine informs that the help has been requested
	 * */
	void communicationHelp(java.lang.String help);
	
	/**
	 * The robot engine informs that the robot has shut down (because it has arrived at the spaceship or it has run out of fuel)
	 * */
	void engineOff(boolean atShip);
	
	/**
	 * The robot engine informs that the communication is over.
	 * */
	void communicationCompleted();
	
	/**
	 * The robot engine informs that the fuel and/or the amount of recycled material has changed
	 * */
	void robotUpdate(int fuel, int recycledMaterial);
	
	/**
	 * The robot engine informs that the robot wants to say something
	 * */
	void robotSays(java.lang.String message);
}
