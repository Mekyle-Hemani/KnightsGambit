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
        // Room dimensions
        int width = gp.screenWidth / gp.tileSize;
        int height = gp.screenHeight / gp.tileSize;

        // Generate the first room without bottom door positions
        List<List<Integer>> room = generateRoom(width, height, null);

        // Flatten the room data into tileLocations
        for (List<Integer> line : room) {
            GamePanel.tileLocations.addAll(line);
        }

        // Initialize room data in NextLineGeneration
        NextLineGeneration.currentRoomData = room;
        NextLineGeneration.currentRoomLineIndex = room.size() - 1; // Start from the bottom
        NextLineGeneration.previousRoomTopDoorPositions = getTopDoorPositions(room);
    }

    private List<List<Integer>> generateRoom(int width, int height, List<Integer> bottomDoorPositions) {
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

        // Add doors/stairs on the top and bottom walls
        addDoorsToRoom(roomData, bottomDoorPositions);

        return roomData;
    }

    private void addDoorsToRoom(List<List<Integer>> roomData, List<Integer> bottomDoorPositions) {
        int width = roomData.get(0).size();
        int numDoors = secureRandom.nextInt(2) + 1; // 1 or 2 doors

        List<Integer> topDoorPositions = new ArrayList<>();

        // Generate positions for top doors
        while (topDoorPositions.size() < numDoors) {
            int pos = secureRandom.nextInt(width - 2) + 1; // Avoid corners
            if (!topDoorPositions.contains(pos)) {
                topDoorPositions.add(pos);
            }
        }

        // Place doors on the top wall
        for (int pos : topDoorPositions) {
            roomData.get(0).set(pos, Integer.parseInt("1" + "2")); // Door tile
        }

        // If bottomDoorPositions are provided, align bottom doors
        if (bottomDoorPositions != null) {
            for (int pos : bottomDoorPositions) {
                roomData.get(roomData.size() - 1).set(pos, Integer.parseInt("1" + "2"));
            }
        } else {
            // Generate random positions for bottom doors
            List<Integer> bottomDoorPositionsNew = new ArrayList<>();
            while (bottomDoorPositionsNew.size() < numDoors) {
                int pos = secureRandom.nextInt(width - 2) + 1;
                if (!bottomDoorPositionsNew.contains(pos)) {
                    bottomDoorPositionsNew.add(pos);
                }
            }
            // Place doors on the bottom wall
            for (int pos : bottomDoorPositionsNew) {
                roomData.get(roomData.size() - 1).set(pos, Integer.parseInt("1" + "2"));
            }
        }

        // Save top door positions for the next room
        NextLineGeneration.previousRoomTopDoorPositions = topDoorPositions;
    }

    private List<Integer> getTopDoorPositions(List<List<Integer>> roomData) {
        List<Integer> topDoorPositions = new ArrayList<>();
        List<Integer> topLine = roomData.get(0);
        for (int x = 1; x < topLine.size() - 1; x++) {
            int tile = topLine.get(x);
            if (tile % 10 == 2) { // Door tile check
                topDoorPositions.add(x);
            }
        }
        return topDoorPositions;
    }
}
