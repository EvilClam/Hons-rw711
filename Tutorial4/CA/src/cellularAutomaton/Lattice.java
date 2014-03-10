package cellularAutomaton;

public interface Lattice <Type> {
	
	/**
	 * Initializes a new Lattice. 
	 */
	public void init();
	
	/**
	 * Updates the current lattice.
	 * 
	 * Iterates once over each cell and applies their individual rules.
	 * These results are stored and overrides the current values of the lattice.
	 */
	public void updateLattice();
	
	/**
	 * Returns the current lattice.
	 */
	public Type getLattice();
	
	/**
	 * Returns the Cell at the given position.
	 * 
	 * @param pos The index of the Cell.
	 * @return The Cell at the given position.
	 */
	public Cell<?> getCell(int[] pos);
	
	/**
	 * Sets the new dimensions of the current lattice.
	 * @param dim The dimensions of the new lattice.
	 */
	public void setDimensions(int[] dim);
	
	/**
	 * Returns the dimensions of the current lattice.
	 * @return The dimensions of the current lattice.
	 */
	public int[] getDimensions();
}
