package mapDevelopmentFunctions;

import main.GamePanel;
import mapGeneration.NextLineGeneration;
import java.util.ArrayList;

public class BridgeLocationGeneration {
    static GamePanel gp;
    public static int rows;
    public static int cols;

    public BridgeLocationGeneration(GamePanel gp) {
        BridgeLocationGeneration.gp = gp;
    }

    public static void setup() {
        rows = NextLineGeneration.getBridgeRegionLength();
        cols = gp.screenWidth / gp.tileSize;
    }

    public static ArrayList<Integer> generateBridge() {
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
        for (int j=0; j<rows; j++) {
            grid.add(71);
            for (int i = 0; i < eachside - 1; i++) {
                grid.add(64);
            }
            for (int i = 0; i < middle; i++) {
                grid.add(10);
            }
            for (int i = 0; i < eachside - 1; i++) {
                grid.add(64);
            }
            grid.add(71);
        }
        return grid;
    }
}
