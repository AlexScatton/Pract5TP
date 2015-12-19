package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.Rotation;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * Public class that represents the TURN instruction of the robot.
 *
 */
public class TurnInstruction implements Instruction{

	private NavigationModule nav ;
	private RobotEngine robot;
	private Rotation rotacion;
	
	/**
	 * default constructor.
	 */
	public TurnInstruction(){

	}
	
	/**
	 * Constructor that gives a Rotation to the turn action.
	 * @param rot
	 */
	public TurnInstruction(Rotation rot){
		this.rotacion = rot;
	}
	
	/**
	 * Description copied from interface: Instruction 
	 * Set the execution context. The method receives the entire engine 
	 * (engine, navigation and the robot container) even though the actual 
	 * implementation of execute() may not require it.
	 * @Specified by: configureContext in interface Instruction
	 * @param engine - The robot engine
	 * @param navigation - The information about the game, i.e., the places, 
	 * current direction and current heading to navigate
	 * @param robotContainer - The inventory of the robot
	 * */
	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer)
			throws WrongInstructionFormatException {
		
		this.nav = navigation; 
		this.robot = engine;
	}

	/**
	 * Turns the robot left or right
	 * @Specified by: execute in interface Instruction
	 * @throws InstructionExecutionException - When the rotation is unknown
	 * */
	public void execute() throws InstructionExecutionException {

		//es nesario pasarle que a que direccion estamos mirando
		this.nav.rotate(this.rotacion);
		this.robot.addFuel(-5);

		this.nav.requestHeadingChanged();
		this.robot.requestRobotStatus();
	}

	/**
	 * Description copied from interface: Instruction
	 * Returns a description of the Instruction syntax. 
	 * The string does not end with the line separator. It is up to the caller 
	 * adding it before printing.
	 * @Specified by: getHelp in interface Instruction
	 * @return the command syntax TURN | GIRAR < LEFT|RIGHT >
	 * */
	public String getHelp() {
		return "     TURN <LEFT | RIGHT> / GIRAR <LEFT | RIGHT>";
	}

	/**
	 * Parses the String returning a TurnInstruction instance or 
	 * throwing a WrongInstructionFormatException()
	 * @Specified by: parse in interface Instruction
	 * @param cad - text String to parse
	 * @return Instruction Reference pointing to an instance of a Instruction subclass, 
	 * if it is corresponding to the String cad
	 * @throws WrongInstructionFormatException - 
	 * When the String is not TURN LEFT or RIGHT or GIRAR LEFT or RIGHT
	 * */
	public Instruction parse(String cad) throws WrongInstructionFormatException{
		//declaraciones
		Instruction retorno = null;
		//separamos la cadena por espacios
		String [] words = cad.split(" ");
		
		//comprobamos que cumple el formato sino, excepcion
		if(	words[0].equalsIgnoreCase("TURN") || words[0].equalsIgnoreCase("GIRAR")){
			if(words.length == 2){
				
				if(words[1].equalsIgnoreCase("LEFT")){
					retorno = new TurnInstruction(Rotation.LEFT);
				}else if(words[1].equalsIgnoreCase("RIGHT")){
					retorno = new TurnInstruction(Rotation.RIGHT);
				}else{
					throw new WrongInstructionFormatException();
				}
			}else{
				throw new WrongInstructionFormatException();
			}
		}else{
			throw new WrongInstructionFormatException();
		}
		return retorno;
	}
}
