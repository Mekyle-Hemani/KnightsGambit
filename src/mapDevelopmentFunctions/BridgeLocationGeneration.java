package mapDevelopmentFunctions;

import main.GamePanel;
import mapGeneration.NextLineGeneration;

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
        rows = NextLineGeneration.getBridgeRegionLength();
        cols = gp.screenWidth / gp.tileSize;
    }

    public static ArrayList<Integer> generateBridge() {
        ArrayList<Integer> bridgeLocation = new ArrayList<>();
        int eachside;
        int middle;
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
            bridgeLocation.add(13);
        }
        for (int i = 0; i < eachside - 1; i++) {
            bridgeLocation.add(46);
        }
        bridgeLocation.add(17);
        bridgeLocation.add(36);
        for (int i = 0; i < eachside - 1; i++) {
            bridgeLocation.add(Integer.parseInt(secureRandom.nextInt(3) + 1 + "5"));
        }
        for (int i = 0; i < middle; i++) {
            bridgeLocation.add(13);
        }
        for (int i = 0; i < eachside - 1; i++) {
            bridgeLocation.add(Integer.parseInt(secureRandom.nextInt(3) + 1 + "5"));
        }
        bridgeLocation.add(16);

        bridgeLocation.add(37);
        for (int i = 0; i < eachside - 1; i++) {
            bridgeLocation.add(26);
        }
        for (int i = 0; i < middle; i++) {
            bridgeLocation.add(13);
        }
        for (int i = 0; i < eachside - 1; i++) {
            bridgeLocation.add(26);
        }
        bridgeLocation.add(27);

        return bridgeLocation;
    }
}
