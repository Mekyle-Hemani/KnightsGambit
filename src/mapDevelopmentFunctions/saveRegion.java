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

    public static ArrayList<Integer> saveMapArray = new ArrayList<>(Arrays.asList(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 1, 1, 1, 0, 0, 0, 0, 47, 46, 46,
            0, 1, 0, 12, 0, 0, 0, 0, 36, 5, 5,
            0, 1, 1, 1, 0, 0, 0, 0, 36, 5, 5,
            0, 0, 0, 0, 0, 0, 0, 0, 36, 5, 5,
            1, 1, 1, 1, 1, 0, 0, 0, 37, 5, 5,
            1, 0, 0, 0, 114, 0, 0, 0, 0, 36, 5,
            1, 0, 0, 0, 114, 0, 0, 0, 0, 36, 5,
            1, 0, 0, 0, 114, 0, 0, 0, 0, 37, 26,
            1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0,
            0, 110, 110, 110, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 111, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 113, 113, 113, 0, 0, 0, 0, 0, 0, 0,
            0, 113, 113, 113, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    );

    public static void setup() {
        rows = NextLineGeneration.getSaveRegionLength()-1;
        cols = gp.screenWidth / gp.tileSize;
    }

    public saveRegion(GamePanel gp) {
        saveRegion.gp = gp;
    }

    public static ArrayList<Integer> generateSaveRegion() {
        ArrayList<Integer> returnArray = new ArrayList<>(saveMapArray);

        int wallTileType = Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + 1);

        for (int i = 0; i < saveMapArray.size(); i++) {
            if (saveMapArray.get(i) == 0) {
                returnArray.set(i, Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + 0));
            } else if (saveMapArray.get(i) == 1) {
                returnArray.set(i, wallTileType);
            } else if (saveMapArray.get(i) == 5) {
                returnArray.set(i, Integer.parseInt(Integer.toString(secureRandom.nextInt(3) + 1) + 5));
            }
        }

        return returnArray;
    }
}
