package tp.pr5.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventObject;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import tp.pr5.Controller;
import tp.pr5.RobotEngine;
import tp.pr5.Rotation;
import tp.pr5.instructions.DropInstruction;
import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.MoveInstruction;
import tp.pr5.instructions.OperateInstruction;
import tp.pr5.instructions.PickInstruction;
import tp.pr5.instructions.QuitInstruction;
import tp.pr5.instructions.TurnInstruction;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * Public class that extends Controller and implements ActionListener,MouseListener and FocusListener.
 * It is used when the application is running in Swing. RobotEngine handles requests send the command execution 
 * that are performed using the views of Swing, we will see below.
 */
public class GUIController extends Controller implements ActionListener, MouseListener, FocusListener{	
	/**
	 * La variable <code>modelo</code> contiene el modelo de la aplicaci�n. Por regla general suele ser un �nico modelo.
	 */
	
    /**
     * <p>Constructor que se encarga de colocar el modelo.
     * <p> La vista se actualizar� a partir de los Observadores.
     * @param elModelo contiene el modelo que realizar� los c�mputos.
	 * @see <a href="http://docs.oracle.com/javase/7/docs/api/java/util/Observable.html"> Observable </a>,
     *      <a href="http://docs.oracle.com/javase/7/docs/api/java/util/Observer.html"> Observer </a>
     */
	

	private String rotation_string;
	private String selected_string;
	private String objeto_string;
	
    /**
     * Establece el modelo que est� controlando esta clase. 
     * @param elModelo el modelo a controlar
     */
    public GUIController(RobotEngine elModelo) {
    	super(elModelo);
    	this.rotation_string = "right";
    	this.selected_string = null;
    	this.objeto_string = null;
	}
	
    
	@Override
	public void startController() {
		this.engine.requestStart();
	}
	
	
	/**
	 * It controls click events of the buttons in the main window.
	 * Check the instruction, communicate to the robot the instruction, refresh the 
	 * fuel and garbage and finally write in the text area.
	 */
    private void cambiarModelo (Component fuente) {
    	//declaraciones
    	Instruction instruc = null;

		if(fuente.getName().equals(MainWindow.BTO_MOVE)) {
			instruc = new MoveInstruction();
			this.engine.communicateRobot(instruc);  
			
	    }else if (fuente.getName().equals(MainWindow.BTO_QUIT) || fuente.getName().equals(MainWindow.BTO_OP_SALIR)) {
			instruc = new QuitInstruction();
			this.engine.communicateRobot(instruc);
			
	    }else if(fuente.getName().equals(MainWindow.BTO_TURN)){
        	if(this.rotation_string.equalsIgnoreCase("LEFT")){
				instruc = new TurnInstruction(Rotation.LEFT);
			}else if(this.rotation_string.equalsIgnoreCase("RIGHT")){
				instruc = new TurnInstruction(Rotation.RIGHT);
			}
			this.engine.communicateRobot(instruc);
        }else if(fuente.getName().equals(MainWindow.COMB_ROTA)){
        	JComboBox campo = (JComboBox)fuente;
        	this.rotation_string = ((String) campo.getSelectedItem());
	
        }else if(fuente.getName().equals(MainWindow.BTO_DROP)){
			instruc = new DropInstruction(this.selected_string);
			this.engine.communicateRobot(instruc);

        }else if(fuente.getName().equals(RobotPanel.TAB_DATOS)){
        	int row = -2;
        	JTable campo = (JTable)fuente;
    		row = campo.getSelectedRow();
    		if( row != -1){
    			selected_string = (String) campo.getValueAt(row,0);
    		}
	    		
        }else if(fuente.getName().equals(MainWindow.TEXT_OBJ)){
        	JTextField campo = (JTextField)fuente;
        	this.objeto_string = campo.getText();
        	
        }else if(fuente.getName().equals(MainWindow.BTO_PICK)){
			instruc = new PickInstruction(this.objeto_string);
			this.engine.communicateRobot(instruc);		
			
		}else if(fuente.getName().equals(MainWindow.BTO_OPERATE)){
			instruc = new OperateInstruction(this.selected_string);
			this.engine.communicateRobot(instruc);
			
			/***************preguntar en clase********/
		}/*else if(fuente.getClass() == PlaceCell.class){
			
			//PlaceCell campo = (PlaceCell) e.getSource();
			PlaceCell campo = (PlaceCell)fuente;
			
			if(campo.getText()!= ""){
				this.engine.actualizarArea(campo.toString());
			}else{
				JOptionPane.showMessageDialog(null,
		                ("Selected PlaceCell null"),
		                "Info",
		                JOptionPane.INFORMATION_MESSAGE);
			}*/
			/**********************************/

		//comprobamos salida
		if(this.engine.isOver()){
			this.engine.requestEngineOff();
		}
    }


	/**
	 * M�todo para tratar los eventos de forma gen�rica. 
	 * Se encarga tanto de solicitar la modificaci�n al modelo como de informar a la vista
	 * @param e el evento a tratar
	 */
	private void trataEventoGenerico(EventObject event){
        Component fuente = (Component) event.getSource(); // el que gener� el evento
	//	System.err.println(fuente.getName());
        cambiarModelo(fuente);
	}


	/**
	 * Se tratan los eventos del tipo <code>ActionEvent</code> informando cuando es necesario a la vista y al modelo.
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		//System.err.print("actionPerformed: ");
        trataEventoGenerico(ae);
	}
	
	/**
	 * Se tratan los eventos del tipo mouseClicked informando cuando es necesario a la vista y al modelo.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		//System.err.print("mouseClicked: ");
        trataEventoGenerico(e);
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		
	}




	@Override
	public void focusLost(FocusEvent e) {
		//System.err.print("focusLost: ");
        trataEventoGenerico(e);
	}


	@Override
	public void focusGained(FocusEvent arg0) {
		
	}
}
