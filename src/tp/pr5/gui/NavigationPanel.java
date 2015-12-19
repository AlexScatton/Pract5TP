package tp.pr5.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import tp.pr5.Direction;
import tp.pr5.Interpreter;
import tp.pr5.NavigationObserver;
import tp.pr5.Place;
import tp.pr5.PlaceInfo;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * Class that represents the Navigation Panel in the graphic interface. It extends from JPanel and
 * implements NavigationObserver and ActionListener.
 *
 */ 
public class NavigationPanel extends JPanel implements NavigationObserver, ActionListener{
	
	public static String TEXT_AREA = "area";
	public static String LAB_DRAW = "drawRobot";
	public static String BTO_PLACE = "boton_place";
	public static String LAB_INFO = "panel_info";
	
	private int coorx;
	private int coory;
	private JLabel drawRobot;
	private JTextArea area;
	public static final int NUM_BOTON = 11;
	private PlaceCell [][] matrizbotones;

	/**
	 * Default Constructor.
	 */
	public NavigationPanel(){
		//inicializacion de los componentes
		this.inicializacion();
		//paneles
		this.creacionPaneles();	
	}
	
	/**
	 * Private method, creates the Panels of the NavigationPanel.
	 * The buttons 11x11 Panel, Panel map and the JScrollPane called panel area.
	 * Finally it activates the events of the buttons.
	 */
	private void creacionPaneles() {
		//this.setLayout(new BorderLayout());
		this.setLayout(new GridLayout(2, 1));
		
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new GridLayout(NUM_BOTON, NUM_BOTON));
		//inicializacion del panel
		for(int i = 0 ; i < NUM_BOTON; i++){
			for(int j = 0 ; j < NUM_BOTON; j++){
				panelBotones.add(matrizbotones[i][j] = new PlaceCell());
			}
		}
		panelBotones.setBorder(new TitledBorder("City Map"));
		
		
		JPanel panelmapa = new JPanel();
		panelmapa.setLayout( new BorderLayout());
		panelmapa.add(drawRobot, BorderLayout.WEST);
		panelmapa.add(panelBotones, BorderLayout.CENTER);
		
		JScrollPane panelarea = new JScrollPane(area);
		panelarea.setBorder(new TitledBorder("Log"));
		
	/*	this.add(panelmapa, BorderLayout.NORTH);
		this.add(panelarea, BorderLayout.SOUTH);*/		
		this.add(panelmapa);
		this.add(panelarea);
	}

	/**
	 * Private method that initializes the components of the Navigation Panel.
	 */
	private void inicializacion() {
		this.area = new JTextArea();
		this.area.setEditable(false);
		this.drawRobot = new JLabel();
		this.drawRobot.setIcon( new ImageIcon(".\\src\\tp\\pr5\\gui\\images\\walleNorth.png"));
		this.matrizbotones = new PlaceCell[NUM_BOTON][NUM_BOTON];
		this.coorx = 5;
		this.coory = 5;
	}

	/**
	 * Set area with an string parameter.
	 * @param area
	 */
	public void actualizarArea(String area) {
		this.area.append(area + Interpreter.LINE_SEPARATOR);
	}

	/**
	 * Public method that updates the color of the button matrix.
	 * @param direccion
	 * @param place
	 */
	public void actualizarColorLugar(Direction direccion, Place place){
		//salida del lugar
		this.matrizbotones[this.coorx][this.coory].leavePlace();

		//procesamiento de coordenadas
		if(direccion == Direction.NORTH){
			this.coorx = this.coorx - 1;
		}else if(direccion == Direction.EAST){
			this.coory = this.coory + 1;
		}else if(direccion == Direction.SOUTH){
			this.coorx = this.coorx + 1;
		}else if(direccion == Direction.WEST){
			this.coory = this.coory - 1;
		}
		//una vez procesadas las coordenadas realizamos el movimiento
		this.matrizbotones[this.coorx][this.coory].enterPlace(place);
	}

	/**
	 * Private method that set the events of the button matrix, adding action listener to every button.
	 * @param c
	 */
	public void setControlador(){
		for(int i = 0 ; i < NUM_BOTON; i++){
			for(int j = 0 ; j < NUM_BOTON; j++){
				matrizbotones[i][j].addActionListener(this);
			}
		}
	}

	/**
	 * Public method that draw the robot according of the direction.
	 * @param direction
	 */
	@Override
	public void headingChanged(Direction newHeading) {
		this.drawRobot.setIcon(newHeading.conversorDirectionDraw());
	}

	@Override
	public void initNavigationModule(PlaceInfo initialPlace, Direction heading) {
	      this.matrizbotones[this.coorx][this.coory].enterPlace((Place) initialPlace);
	      this.headingChanged(heading);
	      this.actualizarArea(initialPlace.toString());
	}

	@Override
	public void robotArrivesAtPlace(Direction heading, PlaceInfo place) {
		this.actualizarColorLugar(heading, (Place) place);
		this.actualizarArea(place.toString());
	}

	@Override
	public void placeScanned(PlaceInfo placeDescription) {
		this.actualizarArea(placeDescription.toString());
	}

	@Override
	public void placeHasChanged(PlaceInfo placeDescription) {
		this.actualizarArea(placeDescription.toString());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		PlaceCell campo = (PlaceCell) e.getSource();
		
		if(campo.getText()!= ""){
			this.actualizarArea(campo.toString());
		}else{
			JOptionPane.showMessageDialog(null,
	                ("Selected PlaceCell null"),
	                "Info",
	                JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
