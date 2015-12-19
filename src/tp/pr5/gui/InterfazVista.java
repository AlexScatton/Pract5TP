package tp.pr5.gui;

import java.util.EventListener;
import java.util.Observable;
import java.util.Observer;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * Public interface that extends from Observer. Responsible of showing the state of the game to the user.
 */
public interface InterfazVista extends Observer{
	
	/**
	 * Le dice a la vista que arranque la visualizacion
	 */
	void arranca(); // comienza la visualizaci�n

	/**
	 * Solicita a la vista que establezca el controlador
	 * @param c el controlador a fijar a la vista
	 */
	public void setControlador(EventListener c);
	
	/**
	 * Public method that updates the view.
	 */
	public void update(Observable arg0, Object arg1);

}
