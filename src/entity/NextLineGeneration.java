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

        if (nextTileLocations.isEmpty()) {
            System.out.println("Generating next line");
            int count = secureRandom.nextInt(5)+1;
            for (int l = 0; l<count; l++) {
                for (int i = 0; i < (gp.screenWidth / gp.tileSize); i++) {
                    int tileType = secureRandom.nextInt(100);
                    int totalTile;

                /*int totalTile = 0;
                switch (tileType) {
                    case 0 -> totalTile = Integer.parseInt(Integer.toString(secureRandom.nextInt(5)+1) + 0);
                    //There are 5 type(s) of ground asset(s). Picking one at random.

                    case 1 -> totalTile = Integer.parseInt(Integer.toString(secureRandom.nextInt(5)+1) + 1);
                    //There are 5 type(s) of wall asset(s). Picking one at random.

                    case 2 -> totalTile = Integer.parseInt(Integer.toString(1) + 2);
                    //There are 1 type(s) of wall asset(s). Picking one at random
                }
                GamePanel.tileLocations.add(totalTile);*/

                    if ((tileType <= 2)) {
                        totalTile = Integer.parseInt(Integer.toString(1) + 2);
                        //Door
                    } else {
                        totalTile = Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + 0);
                    }
                    nextTileLocations.add(totalTile);
                    //Add each new item to the array that holds all the tiles and their asset id.
                }
            }
        } else {
            System.out.println("Not regenerating next line " + nextTileLocations.size());
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