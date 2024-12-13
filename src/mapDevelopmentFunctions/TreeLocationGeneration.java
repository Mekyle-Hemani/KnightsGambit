package mapDevelopmentFunctions;

import main.GamePanel;
import mapGeneration.NextLineGeneration;
import java.util.ArrayList;
import java.util.Random;

public class TreeLocationGeneration {
    static GamePanel gp;
    public static int rows;
    public static int cols;

    public TreeLocationGeneration(GamePanel gp) {
        TreeLocationGeneration.gp = gp;
    }

    public void setup() {
        rows = NextLineGeneration.getTreeRegionLength();
        cols = gp.screenWidth / gp.tileSize;
    }

    public static int[] generateTree() {
        int[] grid = new int[rows * cols];
        ArrayList<Integer> openRows = new ArrayList<>();
        Random rand = new Random();

        while (openRows.size() < 3) {
            int randomRow = rand.nextInt(rows);
            if (!openRows.contains(randomRow)) {
                openRows.add(randomRow);
            }
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int index = r * cols + c;
                grid[index] = (!openRows.contains(r) && rand.nextInt(100) < 20) ? 1 : 0;
            }
        }

        for (int openRow : openRows) {
            for (int c = 0; c < cols; c++) {
                int index = openRow * cols + c;
                grid[index] = 0;
            }
        }

        return grid;
    }
}
