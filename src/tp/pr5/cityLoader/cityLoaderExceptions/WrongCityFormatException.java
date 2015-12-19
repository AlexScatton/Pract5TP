package tp.pr5.cityLoader.cityLoaderExceptions;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 *
 */
public class WrongCityFormatException  extends Throwable {

	public WrongCityFormatException(){
		
	}
	
	public WrongCityFormatException (String msg){
		super(msg);
	}
	
	public WrongCityFormatException(String msg, Throwable arg){
		
	}
	
	public WrongCityFormatException (Throwable arg){
		
	}
}
