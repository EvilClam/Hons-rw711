package FHP;

import java.util.ArrayList;

import cellularAutomaton.Cell;

/**
 * 
 * @author Shaun Schreiber
 *
 */
public class FHPCell extends Cell<Partical[] >{
	
	private Partical[] particals;
	private ArrayList<Integer[]> sides;
	private boolean boarder;//TODO: Lose the wrap around on two edges and make them no slip boundry. 
	private boolean even;
	/**
	 * Initializes the LgcaCell class:
	 * 		with the given number of cells and 
	 * 		the neighbour offsets.
	 * @param numberOfCells the number of cells.
	 */
	public FHPCell(short numberOfCells, boolean even) {
		
		this.particals = new  Partical[numberOfCells];
		this.even = even;
		
		if (even) {
			sides = new ArrayList<Integer[]>();
			sides.add(new Integer[] {-1,0});
			sides.add(new Integer[] {-1,1});
			sides.add(new Integer[] {0,1});
			sides.add(new Integer[] {1,1});
			sides.add(new Integer[] {1,0});
			sides.add(new Integer[] {0,-1});
		} else {
			sides = new ArrayList<Integer[]>();
			sides.add(new Integer[] {-1,-1});
			sides.add(new Integer[] {-1,0});
			sides.add(new Integer[] {0,1});
			sides.add(new Integer[] {1,0});
			sides.add(new Integer[] {1,-1});
			sides.add(new Integer[] {0,-1});
		}
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
	public FHPCell clone() {
		FHPCell tmp = new FHPCell((short)getValue().length, even);
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
