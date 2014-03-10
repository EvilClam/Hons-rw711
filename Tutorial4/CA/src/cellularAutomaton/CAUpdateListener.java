package cellularAutomaton;
/**
 * @author Shaun Schreiber
 */
public interface CAUpdateListener {
	
    /**
     * This function is called after a lattice has been updated.
     * @param lat The updated lattice.
     */
	public void update(Lattice lat);
	
}
