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
     * Initializes the Cellular Automaton and setting isRunning to false and
     * store the given lattice.
     *
     * @param lat The lattice that will be used in the simulation.
     */
    public void initCA(Lattice lat) {
        lattice = lat;
        isRunnig = false;
        listeners = new ArrayList<CAUpdateListener>();
    }

    /**
     * Updates the current lattice by one tik.
     */
    public void nextLattice() {
        if (isRunnig) {
            lattice.updateLattice();
            update(lattice);
        } else {
            System.out.println("Cellular Automaton has not been started");
        }
    }

    /**
     * Invoke all of the update methods from the added listeners.
     *
     * @param lattice The updated lattice.
     */
    private void update(Lattice lattice) {
        for (CAUpdateListener listener : listeners) {
            if (listener != null) {
                listener.update(lattice);
            }
        }
    }

    /**
     * Returns the current lattice.
     *
     * @return The current lattice.
     */
    public Lattice getLattice() {
        return lattice;
    }

    /**
     * Allows the CellularAutomaton to update.
     */
    public void start() {
        isRunnig = true;
    }

    /**
     * Stops the CellularAutomaton from updating.
     */
    public void stop() {
        isRunnig = false;
    }

    /**
     * Adds a CAUpdateListener.
     *
     * @param listen the new CAUpdateListener that need to be added.
     */
    public void addListener(CAUpdateListener listen) {
        if (listen != null) {
            listeners.add(listen);
        }
    }

    /**
     * Removes a give listener
     * @param o The listener to be removed.
     */
    public void removeListener(Object o) {
        listeners.remove(o);
    }
}
