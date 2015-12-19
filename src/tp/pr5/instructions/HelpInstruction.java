package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * Class that represents the HELP instruction of the robot.
 *
 */
public class HelpInstruction implements Instruction{
	private RobotEngine robot;
	
	/**
	 * Default constructor.
	 */
	public HelpInstruction(){
 
	}
	
	/**
	 * Configuration of the context for this instruction
	 * @Specified by: configureContext in interface Instruction
	 * @param engine - The robot engine
	 * @param navigation - The information about the game, i.e., the places, 
	 * current direction and current heading to navigate
	 * @param robotContainer - The inventory of the robot
	 * */
	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer)
			throws WrongInstructionFormatException {
		
		this.robot = engine;
	}

	/**
	 * Prints the help string of every instruction. 
	 * It delegates to the Interpreter class.
	 * @Specified by: execute in interface Instruction
	 * @throws InstructionExecutionException - if there exist any execution error.
	 * */
	public void execute() throws InstructionExecutionException {
		//llamada a la implementacion de help en robot para mostrar la ayuda		
		this.robot.requestHelp();
	}

	/**
	 * Help syntax
	 * @Specified by: getHelp in interface Instruction
	 * @return the instruction syntax HELP
	 * */
	public String getHelp() {
		return "     HELP / AYUDA";
	}

	/**
	 * Parses the String returning a HelpInstruction instance or throwing a 
	 * WrongInstructionFormatException()
	 * @Specified by: parse in interface Instruction
	 * @param cad - Text String to parse
	 * @return Instruction Reference to an instance of HelpInstruction
	 * @throws WrongInstructionFormatException - When the String is not HELP
	 * */
	public Instruction parse(String cad)throws WrongInstructionFormatException{
		//declaraciones
		Instruction retorno = null;
		
	    //separamos el String por espacios
		String [] words = cad.split(" ");
		
		//comprobamos que cumple el formato sino, excepcion
		if(words[0].equalsIgnoreCase("HELP") || words[0].equalsIgnoreCase("AYUDA")){
			if(words.length == 1){
				retorno = new HelpInstruction();
			}else{
				throw new WrongInstructionFormatException();
			}
		}else{
			throw new WrongInstructionFormatException();
		}
		return retorno;
	}
}
