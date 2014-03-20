package HPP;

import java.util.ArrayList;

import cellularAutomaton.Cell;
import cellularAutomaton.Lattice;

public class HppLattice implements Lattice<HppCell[][]> {

	private HppCell[][] lattice;
	private int y;
	private int x;

	@Override
	public void init() {
		lattice = new HppCell[100][100];
		y = 100;
		x = 100;
		for (int j = 0; j < 100; j++) {
			for (int i = 0; i < 100; i++) {
				lattice[j][i] = new HppCell((short) 4);
				lattice[j][i].addPhase(new CollisionPhase());
				lattice[j][i].addPhase(new PropagationPhase());
			}
		}
	}

	@Override
	public void updateLattice() {

		int y = lattice.length;
		int x = lattice[0].length;

		for (int phase = 0; phase < lattice[0][0].getNumberOfPhases(); phase++) {
			HppCell[][] tmpLat = copyGrid();
			for (int j = 0; j < y; j++) {
				for (int i = 0; i < x; i++) {
					tmpLat[j][i].setValue(tmpLat[j][i].applyRules(getNeighbours(new int[] { j, i },
							lattice[j][i].getNeighbours())));
				}
			}
			lattice = tmpLat;
		}

	}

	@Override
	public HppCell[][] getLattice() {
		// TODO Auto-generated method stub
		return lattice;
	}

	@Override
	public Cell<?> getCell(int[] pos) {
		// TODO Auto-generated method stub
		return lattice[pos[0]][pos[1]];
	}

	@Override
	public void setDimensions(int[] dim) {
		y = dim[0];
		x = dim[1];

	}

	@Override
	public int[] getDimensions() {

		return new int[] { y, x };
	}

	private Cell<Partical[]>[] getNeighbours(int[] currentPos,
			ArrayList<Integer[]> offsets) {
		HppCell[] neighbours = new HppCell[offsets.size()];
		for (int cnt = 0; cnt < offsets.size(); cnt++) {
			Integer[] pos = offsets.get(cnt);
			pos[0] = (pos[0] + currentPos[0]) % y;
			pos[1] = (pos[1] + currentPos[1]) % x;

			if (pos[0] < 0) {
				pos[0] = y + pos[0];
			}

			if (pos[1] < 0) {
				pos[1] = x + pos[1];
			}

			neighbours[cnt] = lattice[pos[0]][pos[1]];
		}
		return neighbours;
	}

	/**
	 * 
	 * @param fileName
	 */
	public void inputGrid(String fileName) {

	}

	/**
	 * y: 2 -> 100 x: 2 -> 100 Particals per/cell: 0 - 4 direction: (0)positive
	 * - (1)negative
	 */
	public void randomGrid() {
		
		y = 5;
		x = 5;
		lattice = new HppCell[y][x];
		for (int j = 0; j < y; j++) {
			for (int i = 0; i < x; i++) {
				lattice[j][i] = new HppCell((short) 4);
				lattice[j][i].addPhase(new CollisionPhase());
				lattice[j][i].addPhase(new PropagationPhase());
				int NOP = (int) (Math.random() * 5);
				for (int currentP = 0; currentP < NOP; currentP++) {
					int position = (int) (Math.random() * 4);
					Partical[] particals = lattice[j][i].getValue();
					particals[position] = new Partical();
					lattice[j][i].setValue(particals);
				}
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder totall = new StringBuilder();
		for (int j = 0; j < y; j++) {
			ArrayList<Partical> top = new ArrayList<Partical>();
			ArrayList<Partical> mid = new ArrayList<Partical>();
			ArrayList<Partical> bot = new ArrayList<Partical>();
			//Build up the current row
			for (int i = 0; i < x; i++) {
				top.add(lattice[j][i].getValue()[0]);
				mid.add(lattice[j][i].getValue()[3]);
				mid.add(lattice[j][i].getValue()[1]);
				bot.add(lattice[j][i].getValue()[2]);
			}

			//Print the current row
			for (int i = 0; i < x; i++) {
				Partical current = top.get(0);
				top.remove(0);
				if (current != null) {
					totall.append(" X  ");	
				} else {
					totall.append(" 0  ");
				}
				
			}
			totall.append("\n");
			for (int i = 0; i < x; i++) {
				Partical current = mid.get(0);
				mid.remove(0);
				if (current != null) {
					totall.append("X");	
				} else {
					totall.append("0");
				}
				totall.append("+");
				current = mid.get(0);
				mid.remove(0);
				if (current != null) {
					totall.append("X ");	
				} else {
					totall.append("0 ");
				}
				
			}
			totall.append("\n");
			for (int i = 0; i < x; i++) {
				Partical current = bot.get(0);
				bot.remove(0);
				if (current != null) {
					totall.append(" X  ");	
				} else {
					totall.append(" 0  ");
				}
				
			}
			totall.append("\n");
			totall.append("\n");
		}
		return totall.toString();
	}
	
	public HppCell[][] copyGrid() {
		HppCell[][] tmpLat = new HppCell[y][x];
		for (int j = 0 ; j < y ; j++) {
			for (int i = 0 ; i < x ; i++) {
				tmpLat[j][i] = lattice[j][i].clone();
			}
		}
		return tmpLat;
	}
	
	public static void main (String [] args) {
		HppLattice s= new HppLattice();
		s.randomGrid();
		System.out.println(s.toString());
		s.updateLattice();
		System.out.println("--------------------------------------------");
		System.out.println(s.toString());
		
	}
}
