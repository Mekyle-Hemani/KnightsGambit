package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class Enemy {
    public static BufferedImage boxEnemyImg;
    public static BufferedImage squidEnemyImg;

    static {
        try {
            boxEnemyImg = ImageIO.read(Objects.requireNonNull(Enemy.class.getResourceAsStream("/assets/enemy/1.png")));
            squidEnemyImg = ImageIO.read(Objects.requireNonNull(Enemy.class.getResourceAsStream("/assets/enemy/2.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static GamePanel gp;

    private static SecureRandom secureRandom = new SecureRandom();

    public static Map<Integer, Integer> enemyLocations = new HashMap<>();

    public Enemy(GamePanel gamePanel) throws IOException {
        this.gp = gamePanel;
    }

    private void initialize() {
        // Initialization logic if needed
    }

    public static void developEnemy(int index) {
        if (!enemyLocations.containsKey(index) && secureRandom.nextInt(12) == 3) {
            enemyLocations.put(index, developEnemyAsset());
        }
    }

    public static int developEnemyAsset(){
        int total = 11;
        int squid = 4;
        if (secureRandom.nextInt(total) <= squid) return 112;
        return 212;
    }

    public static boolean compileEnemy(int index) {
        return enemyLocations.containsKey(index);
    }
}
