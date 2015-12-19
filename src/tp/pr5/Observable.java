package tp.pr5;

import java.util.ArrayList;

/**
 * 
 * @author ADRIAN BERNABE, ALEJANDRO SCATTON.
 *Public class Observable. Responsible of adding or deleting observers.
 * @param <T>
 */
public class Observable<T> {
	
	protected ArrayList<T> observers = new ArrayList<T>();

	/**
	 * Public method that adds an observer if it doesn't contains an observer.
	 * @param observer
	 */
	public void addObserver(T observer ){
		if (!observers.contains(observer))
			observers.add(observer);
	}
	
	/**
	 * Public method that deletes an observer.
	 * @param observer
	 */
	public void deleteObserver(T observer){
		observers.remove(observer);
	}
}
