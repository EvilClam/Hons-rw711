package cellularAutomaton;

import java.util.ArrayList;

/**
 * @author Shaun Schreiber
 */
public abstract class Cell<Type> {
	
	private ArrayList<Phase<Type>> phases = new ArrayList<Phase<Type>>();

	/**
	 * Calculates the neighbours relative to its position, the order is
	 * important.
	 * 
	 * @return A list of arrays. Each item in the list identifies a member.
	 */
	public abstract ArrayList<Integer[]> getNeighbours();

	/**
	 * Applies a set of rules on the current cell to determine the next state.
	 * 
	 * @param neighbours
	 *            The neighbours of the current Cell in the same order.
	 * @return The new value of the state.
	 */
	public Type applyRules(Cell<Type>[] neighbours) {
		Phase<Type> currentPhase = phases.get(0);
		phases.remove(0);
		phases.add(currentPhase);
		Type returnValue = currentPhase.executePhase(neighbours, this);
		return returnValue;
	}

	/**
	 * Returns the value of the current cell.
	 * 
	 * @return Current value of the cell.
	 */
	public abstract Type getValue();

	/**
	 * Changes the current value of the cell to the given value.
	 * 
	 * @param value
	 *            The new value of the current Cell.
	 */
	public abstract void setValue(Type value);

	/**
	 * Adds the given phase to a list of phases.
	 * 
	 * @param phase The phase that will execute when applyrules is called.
	 */
	public void addPhase(Phase<Type> phase) {
		phases.add(phase);
	}

	/**
	 * Remove and returns the first element in the queue.
	 * 
	 * @return First phase element or null if queue is empty.
	 */
	public Phase<Type> removePhase() {
		if (0 < phases.size()) { 
			return phases.remove(0);
		}
		return null;
	}

	/**
	 * Remove and returns the element at the given index in the queue.
	 *
	 * @return Element at the given index or null if queue is empty or the index is out of bounds.
	 */
	public Phase<Type> removePhase(int index) {
		if (index < phases.size()) { 
			return phases.remove(index);
		}
		return null;
	}
	
	/**
	 * Note Be carefull here because phases work like listeners so you can just copy
	 * @return
	 */
	public ArrayList<Phase<Type>> getPhases() {
		return phases;
	}
	
	/**
	 * Return the number of current phases.
	 * @return The number of current phases.
	 */
	public int getNumberOfPhases() {
		return phases.size();
	}
}
