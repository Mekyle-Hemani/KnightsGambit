package mapDevelopmentFunctions;

import main.GamePanel;
import mapGeneration.NextLineGeneration;
import presetTileConfigs.*;

import java.security.SecureRandom;
import java.util.ArrayList;

public class BridgeLocationGeneration {
    static GamePanel gp;
    static SecureRandom secureRandom = new SecureRandom();
    public static int rows;
    public static int cols;

    public BridgeLocationGeneration(GamePanel gp) {
        BridgeLocationGeneration.gp = gp;
    }

    public static void setup() {
        rows = NextLineGeneration.getBridgeRegionLength()-1;
        cols = gp.screenWidth / gp.tileSize;
    }

    public static ArrayList<Integer> generateBridge() {
        ArrayList<Integer> bridgeLocation = new ArrayList<>();
        int eachside;
        int middle;

        int newRows = rows + secureRandom.nextInt(7);

        System.out.println(newRows-rows-2);

        if (cols%2==0){
            middle = 2;
            eachside = cols/2;
        } else {
            middle = 3;
            eachside = (cols-2)/2;
        }
        bridgeLocation.add(47);
        for (int i = 0; i < eachside - 1; i++) {
            bridgeLocation.add(46);
        }
        for (int i = 0; i < middle; i++) {
            bridgeLocation.add(29);
            //GroundConfig.formDocksTile(bridgeLocation, 10, 29);
        }
        for (int i = 0; i < eachside - 1; i++) {
            bridgeLocation.add(46);
        }
        bridgeLocation.add(17);

        for (int j = 0; j < newRows-2; j++) {
            bridgeLocation.add(36);
            for (int i = 0; i < eachside - 1; i++) {
                bridgeLocation.add(Integer.parseInt(secureRandom.nextInt(3) + 1 + "5"));
            }
            for (int i = 0; i < middle; i++) {
                bridgeLocation.add(13);
                //GroundConfig.formDocksTile(bridgeLocation, 10, 13);
            }
            for (int i = 0; i < eachside - 1; i++) {
                bridgeLocation.add(Integer.parseInt(secureRandom.nextInt(3) + 1 + "5"));
            }
            bridgeLocation.add(16);
        }

        bridgeLocation.add(37);
        for (int i = 0; i < eachside - 1; i++) {
            bridgeLocation.add(26);
        }
        for (int i = 0; i < middle; i++) {
            bridgeLocation.add(19);
            //GroundConfig.formDocksTile(bridgeLocation, 10, 19);
        }
        for (int i = 0; i < eachside - 1; i++) {
            bridgeLocation.add(26);
        }
        bridgeLocation.add(27);

        for (int i = 0; i < cols; i++){
            bridgeLocation.add(Integer.parseInt(Integer.toString(secureRandom.nextInt(5)+1) + 0));
        }

        return bridgeLocation;
    }
}
