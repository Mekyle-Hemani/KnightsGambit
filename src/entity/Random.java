package entity;

import main.GamePanel;
import java.security.SecureRandom;

public class Random {
    GamePanel gp;

    public Random(GamePanel gp) {
        this.gp = gp;
    }

    public static int GenRandInt(int range) {
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.nextInt(range+1);
    }
}