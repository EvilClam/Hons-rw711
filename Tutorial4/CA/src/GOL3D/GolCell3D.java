package GOL3D;

import java.util.ArrayList;

import cellularAutomaton.Cell;

public class GolCell3D implements Cell<Integer>{

	Integer value;
        
        public GolCell3D() {
            super();
        }

        
	@Override
	public ArrayList<Integer[]> getNeighbours() {
		ArrayList<Integer[]> neighbours = new ArrayList<Integer[]>();
                int[] neigh ={-1,0,1};
                for (int k  = 0 ; k < 3 ; k++) {
                    for (int j  = 0 ; j < 3 ; j++) {
                        for (int i  = 0 ; i < 3 ; i++) {
                            if (!((neigh[k] == 0) && (neigh[j] == 0) && (neigh[i] == 0))) {
                                Integer[] currentNeighbours = {neigh[k], neigh[j], neigh[i]};
                                neighbours.add(currentNeighbours);
                            }
                        }
                    }
                }
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
               
                
		
		if (((numberOfAlive == 5) )&& (value == 0)) {
			return 1;
		}
                
                if (((numberOfAlive >= 4) && (numberOfAlive <= 5) )&& (value == 1)) {
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
