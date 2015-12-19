package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * Public class that represents the RADAR instruction of the robot.
 *
 */
public class RadarInstruction implements Instruction{

	private NavigationModule nav ;
	
	/**
	 * Default constructor.
	 */
	public RadarInstruction(){

	}

	/**
	 * Configure the context
	 * @Specified by: configureContext in interface Instruction
	 * @param engine - The robot engine
	 * @param navigation - The information about the game, i.e., 
	 * the places, current direction and current heading to navigate
	 * @param robotContainer - The inventory of the robot
	 * */
	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer)
			throws WrongInstructionFormatException {
		
		this.nav=navigation; 
	}

	/**
	 * Shows the current place description
	 * @Specified by: execute in interface Instruction
	 * @throws InstructionExecutionException - if there exist any execution error.
	 * */
	public void execute() throws InstructionExecutionException {
		
		//mostramos el contenido del lugar en el que nos encontramos
		this.nav.scanCurrentPlace();
	}

	/**
	 * Description copied from interface: Instruction
	 * Returns a description of the Instruction syntax. 
	 * The string does not end with the line separator. 
	 * It is up to the caller adding it before printing.
	 * @Specified by: getHelp in interface Instruction
	 * @return the Instruction syntax RADAR
	 * */
	public String getHelp() {
		return "     RADAR";
	}

	/**
	 * Parses the String returning an instance of RadarInstruction or 
	 * throwing a WrongInstructionFormatException()
	 * @Specified by: parse in interface Instruction
	 * @param cad - text String to parse
	 * @return Instruction Reference to an instance of RadarInstruction
	 * @throws WrongInstructionFormatException - When the String is not RADAR
	 * */
	public Instruction parse(String cad)throws WrongInstructionFormatException {
		//declaracion 
		Instruction retorno = null;
		
		//dividimos el String por espacios
		String [] words = cad.split(" ");
		
		//comprobamos que cumple el formato sino, excepcion
		if(words[0].equalsIgnoreCase("RADAR")){
			if(words.length == 1){
				retorno = new RadarInstruction();
			}else{
				throw new WrongInstructionFormatException();
			}
		}else{
			throw new WrongInstructionFormatException();
		}
		return retorno;
	}
}
