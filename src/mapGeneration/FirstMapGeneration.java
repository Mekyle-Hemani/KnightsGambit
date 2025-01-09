package mapGeneration;

import main.GamePanel;
import java.security.SecureRandom;

import static main.GamePanel.tileLocations;

public class FirstMapGeneration {
    private final GamePanel gp;
    SecureRandom secureRandom = new SecureRandom(); //Initialize the SecureRandom library for reference

    public FirstMapGeneration(GamePanel gp) {
        this.gp = gp;
    }

    //This generates the map on start along with its visuals
    public void generateFirstMap() {
        int totalTiles = (gp.screenWidth/gp.tileSize) * (gp.screenHeight/gp.tileSize); //This is the width of the screen in tiles times the height of the screen in tiles
        //For every tile visible on screen...
        for (int i = 0; i < totalTiles; i++) {
            tileLocations.add(Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + 0));
        }
    }
}