package mapDevelopmentFunctions;

import main.GamePanel;
import mapGeneration.NextLineGeneration;
import java.util.ArrayList;

public class EntranceLocationGeneration {
    static GamePanel gp;
    public static int rows;
    public static int cols;

    public EntranceLocationGeneration(GamePanel gp) {
        EntranceLocationGeneration.gp = gp;
    }

    public static void setup() {
        rows = NextLineGeneration.getEntranceRegionLength();
        cols = gp.screenWidth / gp.tileSize;
    }

    public static ArrayList<Integer> generateEntrance() {
        ArrayList<Integer> grid = new ArrayList<>();
        int eachside;
        int middle;
        if (cols%2==0){
            middle = 2;
            eachside = cols/2;
        } else {
            middle = 3;
            eachside = (cols-2)/2;
        }
        for (int j=0; j<3; j++) {
            for (int i = 0; i < eachside; i++) {
                grid.add(14);
            }
            for (int i = 0; i < middle; i++) {
                grid.add(10);
            }
            for (int i = 0; i < eachside; i++) {
                grid.add(14);
            }
        }
        return grid;
    }
}
