package mapDevelopmentFunctions;

import main.GamePanel;
import mapGeneration.NextLineGeneration;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class TreeLocationGeneration {
    static GamePanel gp;
    public static int rows;
    public static int cols;

    static SecureRandom secureRandom = new SecureRandom();

    public TreeLocationGeneration(GamePanel gp) {
        TreeLocationGeneration.gp = gp;
    }

    public void setup() {
        rows = NextLineGeneration.getTreeRegionLength()-2;
        cols = gp.screenWidth / gp.tileSize;
    }

    public static ArrayList<Integer> generateTree() {
        ArrayList<Integer> betterGrid = new ArrayList<>();
        ArrayList<Integer> skipCols = new ArrayList<>();

        int checkingColInt;
        for (int i = 0; i < 3; ) {
            checkingColInt = secureRandom.nextInt(cols);
            if (!skipCols.contains(checkingColInt)) {
                skipCols.add(checkingColInt);
                i++;
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (skipCols.contains(j)) {
                    betterGrid.add(Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + 0)); //Ground tile
                } else {
                    if (secureRandom.nextInt(3) == 1) {
                        betterGrid.add(14); //Tree tile
                    } else {
                        betterGrid.add(Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + 0)); //Ground tile
                    }
                }
            }
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < cols; j++) {
                betterGrid.add(Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + 0)); //Ground tile
            }
        }

        return betterGrid;
    }
}
