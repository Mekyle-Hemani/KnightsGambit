package tileDrawing;

import main.GamePanel;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;
import java.util.List;

public class coinDraw {
    GamePanel gp;
    private static SecureRandom secureRandom = new SecureRandom();

    public static List<Integer> coinLocations = new ArrayList<>();

    private void initialize() {

    }

    public static void developCoin(int index) {
        if (secureRandom.nextInt(12) == 3){
            coinLocations.add(index);
        }
    }

    public static void iterateCoins() {
        System.out.println("Iterating coins:");
        for (int item = 0; item < coinLocations.size(); item++) {
            if (coinLocations.get(item) + (11) < 187) {
                coinLocations.set(item, coinLocations.get(item) + (11));
                System.out.println(coinLocations.get(item));
            }
        }
    }

    public static boolean compileCoins(int index) throws IOException {
        //Check if the given index is found in the coinLocations array
        return coinLocations.contains(index);
    }
}