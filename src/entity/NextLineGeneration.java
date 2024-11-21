package entity;
import main.GamePanel;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
public class NextLineGeneration {
    static GamePanel gp;
    SecureRandom secureRandom = new SecureRandom();

    public NextLineGeneration(GamePanel gp) {
        NextLineGeneration.gp = gp;
    }
    public void generateNextLine() {
        //Repeat the following code for the tile width of the screen
        for (int i = 0; i < (gp.screenWidth / gp.tileSize); i++) {
            int tileType = secureRandom.nextInt(100);

            int totalTile;

            if (tileType <= 2) {
                totalTile = Integer.parseInt(Integer.toString(1) + 2);
            } else {
                totalTile = Integer.parseInt(Integer.toString(secureRandom.nextInt(5)+1) + 0);
            }
            GamePanel.tileLocations.addFirst(totalTile);
            //Add each new item to the array that holds all the tiles and their asset id.
        }

        //Repeat the following code for the tile width of the screen
        for (int i = 0; i < (gp.screenWidth / gp.tileSize); i++) {
            //Remove the oldest tiles
            GamePanel.tileLocations.removeLast();
        }
    }
}//*/