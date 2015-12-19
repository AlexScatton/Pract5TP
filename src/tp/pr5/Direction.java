package tp.pr5;

import javax.swing.ImageIcon;

/**
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 * An enumerated type that represents the compass directions (north, east, south and west) 
 * plus a value that represents an unknown direction.
 */
public enum Direction {

	EAST, NORTH, SOUTH, UNKNOWN, WEST;

	/**
	 * Public method that returns the opposite direction from the given direction.
	 * @return Direction : the opposite direction
	 */
	public Direction opposite(){
		//declaraciones
		Direction retorno = null;
				
		if( this == NORTH){
			retorno = SOUTH;
		}else if(this == WEST){
			retorno = EAST;
		}else if(this == SOUTH){
			retorno = NORTH;
		}else{
			retorno = WEST;
		}
		return retorno;
	}
	
	/**
	 * Public method that returns the rotating left direction in the given direction.
	 * @return Direction : left direction.
	 * */
	public Direction GirarIZQ(){
		//declaraciones
		Direction retorno = null;
		if (this == Direction.NORTH){
			retorno = WEST;
		}else if(this == Direction.WEST){
			retorno = SOUTH;
		}else if(this == Direction.SOUTH ){
			retorno = EAST;
		}else{
			retorno = NORTH;
		}
		return retorno;
	}
	
	/**
	 ** Public method that returns the rotating right direction in the given direction.
	 * @return Direction : right direction.
	 **/
	public Direction GirarDER(){
		//declaraciones
		Direction retorno = null;
		
		if (this == Direction.NORTH){
			retorno = Direction.EAST;
		}else if(this == Direction.EAST){
			retorno = Direction.SOUTH;
		}else if(this == Direction.SOUTH ){
			retorno = Direction.WEST;
		}else{
			retorno = NORTH;
		}
		return retorno;
	}

	/**
	 * Public method that returns the correct Wall-E image depending of the current direction.
	 * @return the correct Wall-E image depending of the current direction
	 */
	public ImageIcon conversorDirectionDraw() {
		//declaraciones
		ImageIcon retorno = null;
		
		if (this == Direction.NORTH){
			retorno = new ImageIcon(".\\src\\tp\\pr5\\gui\\images\\walleNorth.png");
		}else if(this == Direction.EAST){
			retorno = new ImageIcon(".\\src\\tp\\pr5\\gui\\images\\walleEast.png");
		}else if(this == Direction.SOUTH ){
			retorno = new ImageIcon(".\\src\\tp\\pr5\\gui\\images\\walleSouth.png");
		}else{
			retorno = new ImageIcon(".\\src\\tp\\pr5\\gui\\images\\walleWest.png");
		}
		return retorno;
	}
}



