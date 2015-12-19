package tp.pr5.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.EventListener;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import tp.pr5.Controller;
import tp.pr5.Interpreter;
import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.RobotEngineObserver;
import tp.pr5.Rotation;
import tp.pr5.instructions.DropInstruction;
import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.MoveInstruction;
import tp.pr5.instructions.OperateInstruction;
import tp.pr5.instructions.PickInstruction;
import tp.pr5.instructions.QuitInstruction;
import tp.pr5.instructions.TurnInstruction;
import tp.pr5.items.ItemContainer;
/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * This class represents the Main window of the graphic interface of the application.
 * It extends from JFrame and implements RobotEngineObserver.
 */	
public class MainWindow extends JFrame implements RobotEngineObserver{
	
    public static String BTO_MOVE = "botonMove";
    public static String BTO_QUIT = "botonQuit";
    public static String BTO_TURN = "botonTurn";
    public static String BTO_PICK = "botonPick";
    public static String BTO_DROP = "botonDrop";
    public static String BTO_OPERATE = "botonOperate";
    public static String COMB_ROTA = "comboRota";
    public static String TEXT_OBJ = "textObject";
	public static String LAB_INFO = "panel_info";
	public static String BTO_OP_SALIR = "opcionsalir";
	
	
	//componenetes y atributos
	private JButton botonMove;
	private JButton botonQuit;
	private JButton botonTurn;
	private JComboBox comboRota;
	private JButton botonPick;
	private JTextField textObject;
	private JButton botonDrop;
	private JButton botonOperate;
	private NavigationPanel navigatePanel;
	private RobotPanel robotPanel;
	private InfoPanel infoPanel;
	//boton del menu
	private JMenuItem opcionsalir;

	private GUIController guicontroller;
	
	/**
	 * Constructor of the Main window.
	 * It creates panels, frames, etc with initial values.
	 * @param robot
	 */
	public MainWindow(Controller controlador){
		super("File");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.inicializacion();

		//paneles
		this.creacionPaneles();
		
		//activamos los eventos de botones y mostramos Frame
		this.setControlador((EventListener) controlador);
		
		//guardamos los observadores
		//hacemos una llamada de update en cada uno de estos casos o para cada uno de estos
		this.guicontroller = (GUIController) controlador;
		guicontroller.registerEngineObserver(this);
		guicontroller.registerEngineObserver(this.infoPanel);
		guicontroller.registerRobotObserver(this.infoPanel);
		guicontroller.registerRobotObserver(this.navigatePanel);
		guicontroller.registerItemContainerObserver(this.infoPanel);
		guicontroller.registerItemContainerObserver(this.robotPanel);
		this.pack();
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.arranca();
	}
	
	
	/**
	 * Private method. It creates the Panels of the window with initial values and the distribution of the Panels(LayoutManager).
	 * It adds components like buttons to the Panel.
	 */
	private void creacionPaneles() {
		//propio frame
		this.setLayout(new BorderLayout());
		JSplitPane Manejo = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		JPanel izq = new JPanel();
		izq.setLayout( new GridLayout(4, 2, 3, 3));
		izq.add(botonMove);
		izq.add(botonQuit);
		izq.add(botonTurn);
		izq.add(comboRota);
		izq.add(botonPick);
		izq.add(textObject);
		izq.add(botonDrop);
		izq.add(botonOperate);
		Manejo.add(izq);
		izq.setBorder(new TitledBorder("Instructions"));

		Manejo.add(robotPanel);

		this.add(Manejo, BorderLayout.NORTH);
		this.add(navigatePanel, BorderLayout.CENTER);
		
		this.infoPanel.setName(this.LAB_INFO);	
		this.add(this.infoPanel, BorderLayout.SOUTH);
		
		this.creacionMenu();
	}
	
	
	/**
	 * Private method. Create Menu
	 */
   private void creacionMenu(){
	   
	   JMenuBar basemenu = new JMenuBar();
	   JMenu opciones = new JMenu("Opciones");
	   this.opcionsalir = new JMenuItem("Salir");
	   this.opcionsalir.setName(this.BTO_OP_SALIR);
	   opciones.add(opcionsalir);
	   basemenu.add(opciones);
	   this.setJMenuBar(basemenu);
   }
	
	/**
	 * Private method. It creates objects of all the components we are using in the Main Window. 
	 * @param robot
	 */
	private void inicializacion(){
		this.botonMove = new JButton("Move");
		this.botonMove.setName(this.BTO_MOVE);
		this.botonQuit = new JButton("Quit");
		this.botonQuit.setName(this.BTO_QUIT);
		this.botonTurn = new JButton("Turn");
		this.botonTurn.setName(this.BTO_TURN);
		this.comboRota = new JComboBox();
		this.comboRota.setName(this.COMB_ROTA);
		this.comboRota.addItem("Right");
		this.comboRota.addItem("Left");
		this.botonPick = new JButton("Pick");
		this.botonPick.setName(this.BTO_PICK);
		this.textObject = new JTextField("");
		this.textObject.setName(this.TEXT_OBJ);
		this.botonDrop = new JButton("Drop");
		this.botonDrop.setName(this.BTO_DROP);
		this.botonOperate = new JButton("Operate");
		this.botonOperate.setName(this.BTO_OPERATE);
		
		this.navigatePanel = new NavigationPanel();
		this.robotPanel = new RobotPanel();
		this.infoPanel = new InfoPanel();
	}
	
	/**
	 * Private method that makes visible the Main Window and with it start the application.
	 */
	public void arranca(){
		this.setVisible(true);
	}
	/**
	 * Private method. It adds the Action Listener to the buttons of the Panel.
	 * @param c -Main window
	 */
	public void setControlador(EventListener controlador){
		this.botonMove.addActionListener((ActionListener) controlador);
		this.botonQuit.addActionListener((ActionListener) controlador);
		this.botonTurn.addActionListener((ActionListener) controlador);
		this.botonPick.addActionListener((ActionListener) controlador);
		this.botonDrop.addActionListener((ActionListener) controlador);
		this.botonOperate.addActionListener((ActionListener) controlador);
		this.comboRota.addActionListener((ActionListener) controlador);
		this.textObject.addFocusListener((FocusListener) controlador);
		//controlador para el menu
		this.opcionsalir.addActionListener((ActionListener) controlador);
		
		this.navigatePanel.setControlador();
		this.robotPanel.setControlador(controlador);
	}

	@Override
	public void raiseError(String msg) {
		JOptionPane.showMessageDialog(null,
                msg,
                "Info",
                JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void communicationHelp(String help) {
		
	}


	@Override
	public void engineOff(boolean atShip) {
		//mostrariamos que se termina con el mensaje de fin correspondiente pero no se observaria
		if(atShip){
			JOptionPane.showMessageDialog(null,
					RobotEngine.WallSay()+ "I am at my spaceship. Bye Bye",
            "Info",
            JOptionPane.INFORMATION_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(null,
			RobotEngine.WallSay()+ "I run out of fuel. " +
			"I cannot move. Shutting down...",
            "Info",
            JOptionPane.INFORMATION_MESSAGE);
		}
		System.exit(0);
	}


	@Override
	public void communicationCompleted() {
	    JDialog.setDefaultLookAndFeelDecorated(true);
	    int response = JOptionPane.showConfirmDialog(null, "Seguro que quieres salir?", "Confirm",
	        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		 if(response == JOptionPane.YES_OPTION ){
			 JOptionPane.showMessageDialog(null,
						RobotEngine.WallSay()+ "I have communication problems. Bye bye",
			            "Info",
			            JOptionPane.INFORMATION_MESSAGE);
			 System.exit(0);
		 }
	}


	@Override
	public void robotUpdate(int fuel, int recycledMaterial) {
		this.robotPanel.actualizarLabStatus("Fuel: " + fuel + " Recycled: " + recycledMaterial);
	}


	@Override
	public void robotSays(String message) {
		JOptionPane.showMessageDialog(null,
				message,
                "Info",
                JOptionPane.INFORMATION_MESSAGE);
	}
	
}
