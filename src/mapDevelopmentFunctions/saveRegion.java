package mapDevelopmentFunctions;

import main.GamePanel;
import mapGeneration.NextLineGeneration;

import java.util.ArrayList;

public class saveRegion {
    static GamePanel gp;

    public static int rows;
    public static int cols;

    public static void setup() {
        rows = NextLineGeneration.getSaveRegionLength();
        cols = gp.screenWidth / gp.tileSize;
    }

    public saveRegion(GamePanel gp) {
        saveRegion.gp = gp;
    }

    public static ArrayList<Integer> generateSaveRegion() {
        ArrayList<Integer> saveRegionLocations = new ArrayList<>();
        return saveRegionLocations;
    }
}
