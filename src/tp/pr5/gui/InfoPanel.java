package tp.pr5.gui;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import tp.pr5.Direction;
import tp.pr5.NavigationObserver;
import tp.pr5.PlaceInfo;
import tp.pr5.RobotEngine;
import tp.pr5.RobotEngineObserver;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.Item;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 *Public class that extends from JPanel and implements RobotEngineObserver, NavigationObserver and 
 *InventoryObserver.
 * It is responsible of the information panel of the SWING window.
 */
public class InfoPanel extends JPanel implements RobotEngineObserver, NavigationObserver, InventoryObserver{
	
	private JLabel info;
	
	/**
	 * Default infoPanel Constructor.
	 */
	public InfoPanel(){
		this.inicializacion();
		
		//paneles
		this.creacionPaneles();
		this.arranca();
	}
	
	/**
	 * Private method. It creates the Panels of the window with initial values and the 
	 * distribution of the Panels(LayoutManager).
	 * It adds components like buttons to the Panel.
	 */
	private void creacionPaneles() {
		//propio frame
		this.setLayout(new BorderLayout());
		this.add(this.info, BorderLayout.CENTER);
	}
	
	/**
	 * Private method. It creates objects of all the components we are using in the Main Window. 
	 * @param robot
	 */
	private void inicializacion(){
		//no hace falta ponerlo porque ya asigna el texto al hacer initnavigation
		this.info = new JLabel();
		this.info.setHorizontalAlignment(0);
	}
	
	/**
	 * Private method that makes visible the panel of the infoPanel class.
	 */
	private void arranca() {
		this.setVisible(true);
	}
	
	@Override
	public void inventoryChange(List<Item> inventory) {
		//cambia el inventario
	}

	@Override
	public void inventoryScanned(String inventoryDescription) {
	/*	JOptionPane.showMessageDialog(null,
				inventoryDescription,
                "Info",
                JOptionPane.INFORMATION_MESSAGE);*/
		this.info.setText(inventoryDescription);
	}

	@Override
	public void itemScanned(String description) {
	/*	JOptionPane.showMessageDialog(null,
				description,
                "Info",
                JOptionPane.INFORMATION_MESSAGE);*/
		this.info.setText(description);
	}

	@Override
	public void itemEmpty(String itemName) {
		/*JOptionPane.showMessageDialog(null,
				"What a pity! I have no more " + 
				itemName + " in my inventory",
                "Info",
                JOptionPane.INFORMATION_MESSAGE);	*/	
		this.info.setText("What a pity! I have no more " + itemName + " in my inventory");
	}

	@Override
	public void headingChanged(Direction newHeading) {
	/*	JOptionPane.showMessageDialog(null,
				RobotEngine.WallSay() + "Moving in direction " + newHeading,
                "Info",
                JOptionPane.INFORMATION_MESSAGE);*/
	//	this.info.setText(RobotEngine.WallSay() + "Moving in direction " + newHeading);
	}

	@Override
	public void initNavigationModule(PlaceInfo initialPlace, Direction heading) {
	/*	JOptionPane.showMessageDialog(null,
				initialPlace.toString(),
                "Info",
                JOptionPane.INFORMATION_MESSAGE);*/
	//	this.info.setText(initialPlace.toString() + Interpreter.LINE_SEPARATOR + "WALL·E is looking at direction " + heading );
	}

	@Override
	public void robotArrivesAtPlace(Direction heading, PlaceInfo place) {
		/*JOptionPane.showMessageDialog(null,
				"headingChange",
                "Info",
                JOptionPane.INFORMATION_MESSAGE);*/
		//this.info.setText("headingChange");
		this.headingChanged(heading);
		this.initNavigationModule(place, heading);
	}

	@Override
	public void placeScanned(PlaceInfo placeDescription) {
		/*JOptionPane.showMessageDialog(null,
				placeDescription.toString(),
                "Info",
                JOptionPane.INFORMATION_MESSAGE);*/
		//this.info.setText(placeDescription.toString());
	}

	@Override
	public void placeHasChanged(PlaceInfo placeDescription) {
		//this.info.setText(placeDescription.toString());
	}

	@Override
	public void raiseError(String msg) {
		this.info.setText(msg);
	}

	@Override
	public void communicationHelp(String help) {
		//this.info.setText(help);
	}

	@Override
	public void engineOff(boolean atShip) {
	/*	if(atShip){
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
//		this.info.setText(msj);
		System.exit(0);*/
	}

	@Override
	public void communicationCompleted() {

	}

	@Override
	public void robotUpdate(int fuel, int recycledMaterial) {
		this.info.setText("Robot Attributes has been updated{" + fuel + ", " + recycledMaterial + "}");
	}

	@Override
	public void robotSays(String message) {
		/*JOptionPane.showMessageDialog(null,
				RobotEngine.WallSay() + message,
                "Info",
                JOptionPane.INFORMATION_MESSAGE);*/
		this.info.setText(RobotEngine.WallSay() + message);
	}
}
