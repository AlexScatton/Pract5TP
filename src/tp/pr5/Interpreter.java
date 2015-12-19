package tp.pr5;

import tp.pr5.instructions.DropInstruction;
import tp.pr5.instructions.HelpInstruction;
import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.MoveInstruction;
import tp.pr5.instructions.OperateInstruction;
import tp.pr5.instructions.PickInstruction;
import tp.pr5.instructions.QuitInstruction;
import tp.pr5.instructions.RadarInstruction;
import tp.pr5.instructions.ScanInstruction;
import tp.pr5.instructions.TurnInstruction;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * The interpreter is in charge of converting the user input in an 
 * instruction for the robot. 
 * Up to now, the valid instructions are:
 *  MOVE
 *  TURN { LEFT | RIGHT }
 *  PICK <ITEM>
 *  SCAN [ <ITEM> ]
 *  OPERATE <ITEM>
 *  HELP
 *  QUIT
 */
public class Interpreter {
	//variable para capturar los saltos de linea
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	public static final int cantInstruccion = 9;
	public static Collection<Instruction> b;
	
	/**
	 * Default Interpreter constructor.
	 */
	public Interpreter(){
		this.b = new ArrayList<Instruction>();
		b.add(new QuitInstruction());
		b.add(new HelpInstruction());
		b.add(new DropInstruction());
		b.add(new MoveInstruction());
		b.add(new OperateInstruction());
		b.add(new PickInstruction());
		b.add(new RadarInstruction());
		b.add(new ScanInstruction());
		b.add(new TurnInstruction());	
	}
	
	/**
	 * Generates a new instruction according to the user input.
	 * @param line - A string with the user input
	 * @return The instruction read from the given line. 
	 * If the instruction is not correct, then it returns a not valid 
	 * instruction, i.e., an empty instruction (calling to the default 
	 * constructor of class Instruction).
	 */
	public static Instruction generateInstruction(java.lang.String line)
			throws WrongInstructionFormatException{
		int i = 0;
		boolean correct = false;
		Instruction instr = null;
		
		Iterator it = b.iterator();
		while(it.hasNext() && !correct){
			try{
				instr = ((Instruction) it.next()).parse(line);
				correct = true;
			}catch(WrongInstructionFormatException e){
				
			}
		}
		
		if(correct) return instr;
		else throw new WrongInstructionFormatException( RobotEngine.WallSay() + "I do not understand. Please repeat");
	}
	
	/**
	 * It returns type about all the instructions that the robot understands
	 * */
	public static String interpreterHelp(){
		//declaraciones
		int i = 0;
		String listado = "";
		
		//Recorrido que nos muestra todos los getHelp de las instrucciones
		Iterator it = b.iterator();
		while(it.hasNext()){
			listado = listado + ((Instruction) it.next()).getHelp()+ Interpreter.LINE_SEPARATOR;
			i++;
		}
		return listado;
	}
}
