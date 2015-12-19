package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.CodeCard;
import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * Class that represents the Operate instruction of the robot.
 *
 */
public class OperateInstruction implements Instruction{

	
	private NavigationModule nav ;
	private ItemContainer rC; 
	private RobotEngine robot;
	private String id;
	
	/**
	 * Default constructor.
	 */
	public OperateInstruction(){

	}
	
	/**
	 * Constructor of the Operate Inst,gives an id for the item.
	 * @param iden
	 */
	public OperateInstruction(String iden){
		this.id = iden;
	}
	
	/**
	 * Description copied from interface: Instruction 
	 * Set the execution context. The method receives the entire engine 
	 * (engine, navigation and the robot container) even though the actual implementation of execute() may not require it.
	 * @Specified by: configureContext in interface Instruction
	 * @param engine - The robot engine
	 * @param navigation - The information about the game, i.e., 
	 * the places, current direction and current heading to navigate
	 * @param robotContainer - the invetary of the robot
	 * */
	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer)
			throws WrongInstructionFormatException {
		
		//es un set para acceder a engine ...
		this.nav = navigation; 
		this.rC = robotContainer; 
		this.robot = engine;
	}

	/**
	 * The robot uses the requested item.
	 * @Specified by: execute in interface Instruction
	 * @throws InstructionExecutionException - 
	 * When the robot inventory does not contain an item with this name or when the item 
	 * cannot be used
	 * */
	public void execute() throws InstructionExecutionException {
		//declaraciones
		Item objeto_robot = null;
		
		//busca dentro del inventario
		if(id != null){
			objeto_robot = this.rC.getItem(id);
			
			if(objeto_robot == null){
				throw new InstructionExecutionException("");
			}else{
				if(objeto_robot.canBeUsed()){
					//si existe y se puede usar lo usamos
					if(objeto_robot.use(this.robot, this.nav)){
						
						if(objeto_robot.getClass() != CodeCard.class)
							this.robot.requestRobotStatus();
							//comprobamos si quedan uso y sino lo borramos
						if(!objeto_robot.canBeUsed()){
							this.rC.useItem(objeto_robot);
							
							this.rC.requestItemEmpty(this.id);
							this.rC.requestInventoryChange();
						}
					}else{
						throw new InstructionExecutionException("I have problems " +
								"using the object " + objeto_robot.getId().toLowerCase());
					}
				}
			}
		}else{
			//comunicamos el error para caso de interfaz
			throw new InstructionExecutionException("Selected any object");
		}
	}

	/**
	 * Description copied from interface: Instruction
     * Returns a description of the Instruction syntax. 
	 * The string does not end with the line separator. 
	 * It is up to the caller adding it before printing.
	 * @Specified by: getHelp in interface Instruction
	 * @return the Instruction syntax OPERATE|OPERAR <ID>
	 */
	public String getHelp() {
		// TODO Auto-generated method stub
		return "     OPERATE <id> / OPERAR <id>";
	}

	/**
	 * Parses the String returning an instance of OperateInstruction or 
	 * throwing a WrongInstructionFormatException()
	 * @Specified by: parse in interface Instruction
	 * @param cad - text String to parse
	 * @return Instruction Reference to an instance of OperateInstruction
	 * @throws WrongInstructionFormatException - 
	 * When the String is not OPERATE|OPERAR <ID>
	 * */
	public Instruction parse(String cad) throws WrongInstructionFormatException {
		//declaraciones
		Instruction retorno = null;
		
		//separamos el String por espacios
		String [] words = cad.split(" ");
		
		//comprobamos que cumple el formato sino, excepcion
		if(	words[0].equalsIgnoreCase("OPERATE") || words[0].equalsIgnoreCase("OPERAR")){
			if(words.length == 2){
				retorno = new OperateInstruction(words[1]);
			}else{
				throw new WrongInstructionFormatException();
			}
		}else{
			throw new WrongInstructionFormatException();
		}
		return retorno;
	}
}
