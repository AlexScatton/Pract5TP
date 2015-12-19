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
 * Class that represents the PICK instruction of the robot.
 *
 */
public class PickInstruction implements Instruction{
	
	private RobotEngine robot;
	private NavigationModule nav ;
	private ItemContainer rC; 
	private String id;

	/**
	 * Default constructor.
	 */
	public PickInstruction(){
		
	}
	
	/**
	 * Constructor of the Pick instruction, it gives an id (String).
	 * @param iden
	 */
	public PickInstruction(String iden){
		this.id = iden;
	}
	
	/**
	 * Configures the context
	 * @Specified by: configureContext in interface Instruction
	 * @param engine - The robot engine
	 * @param navigation - The information about the game, i.e., 
	 * the places, current direction and current heading to navigate
	 * @param robotContainer - The inventory of the robot
	 * */
	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer)
			throws WrongInstructionFormatException {
		this.robot = engine;
		this.nav = navigation; 
		this.rC = robotContainer; 
	}

	/**
	 * The robot adds an item to its inventory from the current place, if it exists
	 * @Specified by: execute in interface Instruction
	 * @throws instructionExecutionException - 
	 * When the place does not contain an item with this name or when 
	 * there is another item with the same id in the robot inventory 
	 * InstructionExecutionException - if there exist any execution error.
	 * */
	public void execute() throws InstructionExecutionException {
		//declaraciones
		Item aux_item = null;
		
		//recogemos el objeto del lugar
		aux_item = this.nav.pickItemFromCurrentPlace(this.id);
		//si no cogemos nada
		if(aux_item == null){
			throw new InstructionExecutionException("Ooops, " +
					"this place has not the object " );
		}else{
			//si se añade correctamente el objeto del lugar en el robot
			if(this.rC.addItem(aux_item)){
				
				this.nav.requestPlaceHasChanged();
				this.rC.requestInventoryChange();
				this.robot.saySomething("I am happy! Now I have " + this.id );
				
			}else{
				throw new InstructionExecutionException("I am stupid! " +
						"I had already the object " + this.id);
			}
		}
	}

	/**
	 * Description copied from interface: Instruction
	 * Returns a description of the Instruction syntax. 
	 * The string does not end with the line separator. It is up to the caller 
	 * adding it before printing.
	 * @Specified by: getHelp in interface Instruction
	 * @return the command syntax PICK|COGER <id>
	 * */
	public String getHelp() {
		return "     PICK <id> / COGER <id>";
	}

	/**
	 * Parses the String returning an instance of PickInstruction 
	 * or throwing a WrongInstructionFormatException()
	 * @Specified by: parse in interface Instruction
	 * @param cad - text String to parse
	 * @return Instruction Reference to an instance of PickInstruction
	 * @throws WrongInstructionFormatException - hen the String is not PICK|COGER <id>
	 * */
	public Instruction parse(String cad)throws WrongInstructionFormatException {	
		//declaracion
		Instruction retorno = null;
		
		//dividimos el String por espacios
		String [] words = cad.split(" ");
		
		//comprobamos que cumple el formato sino, excepcion
		if(	words[0].equalsIgnoreCase("PICK") || words[0].equalsIgnoreCase("COGER")){
			if(words.length == 2){
				retorno = new PickInstruction(words[1]);
			}else{
				throw new WrongInstructionFormatException();
			}
		}else{
			throw new WrongInstructionFormatException();
		}
		return retorno;
	}
}
