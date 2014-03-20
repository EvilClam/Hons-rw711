package FHP;

import cellularAutomaton.Cell;
import cellularAutomaton.Phase;

public class PropagationPhase implements Phase<Partical[]>{

	@Override
	public Partical[] executePhase(Cell<Partical[]>[] neighbours,
			Cell<Partical[]> current) {
		Partical[] newValue = new Partical[current.getValue().length];
		for (int i = 0 ; i < 4 ; i++) {
			if (neighbours[(i + 2)%4] != null && neighbours[(i + 2)%4].getValue()[i] != null) {
				newValue[i] = (neighbours[(i + 2)%4].getValue()[i]).clone();
			} else {
				newValue[i] = null;
			}	
		}
		return newValue;
	}

}
