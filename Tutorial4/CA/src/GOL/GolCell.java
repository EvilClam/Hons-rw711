package GOL;

import java.util.ArrayList;

import cellularAutomaton.Cell;

public class GolCell implements Cell<Integer>{

	Integer value;
        public GolCell() {
            super();
        }

        
	@Override
	public ArrayList<Integer[]> getNeighbours() {
		ArrayList<Integer[]> neighbours = new ArrayList<Integer[]>();
		neighbours.add(new Integer[]{-1,-1});
		neighbours.add(new Integer[]{-1,0});
		neighbours.add(new Integer[]{-1,1});
		neighbours.add(new Integer[]{0,-1});
		neighbours.add(new Integer[]{0,1});
		neighbours.add(new Integer[]{1,-1});
		neighbours.add(new Integer[]{1,0});
		neighbours.add(new Integer[]{1,1});
		return neighbours;
	}


	@Override
	public Integer applyRules(Cell<Integer>[] neighbours) {
		
		int numberOfAlive = 0; 
		for (int i = 0; i < neighbours.length ; i++) {
			if (neighbours[i].getValue() != 0) {
				numberOfAlive++;
			}
		}
		
		if ((value == 0 && numberOfAlive == 3) || (value == 1 && (numberOfAlive == 2 || numberOfAlive == 3))) {
			return 1;
		}
		
		return 0;
	}


	@Override
	public Integer getValue() {
		return value;
	}


	@Override
	public void setValue(Integer value) {
		this.value = value;
		
	}

}
