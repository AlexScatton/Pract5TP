package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * Class that represents the SCAN instruction of the robot.
 *
 */
public class ScanInstruction implements Instruction{

	private RobotEngine robot;
	private ItemContainer rC; 
	private String id;
	
	/**
	 * Default constructor.
	 */
	public ScanInstruction(){

	}
	
	/**
	 * Constructor that gives an id (string).
	 * @param iden
	 */
	public ScanInstruction(String iden){
		this.id = iden;
	}
	
	/**
	 * Description copied from interface: Instruction
	 * Set the execution context. The method receives the entire engine 
	 * (engine, navigation and the robot container) even though the actual implementation of execute() may not require it.
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
		this.rC = robotContainer; 
	}

	/**
	 * Prints the description of a concrete item or the complete inventory of the robot
	 * @Specified by:
	 * execute in interface Instruction
	 * @throws InstructionExecutionException - When the robot does not contain 
	 * the item to be scanned
	 * */
	public void execute() throws InstructionExecutionException {
		//declaraciones
		Item objeto_robot = null;
		
		//accedemos al invetario
		if(this.id == null){
			
			if(this.rC.numberOfItems() == 0){
				this.robot.saySomething("My inventory is empty");
			}else{
				this.robot.saySomething("I am carrying the following items");
				this.rC.requestInventoryScanned();
			}
		}else{
			//con argumento
			objeto_robot = this.rC.getItem(this.id);
			if( objeto_robot != null){
				this.rC.requestScanItem(RobotEngine.WallSay()+ objeto_robot.getId()+": "+objeto_robot.toString());
			}else if(objeto_robot == null){
				throw new InstructionExecutionException("Not exist the object to find");
			}
		}
	}

	/**
	 * Description copied from interface: Instruction
	 * Returns a description of the Instruction syntax. 
	 * The string does not end with the line separator. 
	 * It is up to the caller adding it before printing.
	 * @Specified by: getHelp in interface Instruction
	 * @return the Instruction syntax of the instruction SCAN | ESCANEAR [id]
	 * */
	public String getHelp() {
		return "     SCAN [<id>] / ESCANEAR [<id>]";
	}

	/**
	 * Parses the String returning a ScanInstruction instance 
	 * or throwing a WrongInstructionFormatException()
	 * @Specified by: parse in interface Instruction
	 * @param cad - text String to parse
	 * @return Instruction Reference to an instance of ScanInstruction
	 * @throws WrongInstructionFormatException - 
	 * When the String is not SCAN | ESCANEAR [id]
	 * */
	public Instruction parse(String cad) throws WrongInstructionFormatException{
		//declaraciones
		Instruction retorno = null;
		
		//dividimos el String por espacios
		String [] words = cad.split(" ");
		
		//comprobamos que cumple el formato sino, excepcion
		if(	words[0].equalsIgnoreCase("SCAN") || words[0].equalsIgnoreCase("ESCANEAR")){
			if(words.length == 1){
				retorno = new ScanInstruction();
			}else if(words.length == 2){
				retorno = new ScanInstruction(words[1]);
			}else{
				throw new WrongInstructionFormatException();
			}
		}else{
			throw new WrongInstructionFormatException();
		}
		return retorno;
	}
}
