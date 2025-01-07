package presetTileConfigs;

import java.security.SecureRandom;

import tileDrawing.*;

public class GroundConfig {
    static SecureRandom secureRandom = new SecureRandom();
    public static void formGroundTile(java.util.List<Integer> list) {
        CoinDraw.developCoin(list.size());
        list.add(Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + 0));
    }
}
