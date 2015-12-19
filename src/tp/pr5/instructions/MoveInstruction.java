package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * Class that represents the Move instruction of the robot.
 *
 */
public class MoveInstruction implements Instruction {
	
	private NavigationModule nav ;
	private RobotEngine robot;

	/**
	 * Default constructor
	 */
	public MoveInstruction(){

	}

	/**
	 * Description copied from interface: Instruction
	 * Set the execution context. The method receives the entire engine 
	 * (engine, navigation and the robot container) even though the actual implementation of execute() may not require it.
	 * @Specified by: getHelp in interface Instruction
	 * @return the command syntax MOVE|MOVER
	 * */
	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer)
			throws WrongInstructionFormatException {
		
		this.nav = navigation; 
		this.robot = engine;
	}

	/**
	 * Moves from the current place to the next place on the current Direction. 
	 * An opened street must exist between both places to be moved
	 * @Specified by: execute in interface Instruction
	 * @throws InstructionExecutionException - 
	 * When the robot cannot go to other place (there is a wall, a closed street...)
	 * */
	public void execute() throws InstructionExecutionException {
		//accedemos a la implementacion de move de Navigation
		this.nav.move();
		
		this.nav.requestRobotArrivesAtPlace();
		this.robot.addFuel(-5);
		this.robot.requestRobotStatus();
	}

	/**
	 * Description copied from interface: Instruction
	 * Returns a description of the Instruction syntax. 
	 * The string does not end with the line separator. 
	 * It is up to the caller adding it before printing.
	 * @return the command syntax MOVE|MOVER
	 */
	public String getHelp() {
		return "     MOVE / MOVER";
	}

	/**
	 * Parses the String returning a MoveInstruction instance or throwing a WrongInstructionFormatException()
	 * @Specified by: parse in interface Instruction
	 * @param cad - text String to parse
	 * @return Instruction Reference to an instance of MoveInstruction
	 * @throws WrongInstructionFormatException - When the String is not MOVE
	 * */
	public Instruction parse(String cad)throws WrongInstructionFormatException{
		//declaraciones
		Instruction retorno = null;
		
		//separamos el Strin de la instruccion
		String [] words = cad.split(" ");
		
		//comprobamos que cumple el formato sino, excepcion
		if(words[0].equalsIgnoreCase("MOVE") || words[0].equalsIgnoreCase("MOVER")){
			if(words.length == 1){
				retorno = new MoveInstruction();
			}else{
				throw new WrongInstructionFormatException();
			}
		}else{
			throw new WrongInstructionFormatException();
		}
		return retorno;
	}
}
