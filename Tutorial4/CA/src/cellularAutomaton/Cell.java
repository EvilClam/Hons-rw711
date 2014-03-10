package cellularAutomaton;

import java.util.ArrayList;

/**
 * @author Shaun Schreiber
 */
public interface Cell<Type> {
	
	/**
	 * Calculates the neighbours relative to its position, the order is important.
	 * @return A list of arrays. Each item in the list identifies a member. 
	 */
	public ArrayList<Integer[]> getNeighbours();
	
	/**
	 * Applies a set of rules on the current cell to determine the next state. 
	 * @param neighbours The neighbours of the current Cell in the same order.
	 * @return The new value of the state.
	 */
	public Type applyRules(Cell<Type> [] neighbours);
	
	/**
	 * Returns the value of the current cell.
	 * @return Current value of the cell.
	 */
	public Type getValue();
	
	
	/**
	 * Changes the current value of the cell to the given value.
	 * @param value The new value of the current Cell.
	 */
	public void setValue(Type value);
}
