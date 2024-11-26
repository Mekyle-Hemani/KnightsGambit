package entity;
import main.GamePanel;
import java.security.SecureRandom;
import java.util.ArrayList;

public class NextLineGeneration {
    static GamePanel gp;
    SecureRandom secureRandom = new SecureRandom();
    public static java.util.List<Integer> nextTileLocations = new ArrayList<>();

    public NextLineGeneration(GamePanel gp) {
        NextLineGeneration.gp = gp;
    }

    public void generateNextLine() {
        //Repeat the following code for the tile width of the screen

        if (nextTileLocations.size()/(gp.screenWidth/gp.tileSize) <= 7) {
            System.out.println("Generating next line");
            int count = 7 - nextTileLocations.size()/(gp.screenWidth/gp.tileSize);
            for (int l = 0; l<count; l++) {
                for (int i = 0; i < (gp.screenWidth / gp.tileSize); i++) {
                    int tileType = secureRandom.nextInt(100);
                    int totalTile;

                    if ((tileType <= 80)) {
                        int range = secureRandom.nextInt(3, 7);
                        totalTile = Integer.parseInt(Integer.toString(1) + 2);
                        //Stair
                        if (RangeChecker.isInRange(nextTileLocations, (gp.screenWidth/gp.tileSize), i, 3, 2)){
                            totalTile = Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + 0);
                        }
                    } else {
                        totalTile = Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + 0);
                    }
                    nextTileLocations.add(totalTile);
                    //Add each new item to the array that holds all the tiles and their asset id.
                }
            }
        }

        for (int i = 0; i < (gp.screenWidth / gp.tileSize); i++) {
            GamePanel.tileLocations.addFirst(nextTileLocations.getFirst());
            nextTileLocations.removeFirst();
        }

        //Repeat the following code for the tile width of the screen
        for (int i = 0; i < (gp.screenWidth / gp.tileSize); i++) {
            //Remove the oldest tiles
            GamePanel.tileLocations.removeLast();
        }
    }
}//*/