package presetTileConfigs;

import java.security.SecureRandom;
import java.util.ArrayList;
import tileDrawing.*;

public class GroundConfig {
    static SecureRandom secureRandom = new SecureRandom();
    public static void formGroundTile(java.util.List<Integer> list) {
        coinDraw.developCoin(list.size());
        list.add(Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + 0));
    }
}
