package tp.pr5;


/**
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON
 * An enum class that represents in which direction the robot rotates (left or right) 
 * plus a value that represents an unknown direction
 */
public enum Rotation {

	LEFT, RIGHT, UNKNOWN; 
	/**
	 * Public method that convert the string to the correct Rotation.
	 * @param rot_string
	 * @return the Rotation according to the string
	 */
	public Rotation conversorStringRotation(String rot_string){
		//declaraciones
		Rotation retorno = null;
		
		if (rot_string.equalsIgnoreCase("LEFT")){
			retorno = Rotation.LEFT;
		}else if(rot_string.equalsIgnoreCase("RIGHT")){
			retorno = Rotation.RIGHT;
		}else{
			retorno = Rotation.UNKNOWN;
		}
		return retorno;
	}
}




