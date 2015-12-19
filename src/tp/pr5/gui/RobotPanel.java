package tp.pr5.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import tp.pr5.RobotEngineObserver;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.Item;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * Class that represents the Robot Panel in the graphic interface.
 * It extends from JPanel and implements InventoryObserver and RobotEngineObserver.
 */
public class RobotPanel extends JPanel implements InventoryObserver, RobotEngineObserver{
	
	public static final String TAB_DATOS = "tablaDatos";
	public static final String LAB_STATE = "robotState";
	
	//espeficicar atributos
	private JLabel robotState;
	private JTable tablaDatos;
	private DefaultTableModel modelo;
	
	//tenemos que como maximo se puedan meter 10 datos en la tabla
	private int COORFILAMAX = 0;
	private static final int COORCOLUMMAX = 2;
	public static final String [] titulocolumnas = {"Id", "Description"};
	
	/**
	 * Default Constructor
	 */
	public RobotPanel(){

		//inicializacion
		this.inicializacion();
	
		//paneles
		this.creacionPaneles();
	}
	/**
	 * Private method that creates the Panels of the Robot Panel
	 */
	private void creacionPaneles() {
		this.setLayout( new BorderLayout());
		
    	this.setBorder(new TitledBorder("Robot info"));
    	
    	JScrollPane paneltabladatos = new JScrollPane(tablaDatos);
    	paneltabladatos.setPreferredSize(new Dimension(50, 50));
		this.add(robotState, BorderLayout.NORTH);
		this.add(paneltabladatos, BorderLayout.CENTER);
	}

	/**
	 * Private method that initializes the attributes of the class Robot Panel.
	 */
	private void inicializacion() {
		String [][]cadena = new String [COORFILAMAX][RobotPanel.COORCOLUMMAX];
		modelo = new DefaultTableModel( cadena, titulocolumnas);
		tablaDatos = new JTable(modelo);
		tablaDatos.setName(this.TAB_DATOS);
		tablaDatos.setRowSelectionAllowed(true);
		tablaDatos.setEnabled(true);
		
		robotState = new JLabel();
		robotState.setName(this.LAB_STATE);
		robotState.setFont(new Font("Tahoma", Font.BOLD, 11));
		robotState.setHorizontalAlignment(0);
	}
	
	/**
	 * Public method. It adds a item container.
	 * @param listaobjetos
	 * @param objetoinsertar
	 */
/*	public void anadirContenedor(Collection<Item> listaobjetos, String objetoinsertar){
		//declaracion
		Iterator<Item> it = listaobjetos.iterator();		
		int i = 0;
		Item objetoobservado = null;
		boolean encontrado = false;
		
		//actualizamos las lineas de la tabla de datos
		this.COORFILAMAX = listaobjetos.size();
		while( i < this.COORFILAMAX && !encontrado){
				//cogemos elemento
				objetoobservado = ((Item)it.next());
				//añadimos la informacion del elemento a la tabla de datos
				if(objetoinsertar.equalsIgnoreCase(objetoobservado.getId())){
					encontrado = true;
					String []cadena = {objetoobservado.getId(),objetoobservado.getDescription()};
					this.modelo.addRow(cadena);
				}
			i++;			 
		}
	}*/
	
	/**
	 * Public method. It remove an item container.
	 * @param listaobjetos
	 */
/*	public void quitarContenedor(Collection<Item> listaobjetos) {
		//actualizamos las lineas de la tabla de datos
		this.COORFILAMAX = listaobjetos.size();
		//eliminamos el elemento que tenemos seleccionado de la tabla
		this.modelo.removeRow(this.tablaDatos.getSelectedRow());
	}

	/**
	 * Public method that catch the selected row and return the value in String
	 * @return String with the value of some position of the TablaDatos.
	 */
/*	public String capturarSeleccionado() {
		//declaraciones
		String retorno = "";
		int row = 0;
		
		row = this.tablaDatos.getSelectedRow();
		if( row != -1){
			retorno = (String) this.tablaDatos.getValueAt(row,0);
		}
		return retorno;
	}
*/
	/**
	 * Public void method that updates the robot status.
	 * @param msg
	 */
	public void actualizarLabStatus(String msg){
		this.robotState.setText(msg);
	}
	
	@Override
	public void inventoryChange(List<Item> inventory) {
		//declaracion
		if(inventory != null){
			Iterator<Item> it = inventory.iterator();		
			int i = 0;
			Item objetoobservado = null;
			boolean encontrado = false;
			
			this.COORFILAMAX = inventory.size();
			
			String [] []aux = new String [COORFILAMAX][RobotPanel.COORCOLUMMAX];
			
			//actualizamos las lineas de la tabla de datos
			while( i < this.COORFILAMAX && !encontrado){
				//cogemos elemento
				objetoobservado = ((Item)it.next());
				//añadimos la informacion del elemento a la tabla de datos
				String []cadena = {objetoobservado.getId(),objetoobservado.getDescription()};
				aux[i] = cadena;
				i++;			 
			}		
		this.modelo.setDataVector( aux, titulocolumnas);
		}
	}
	
	/**
	 * Set controller method. EventListener parameter.
	 * @param controlador
	 */
	public void setControlador(EventListener controlador) {
		this.tablaDatos.addMouseListener((MouseListener) controlador);
	}

	@Override
	public void inventoryScanned(String inventoryDescription) {
		
	}
	
	@Override
	public void itemScanned(String description) {
		
	}
	
	@Override
	public void itemEmpty(String itemName) {
		JOptionPane.showMessageDialog(null,
				("What a pity! I have no more " + itemName + " in my inventory"),
                "Info",
                JOptionPane.INFORMATION_MESSAGE);
	}
	
	@Override
	public void raiseError(String msg) {
		
	}
	
	@Override
	public void communicationHelp(String help) {
		
	}
	
	@Override
	public void engineOff(boolean atShip) {
		
	}
	
	@Override
	public void communicationCompleted() {
		
	}
	
	@Override
	public void robotUpdate(int fuel, int recycledMaterial) {
		this.actualizarLabStatus("Fuel: " + fuel + " Recycled: " + recycledMaterial);
	}
	
	@Override
	public void robotSays(String message) {
	}

}
