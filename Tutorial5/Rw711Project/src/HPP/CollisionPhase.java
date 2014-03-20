package HPP;

import cellularAutomaton.Cell;
import cellularAutomaton.Phase;

public class CollisionPhase implements Phase<Partical[]> {

	@Override
	public Partical[] executePhase(Cell<Partical[]>[] neighbours,
			Cell<Partical[]> current) {
		Partical[] tmp = ((HppCell) current).getValue();
		Partical[] returnNew = new Partical[4];
		for (int i = 0; i < 4; i++) {
			if (tmp[i] != null) {
				returnNew[i] = tmp[i].clone();
			}
		}
		if ((returnNew[0] != null && returnNew[2] != null)
				&& (returnNew[1] == null && returnNew[3] == null)) {
			
			returnNew[1] = returnNew[0].clone();
			returnNew[3] = returnNew[2].clone();
			returnNew[0] = null;
			returnNew[2] = null;
		} else if ((returnNew[1] != null && returnNew[3] != null)
				&& (returnNew[0] == null && returnNew[2] == null)) {
			
			returnNew[0] = returnNew[1].clone();
			returnNew[2] = returnNew[3].clone();
			returnNew[1] = null;
			returnNew[3] = null;
		}
		return returnNew;
	}

}
