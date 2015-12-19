package tp.pr5.gui;

import java.awt.Color;
import javax.swing.JButton;
import tp.pr5.Place;
import tp.pr5.PlaceInfo;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * Class that represents the button cell. It extends from JButton.
 *
 */
public class PlaceCell extends JButton{
	//atributos
	private Place lugar;
	
	/**
	 * Default constructor
	 */
	public PlaceCell(){
		
	}
	/**
	 * Method that shows the current place in one green button.
	 * @param currentPlace
	 */
	public void enterPlace(Place currentPlace){
		this.lugar = currentPlace;
		this.setText(this.lugar.getName());
		this.setBackground(Color.green);
	}
	/**
	 * Method that puts on gray one button when the robot is not there anymore.
	 */
	public void leavePlace(){
		this.setBackground(Color.gray);
	}
	
	/**
	 * Public method that initialize place cell.
	 * @param initialPlace
	 */
	public void initPlaceCell(PlaceInfo initialPlace) {
			this.lugar = (Place) initialPlace;
			this.setText(initialPlace.getName());
			this.setBackground(Color.green);
	}
	
	/**
	 * toString method that shows the place.
	 */
	public String toString(){
		return this.lugar.toString();
	}
}
