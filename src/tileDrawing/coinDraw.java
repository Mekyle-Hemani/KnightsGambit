package tileDrawing;

import main.GamePanel;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;
import java.util.List;
import entity.*;

public class CoinDraw {
    static GamePanel gp;
    private static SecureRandom secureRandom = new SecureRandom();

    public static List<Integer> coinLocations = new ArrayList<>();

    public CoinDraw(GamePanel gamePanel) throws IOException {
        this.gp = gamePanel;
    }

    private void initialize() {

    }

    public static void developCoin(int index) {
        if (secureRandom.nextInt(12) == 3){
            coinLocations.add(index);
        }
    }

    public static void iterateCoins() {
        for (int item = 0; item < coinLocations.size(); item++) {
            if (coinLocations.get(item) + (gp.screenWidth / gp.tileSize) < 187) {
                coinLocations.set(item, coinLocations.get(item) + (gp.screenWidth / gp.tileSize));
            }
        }
    }

    public static boolean compileCoins(int index){
        return coinLocations.contains(index);
    }

    public static void collectCoins(int playerIndex) {
        if (coinLocations.contains(playerIndex)) {
            coinLocations.remove((Integer) playerIndex);
            Player.money += 1;
            System.out.println("Money: "+Player.money);
        }
    }
}