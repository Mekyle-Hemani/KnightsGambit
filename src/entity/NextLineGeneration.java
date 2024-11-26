package entity;
import main.GamePanel;
import java.security.SecureRandom;
import java.util.ArrayList;

public class NextLineGeneration {
    static GamePanel gp;
    SecureRandom secureRandom = new SecureRandom();
    public static java.util.List<Integer> nextTileLocations = new ArrayList<>();
    public static java.util.List<Integer> totalTileLocations = new ArrayList<>();

    public NextLineGeneration(GamePanel gp) {
        NextLineGeneration.gp = gp;
    }

    public void generateNextLine() {
        //Repeat the following code for the tile width of the screen

        if (nextTileLocations.size()/(gp.screenWidth/gp.tileSize) <= 7) {
            int count = 7 - nextTileLocations.size()/(gp.screenWidth/gp.tileSize);
            for (int l = 0; l<count; l++) {
                for (int i = 0; i < (gp.screenWidth / gp.tileSize); i++) {
                    int tileType = secureRandom.nextInt(100);
                    int totalTile;

                    if (tileType <= 20) {
                        totalTileLocations = new ArrayList<>();
                        totalTileLocations.addAll(nextTileLocations);
                        for (int j = 0; j<((gp.screenWidth / gp.tileSize)-i); j++){
                            totalTileLocations.add(10);
                            System.out.println(j+" "+i);
                        }
                        totalTileLocations.addAll(GamePanel.tileLocations);

                        if (RangeChecker.isInRange(totalTileLocations, (gp.screenWidth/gp.tileSize), i, 5, 2)){
                            System.out.println("Room Location Removed");
                            totalTile = Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + 0);
                        } else{
                            System.out.println("Room Location Established");
                            totalTile = Integer.parseInt(Integer.toString(1) + 2);
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