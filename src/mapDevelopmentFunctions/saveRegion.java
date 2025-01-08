package mapDevelopmentFunctions;

import main.GamePanel;
import mapGeneration.NextLineGeneration;
import presetTileConfigs.GroundConfig;

import java.security.SecureRandom;
import java.util.ArrayList;

public class saveRegion {
    static GamePanel gp;

    public static int rows;
    public static int cols;

    static SecureRandom secureRandom = new SecureRandom();

    public static void setup() {
        rows = NextLineGeneration.getSaveRegionLength();
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
