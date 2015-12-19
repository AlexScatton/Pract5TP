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
 * Class that represents the DROP instruction of the Robot.
 *
 */
public class DropInstruction implements Instruction{
	//atributos
	private NavigationModule nav ;
	private ItemContainer rC; 
	private RobotEngine robot;
	private String id;
	
	/**
	 * Default constructor
	 */
	public DropInstruction() {
		
	}
	/**
	 * Constructor that gives an id (String) for the item.
	 * @param iden
	 */
	public DropInstruction(String iden){
		this.id = iden;
	}
	
	
	/**
	 * Fixes the current context, i.e., the robot engine and the navigation module
	 * @Specified by: configureContext in interface {@link Instruction}
	 * @param engine - The robot engine
	 * @param navigation - The information about the game, i.e., the places, 
	 * current direction and current heading to navigate
	 * @param robotContainer - The inventory of the robot
	 * */
	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer)
			throws WrongInstructionFormatException {
		
		
		this.nav = navigation; 
		this.rC = robotContainer; 
		this.robot = engine;
		
	}

	/**
	 * The robot drops an Item from its inventory in the current place, 
	 * if the item exists
	 * @Specified by: configureContext in interface {@link Instruction}
	 * @throws InstructionExecutionException - 
	 * When the robot inventory does not contain an item with this name or when there is 
	 * another item with the same id in the current place
	 * */
	public void execute() throws InstructionExecutionException {
		//declaraciones
		Item it = null;		
		
		//comprobamos si existe el objeto en el contenedor de robot y 
		//el del lugar antes de empezar a modificar cualquier cosa
		if(this.id != null){
			it = this.rC.pickItem(this.id);
			if(it != null){
				if(!this.nav.findItemAtCurrentPlace(it.getId())){
					this.nav.dropItemAtCurrentPlace(it);
					
					this.nav.requestPlaceHasChanged();
					this.rC.requestInventoryChange();
					this.robot.saySomething("Great! I have dropped "+it.getId());
				}else{
					//ya existe uno en ese lugar
					throw new InstructionExecutionException(it.getId() + "exist in this place");
				}
			}else{
				throw new InstructionExecutionException("You do not have any " + this.id + ".");
			}
		}else{
			//comunicamos el error para caso de interfaz
			throw new InstructionExecutionException("Selected any object");
		}
	}

	/**Instruction help
	 * @Specified getHelp in interface Instruction
	 * @return the instruction syntax DROP <id>
	 * */
	public String getHelp() {
		// TODO Auto-generated method stub
		return "     DROP <id> / SOLTAR <id>";
	}

	/**
	 * Parses the String returning an instance of DropInstruction or 
	 * throwing a WrongInstructionFormatException()
	 * @Specified by: parse in interface Instruction
	 * @param cad - text String to parse
	 * @return Instruction Reference to an instance of DropInstruction
	 * @throws WrongInstructionFormatException - When the String is not DROP <id>
	 * */
	public Instruction parse(String cad)throws WrongInstructionFormatException{
		//declaraciones
		Instruction retorno = null;
		// Separamos el String por espacios
		String [] words = cad.split(" ");
		
		//comprobamos que cumple el formato sino, excepcion
		if(	words[0].equalsIgnoreCase("DROP") || words[0].equalsIgnoreCase("SOLTAR")){
			if(words.length == 2){
				retorno = new DropInstruction(words[1]);
			}else{
				throw new WrongInstructionFormatException();
			}
		}else{
			throw new WrongInstructionFormatException();
		}
		return retorno;
	}
}
