/*package entity;

import main.GamePanel;

import java.security.SecureRandom;

public class NextLineGeneration {
    static GamePanel gp;
    SecureRandom secureRandom = new SecureRandom(); //Initialize the SecureRandom library for reference

    public NextLineGeneration(GamePanel gp) {
        NextLineGeneration.gp = gp;
    }

    public void generateNextLine() {
        //Repeat the following code for the tile width of the screen
        for (int i = 0; i < (gp.screenWidth / gp.tileSize); i++) {
            //What type of tile is going to be generated
            int tileType = secureRandom.nextInt(3);
            //0 = ground (Walkable)
            //1 = wall (NotWalkable)
            //2 = Stair/Door (Walkable)

            //Each type of tile has multiple different assets
            //When each tile is first created (In this script) it is also assigned one of its assets at random
            //Without the following script, the assets will randomize not showing the same type of wall when the player moves etc.

            //Total tile is the assets number + the tile type
            int totalTile = 0;
            switch (tileType) {
                case 0 -> totalTile = Integer.parseInt(Integer.toString(secureRandom.nextInt(5)+1) + 0);
                //There are 5 type(s) of ground asset(s). Picking one at random.

                case 1 -> totalTile = Integer.parseInt(Integer.toString(secureRandom.nextInt(5)+1) + 1);
                //There are 5 type(s) of wall asset(s). Picking one at random.

                case 2 -> totalTile = Integer.parseInt(Integer.toString(1) + 2);
                //There are 1 type(s) of wall asset(s). Picking one at random
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
}*/

package entity;

import main.GamePanel;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class NextLineGeneration {
    GamePanel gp;
    SecureRandom secureRandom = new SecureRandom();

    public static List<List<Integer>> currentRoomData;
    public static int currentRoomLineIndex;

    public NextLineGeneration(GamePanel gp) {
        this.gp = gp;
    }

    public void generateNextLine() {
        int width = gp.screenWidth / gp.tileSize;

        // Remove the last line from tileLocations
        for (int i = 0; i < width; i++) {
            GamePanel.tileLocations.remove(GamePanel.tileLocations.size() - 1);
        }

        // Generate the next line
        List<Integer> nextLine;

        if (currentRoomLineIndex > 0) {
            // Get the next line from the current room
            currentRoomLineIndex--;
            nextLine = currentRoomData.get(currentRoomLineIndex);
        } else {
            // Generate a new room
            currentRoomData = generateRoom(width);
            currentRoomLineIndex = currentRoomData.size() - 1;
            nextLine = currentRoomData.get(currentRoomLineIndex);
        }

        // Add the next line at the beginning of tileLocations
        GamePanel.tileLocations.addAll(0, nextLine);
    }

    private List<List<Integer>> generateRoom(int width) {
        int height = secureRandom.nextInt(5) + 5; // Height between 5 and 9
        List<List<Integer>> roomData = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            List<Integer> line = new ArrayList<>();
            for (int x = 0; x < width; x++) {
                int tile;
                if (y == 0 || y == height - 1 || x == 0 || x == width - 1) {
                    // Wall tiles at the edges
                    tile = Integer.parseInt((secureRandom.nextInt(5) + 1) + "1");
                } else {
                    // Ground tiles inside
                    tile = Integer.parseInt((secureRandom.nextInt(5) + 1) + "0");
                }
                line.add(tile);
            }
            roomData.add(line);
        }
        // Add a door/stair on the bottom wall to allow entry from the previous room
        int doorPositionBottom = secureRandom.nextInt(width - 2) + 1;
        int doorTile = Integer.parseInt("1" + "2");
        roomData.get(height - 1).set(doorPositionBottom, doorTile);

        // Add a door/stair on the top wall to allow entry to the next room
        int doorPositionTop = secureRandom.nextInt(width - 2) + 1;
        roomData.get(0).set(doorPositionTop, doorTile);

        return roomData;
    }
}
