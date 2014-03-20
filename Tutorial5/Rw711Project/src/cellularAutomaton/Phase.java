package cellularAutomaton;

/**
 * 
 * @author EvilClam
 *
 * @param <Type> Generic listener.
 */
public interface Phase<Type> {
	
	/**
	 * 
	 * @return
	 */
	public Type executePhase(Cell<Type> [] neighbours , Cell<Type> current);
}
