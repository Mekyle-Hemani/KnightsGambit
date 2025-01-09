package mapDevelopmentFunctions;

import main.GamePanel;
import mapGeneration.NextLineGeneration;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

public class saveRegion {
    static GamePanel gp;

    public static int rows;
    public static int cols;

    public static int tileSaveInterval = 50;

    static SecureRandom secureRandom = new SecureRandom();

    /*ArrayList<Integer> list = new ArrayList<>(Arrays.asList(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 1, 1, 1, 0, 0, 0, 0, 7, 6, 6,
            0, 1, 0, 2, 0, 0, 0, 0, 6, 5, 5,
            0, 1, 1, 1, 0, 0, 0, 0, 6, 5, 5,
            0, 0, 0, 0, 0, 0, 0, 0, 6, 5, 5,
            1, 1, 1, 1, 1, 0, 0, 0, 7, 5, 5,
            1, x, x, 0, 8, 0, 0, 0, 0, 6, 5,
            1, x, x, 0, 8, 0, 0, 0, 0, 6, 5,
            1, x, x, 0, 8, 0, 0, 0, 0, 6, 7,
            1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0,
            0, 10, 10, 10, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 11, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, x, x, x, 0, 0, 0, 0, 0, 0, 0,
            0, x, x, x, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    );*/

    public static void setup() {
        rows = NextLineGeneration.getSaveRegionLength()-1;
        cols = gp.screenWidth / gp.tileSize;
    }

    public saveRegion(GamePanel gp) {
        saveRegion.gp = gp;
    }

    public static ArrayList<Integer> generateSaveRegion() {
        ArrayList<Integer> saveRegionLocations = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (j == 3 && i == 3) {
                    saveRegionLocations.add(111);
                } else {
                    saveRegionLocations.add(Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + 0));
                }
            }
        }
        for (int i = 0; i < cols; i++){
            saveRegionLocations.add(Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + 0));
        }
        return saveRegionLocations;
    }
}
