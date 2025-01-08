package presetTileConfigs;

import java.security.SecureRandom;

import tileDrawing.*;

public class GroundConfig {
    static SecureRandom secureRandom = new SecureRandom();
    public static void formGroundTile(java.util.List<Integer> list, int offset) {
        //CoinDraw.developCoin(list.size()-(offset*11));
        list.add(Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + 0));
    }

    public static void formDocksTile(java.util.List<Integer> list, int offset, int tile) {
        CoinDraw.developCoin(list.size()-(offset*11));
        list.add(tile);
    }
}
