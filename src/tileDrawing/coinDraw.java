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
        if (secureRandom.nextInt(9) == 3){
            coinLocations.add(index);
            System.out.println("Coin address saved "+index);
        }
    }

    public static void iterateCoins() {
        System.out.println("Iterating coins:");
        for (int item = 0; item < coinLocations.size(); item++) {
            coinLocations.set(item, coinLocations.get(item) + (11));
            System.out.println(coinLocations.get(item));
        }
        coinLocations.removeIf(number -> number > 187);
    }

    public static boolean compileCoins(int index) throws IOException {
        //Check if the given index is found in the coinLocations array
        return coinLocations.contains(index);
    }
}