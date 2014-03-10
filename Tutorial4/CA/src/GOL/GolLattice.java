package GOL;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import cellularAutomaton.Cell;
import cellularAutomaton.Lattice;

public class GolLattice implements Lattice<GolCell[][]> {

    GolCell[][] lattice;
    int y;
    int x;

    public void randomCA() {
        int y = 17;
        int x = 17;
        int dim[] = {y, x};
        setDimensions(dim);
        lattice = new GolCell[y][x];
        for (int j = 0; j < y; j++) {
            for (int i = 0; i < x; i++) {
                lattice[j][i] = new GolCell();
                int tmp = (int) (Math.random() * 2);
                lattice[j][i].setValue(tmp);
            }
        }
    }

    public void readFromFile(String fileName) {
        Scanner sc;
        try {
            sc = new Scanner(new File(fileName));
            String[] row1 = sc.nextLine().split(" ");
            int y = Integer.parseInt(row1[0]);
            int x = Integer.parseInt(row1[1]);
            int dim[] = {y, x};
            setDimensions(dim);
            lattice = new GolCell[y][x];
            for (int j = 0; j < y; j++) {
                String row = sc.nextLine();
                for (int i = 0; i < x; i++) {
                    lattice[j][i] = new GolCell();
                    int tmp = Integer.parseInt(row.charAt(i) + "");
                    lattice[j][i].setValue(tmp);
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

        GolCell[][] temp = new GolCell[y][x];
        for (int j = 0; j < y; j++) {
            for (int i = 0; i < x; i++) {
                temp[j][i] = new GolCell();
                int current[] = {j, i};
                temp[j][i].setValue(lattice[j][i].applyRules((Cell<Integer>[]) getCells(lattice[j][i].getNeighbours(), current)));
            }
        }
        lattice = temp;
    }

    @Override
    public Cell<?> getCell(int[] pos) {
        return (Cell<?>) lattice[pos[0]][pos[1]];
    }

    @Override
    public void setDimensions(int[] dim) {
        y = dim[0];
        x = dim[1];

    }

    @Override
    public int[] getDimensions() {
        return new int[]{y, x};
    }

    public GolCell[] getCells(ArrayList<Integer[]> cellPos, int[] currentPos) {
        GolCell[] neighbours = new GolCell[cellPos.size()];

        for (int i = 0; i < cellPos.size(); i++) {
            Integer[] pos = cellPos.get(i);
            pos[0] = (pos[0] + currentPos[0]) % y;
            pos[1] = (pos[1] + currentPos[1]) % x;

            if (pos[0] < 0) {
                pos[0] = y + pos[0];
            }

            if (pos[1] < 0) {
                pos[1] = x + pos[1];
            }

            neighbours[i] = lattice[pos[0]][pos[1]];
        }

        return neighbours;
    }

    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (int j = 0; j < y; j++) {
            for (int i = 0; i < x; i++) {
                temp.append(lattice[j][i].getValue());
            }
            temp.append("\n");
        }
        return temp.toString();
    }

    public GolCell[][] getLattice() {
        return lattice;
    }
}
