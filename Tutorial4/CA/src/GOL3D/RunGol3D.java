package GOL3D;

import cellularAutomaton.CAUpdateListener;
import cellularAutomaton.CellularAutomaton;
import cellularAutomaton.Lattice;

public class RunGol3D implements CAUpdateListener{
	
	public static void main(String [] args) {
		CellularAutomaton s = new CellularAutomaton(){};
		GolLattice3D goll = new GolLattice3D();
		goll.readFromFile("Test13D.txt");
		s.initCA(goll);
		s.addListener(new RunGol3D());
		s.start();
                System.out.println((GolLattice3D)s.getLattice());
                s.nextLattice();
               
	}

	@Override
	public void update(Lattice lat) {
		System.out.println((GolLattice3D)lat);
		
	}
}
