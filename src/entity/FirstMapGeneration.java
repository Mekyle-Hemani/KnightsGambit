/*package entity;

import main.GamePanel;

import java.security.SecureRandom;

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
            GamePanel.tileLocations.add(totalTile);
            //Add each new item to the array that holds all the tiles and their asset id.
        }
    }
}*/

package entity;

import main.GamePanel;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class FirstMapGeneration {
    private final GamePanel gp;
    private SecureRandom secureRandom = new SecureRandom();

    public FirstMapGeneration(GamePanel gp) {
        this.gp = gp;
    }

    public void generateFirstMap() {
        // Generate the first room
        List<List<Integer>> room = generateRoom(gp.screenWidth / gp.tileSize, gp.screenHeight / gp.tileSize);

        // Flatten the room data into tileLocations
        for (List<Integer> line : room) {
            GamePanel.tileLocations.addAll(line);
        }

        // Initialize the room data in NextLineGeneration
        NextLineGeneration.currentRoomData = room;
        NextLineGeneration.currentRoomLineIndex = room.size();
    }

    private List<List<Integer>> generateRoom(int width, int height) {
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
        // Add a door/stair on the top wall to allow entry to the next room
        int doorPosition = secureRandom.nextInt(width - 2) + 1; // Not on corners
        int tile = Integer.parseInt("1" + "2"); // Stair/Door tile
        roomData.get(0).set(doorPosition, tile); // Replace wall with door

        return roomData;
    }
}
