package HPP;

import java.util.ArrayList;

import cellularAutomaton.Cell;

/**
 * 
 * @author Shaun Schreiber
 *
 */
public class HppCell extends Cell<Partical[] >{
	
	private Partical[] particals;
	private ArrayList<Integer[]> sides;
	private boolean boarder;//TODO: Lose the wrap around on two edges and make them no slip boundry. 
	
	/**
	 * Initializes the LgcaCell class:
	 * 		with the given number of cells and 
	 * 		the neighbour offsets.
	 * @param numberOfCells the number of cells.
	 */
	public HppCell(short numberOfCells) {
		particals = new  Partical[numberOfCells];
		
		sides = new ArrayList<Integer[]>();
		sides.add(new Integer[] {-1,0});
		sides.add(new Integer[] {0,1});
		sides.add(new Integer[] {1,0});
		sides.add(new Integer[] {0,-1});
	}
	
	
	@Override
	public ArrayList<Integer[]> getNeighbours() {
		return sides;
	}
	
	
	@Override
	public Partical[]  getValue() {
		return particals;
	}

	@Override
	public void setValue(Partical[]  value) {
		particals = value;
	}
	
	@Override
	public HppCell clone() {
		HppCell tmp = new HppCell((short)getValue().length);
		for (int i = 0 ; i < getValue().length ; i++) {
			if (getValue()[i] != null) {
				tmp.getValue()[i] = getValue()[i].clone();
			} else {
				tmp.getValue()[i] = null;
			}
			
		}
		tmp.addPhase(getPhases().get(0));
		tmp.addPhase(getPhases().get(1));
		
		return tmp;
	}

}
