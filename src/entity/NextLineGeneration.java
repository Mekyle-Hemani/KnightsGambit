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
    public static List<Integer> previousRoomTopDoorPositions;

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

        if (currentRoomLineIndex >= 0) {
            // Get the next line from the current room
            nextLine = currentRoomData.get(currentRoomLineIndex);
            currentRoomLineIndex--;
        } else {
            // Generate a new room with aligned doors
            int height = secureRandom.nextInt(5) + 5; // Height between 5 and 9
            currentRoomData = generateRoom(width, height, previousRoomTopDoorPositions);
            currentRoomLineIndex = currentRoomData.size() - 1;
            nextLine = currentRoomData.get(currentRoomLineIndex);
            currentRoomLineIndex--;
        }

        // Add the next line at the beginning of tileLocations
        GamePanel.tileLocations.addAll(0, nextLine);
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

        // Align bottom doors with previous room's top doors
        if (bottomDoorPositions != null && !bottomDoorPositions.isEmpty()) {
            for (int pos : bottomDoorPositions) {
                roomData.get(roomData.size() - 1).set(pos, Integer.parseInt("1" + "2")); // Door tile
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
        previousRoomTopDoorPositions = topDoorPositions;
    }
}
