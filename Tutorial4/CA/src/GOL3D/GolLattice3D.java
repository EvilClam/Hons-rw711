package GOL3D;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import cellularAutomaton.Cell;
import cellularAutomaton.CellularAutomaton;
import cellularAutomaton.Lattice;

public class GolLattice3D implements Lattice<GolCell3D[][][]> {

    GolCell3D[][][] lattice;
    int y;
    int x;
    int z;

   public void randomCA() {
        int z = 17; 
        int y = 17;
        int x = 17;
        int dim[] = {z, y, x};
        setDimensions(dim);
        lattice = new GolCell3D[z][y][x];
        for (int k = 0; k < z; k++) {
            for (int j = 0; j < y; j++) {
                for (int i = 0; i < x; i++) {
                    lattice[k][j][i] = new GolCell3D();
                    int tmp = (int) (Math.random() * 2);
                    lattice[k][j][i].setValue(tmp);
                }
            }
        }
    }
    
    public void readFromFile(String fileName) {
        Scanner sc;
        try {
            sc = new Scanner(new File(fileName));
            String[] row1 = sc.nextLine().split(" ");
            int z = Integer.parseInt(row1[0]);
            int y = Integer.parseInt(row1[1]);
            int x = Integer.parseInt(row1[2]);
            int dim[] = {z, y, x};
            setDimensions(dim);
            lattice = new GolCell3D[z][y][x];
            for (int k = 0; k < z; k++) {
                for (int j = 0; j < y; j++) {
                    String row = sc.nextLine();
                    for (int i = 0; i < x; i++) {
                        lattice[k][j][i] = new GolCell3D();
                        int tmp = Integer.parseInt(row.charAt(i) + "");
                        lattice[k][j][i].setValue(tmp);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Invalid file");
            System.exit(0);
        }
    }

    @Override
    public void init() {
    }

    @Override
    public void updateLattice() {

        GolCell3D[][][] temp = new GolCell3D[z][y][x];
        for (int k = 0; k < z; k++) {
            for (int j = 0; j < y; j++) {
                for (int i = 0; i < x; i++) {
                    temp[k][j][i] = new GolCell3D();
                    int current[] = {k, j, i};
                    temp[k][j][i].setValue(lattice[k][j][i].applyRules(getCells(lattice[k][j][i].getNeighbours(), current)));
                }
            }
        }
        lattice = temp;
    }

    @Override
    public Cell<?> getCell(int[] pos) {
        return lattice[pos[0]][pos[1]][pos[2]];
    }

    @Override
    public void setDimensions(int[] dim) {
        z = dim[0];
        y = dim[1];
        x = dim[2];

    }

    @Override
    public int[] getDimensions() {
        return new int[]{z, y, x};
    }

    public GolCell3D[] getCells(ArrayList<Integer[]> cellPos, int[] currentPos) {
        GolCell3D[] neighbours = new GolCell3D[cellPos.size()];

        for (int i = 0; i < cellPos.size(); i++) {
            Integer[] pos = cellPos.get(i);
            pos[0] = (pos[0] + currentPos[0]) % z;
            pos[1] = (pos[1] + currentPos[1]) % y;
            pos[2] = (pos[2] + currentPos[2]) % x;

            if (pos[0] < 0) {
                pos[0] = z + pos[0];
            }

            if (pos[1] < 0) {
                pos[1] = y + pos[1];
            }

            if (pos[2] < 0) {
                pos[2] = x + pos[2];
            }

            neighbours[i] = lattice[pos[0]][pos[1]][pos[2]];
        }

        return neighbours;
    }

    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (int k = 0; k < z; k++) {
            for (int j = 0; j < y; j++) {
                for (int i = 0; i < x; i++) {
                    temp.append(lattice[k][j][i].getValue());
                }
                temp.append("\n");
            }
        }
        return temp.toString();
    }

    public GolCell3D[][][] getLattice() {
        return lattice;
    }
}
