package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * Class that represents the QUIT instruction of the robot.
 *
 */
public class QuitInstruction implements Instruction{

	private RobotEngine robot;

	/**
	 * Default constructor.
	 */
	public QuitInstruction(){

	}

	/**
	 * Description copied from interface: Instruction 
	 * Set the execution context. The method receives the entire engine 
	 * (engine, navigation and the robot container) even though the actual implementation 
	 * of execute() may not require it.
	 * Specified by: getHelp in interface Instruction
	 * @return the Instruction syntax QUIT|SALIR
	 * */
	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer)
			throws WrongInstructionFormatException {
		
		this.robot=engine;
	}

	/**
	 * Request the robot to stop the simulation
	 * @Specified by: execute in interface Instruction
	 * @throws InstructionExecutionException - if there exist any execution error.
	 * */
	public void execute() throws InstructionExecutionException {
		//llamamos al metodo de robot para salir de la aplicacion
		this.robot.requestQuit();
	}

	/**
	 * Description copied from interface: Instruction
	 * Returns a description of the Instruction syntax. 
	 * The string does not end with the line separator. 
	 * It is up to the caller adding it before printing.
	 * Specified by: getHelp in interface Instruction
	 * @return the Instruction syntax QUIT|SALIR
	 * */
	public String getHelp() {
		return "     QUIT / SALIR";
	}

	/**
	 * Parsers the String returning an instance of QuitInstruction 
	 * or throwing a WrongInstructionFormatException()
	 * @Specified by: parse in interface Instruction
	 * @param cad - text String to parse
	 * @return Instruction reference to an instance of QuitInstruction
	 * @throws WrongInstructionFormatException - When the String is not QUIT | SALIR
	 * */
	public Instruction parse(String cad)throws WrongInstructionFormatException{
		//declaracion
		Instruction retorno = null;
		
		//separamos el String de instruccion por espacios
		String [] words = cad.split(" ");
		
		//comprobamos que cumple el formato sino, excepcion
		if(words[0].equalsIgnoreCase("QUIT") || words[0].equalsIgnoreCase("SALIR")){
			if(words.length == 1){
				retorno = new QuitInstruction();
			}else{
				throw new WrongInstructionFormatException();
			}
		}else{
			throw new WrongInstructionFormatException();
		}
		return retorno;
	}
}
