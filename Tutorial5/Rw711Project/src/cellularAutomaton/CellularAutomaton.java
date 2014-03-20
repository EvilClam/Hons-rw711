package cellularAutomaton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shaun Schreiber
 */
public abstract class CellularAutomaton {
	
	private List<CAUpdateListener> listeners;
	private Lattice lattice; 
	private volatile boolean isRunnig;
	
	/**
	 * TODO:
	 */
	public void initCA(Lattice lat) 
	{
		lattice = lat;
		isRunnig = false;
		listeners = new ArrayList<CAUpdateListener>();
	}
	
	/**
	 * TODO:
	 */
	public void nextLattice() 
	{
		if (isRunnig) {
			lattice.updateLattice();
			update(lattice);
		} else {
			System.out.println("Cellular Automaton has not been started");
		}
	}
	
	/**
	 * TODO:
	 */
	private void update(Lattice lattice) 
	{
		for (CAUpdateListener listener : listeners) {
			if (listener != null) {
				listener.update(lattice);
			}
		}
	}
	
	/**
	 * TODO:
	 */
	public Lattice getLattice() 
	{
		return lattice;
	}
	
	/**
	 * TODO:
	 */
	public void start() 
	{
		isRunnig = true;
	}
	
	/**
	 * TODO:
	 */
	public void stop()
	{
		isRunnig = false;
	}
	
	/**
	 * TODO:
	 */
	public void addListener(CAUpdateListener listen)
	{
		if (listen != null){
			listeners.add(listen);
		} 
	}
	
	/**
	 * TODO:
	 */
	public void removeListener(Object o) {
		listeners.remove(o);
	}
}
