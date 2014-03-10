package GOL;

import cellularAutomaton.CAUpdateListener;
import cellularAutomaton.CellularAutomaton;
import cellularAutomaton.Lattice;

public class RunGol implements CAUpdateListener{
	
	public static void main(String [] args) {
		CellularAutomaton s = new CellularAutomaton(){};
		GolLattice goll = new GolLattice();
		goll.readFromFile("Test5");
		s.initCA(goll);
		s.addListener(new RunGol());
		s.start();
                s.nextLattice();
               
	}

	@Override
	public void update(Lattice lat) {
		System.out.println((GolLattice)lat);
		
	}
}
