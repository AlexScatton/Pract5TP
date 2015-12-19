package tp.pr5.console;

import java.util.Scanner;

import tp.pr5.Controller;
import tp.pr5.Interpreter;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * Public class that extends from Controller class.
 * We use it when the application runs on console. It has the loop game responsible of reading the user input, parse it
 * and ask to robotEngine to execute the command.
 */
public class ConsoleController extends Controller{

	private Scanner sc = new Scanner (System.in);
	
	/**
	 * ConsoleController constructor. The parameter is a RobotEngine variable.
	 * It creates the console and add the observers to the engine(engine, navigation and itemContainer).
	 * @param game
	 */
	public ConsoleController(RobotEngine game){
		super(game);
		Console consola = new Console();
		this.engine.addEngineObserver(consola);
		this.engine.addNavigationObserver(consola);
		this.engine.addItemContainerObserver(consola);
	}
	
	@Override
	public void startController() {
	
		Instruction Instr = null;
		Interpreter interpreter = new Interpreter(); 
		
		this.engine.requestStart();
		
		//salimos del bucle(fin de aplicacion) si salir es true o encontramos la nave	
		while( !this.engine.isOver()){
			//promtp
			this.engine.robotPromtp();
			
			String instruc = sc.nextLine();
		
			//pasaremos de string a instruccion 
			try {
				Instr = Interpreter.generateInstruction(instruc);
			}catch (WrongInstructionFormatException e) {
				// mostramos el mensaje de error por la cual no se a ejecutado
				//y la isntruccion valdra null al no poder reconocer la instruccion
				this.engine.requestError(e.getMessage());
				Instr = null;
			}
			
			if(Instr != null){
				//si existe la instruccion comunicaremos con navigation
				this.engine.communicateRobot(Instr);
				//si no tiene combustible
			}
			this.engine.requestEngineOff();
		}//cierra while
/*	if(this.engine.isOver()){
		this.engine.communicationCompleted();
	}*/
		
	}

}
