package tp.pr5;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON
 * Public interface. It provides the information about each place of the city.
 */
public interface PlaceInfo {

	/**
	 * Return the place name
	 * */
	public String getName();
	
	/**
	 * Return the place description
	 * */
	public String getDesciption();
	
	/**
	 * Return if the space ship is in this place. 
	 * */
	public boolean isSpaceship();
}
