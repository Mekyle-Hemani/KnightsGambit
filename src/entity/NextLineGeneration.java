package entity;
import main.GamePanel;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
public class NextLineGeneration {
    static GamePanel gp;
    SecureRandom secureRandom = new SecureRandom();
    private boolean inRoom = false;
    private int roomHeight;
    private int roomCurrentRow = 0;
    private int roomStartColumn;
    private int roomEndColumn;
    private int roomEntranceColumn;
    private int roomOptionalExitColumn;
    private boolean hasOptionalExit = false;
    private int previousRoomEntranceColumn = -1;
    private int previousRoomOptionalExitColumn = -1;
    public NextLineGeneration(GamePanel gp) {
        NextLineGeneration.gp = gp;
    }
    public void generateNextLine() {
        int numColumns = gp.screenWidth / gp.tileSize;
        List<Integer> newRowTiles = new ArrayList<>();
        if (!inRoom) {
            if (secureRandom.nextInt(100) < 20) {
                inRoom = true;
                int roomWidth = secureRandom.nextInt(4) + 3;
                roomHeight = secureRandom.nextInt(4) + 3;
                roomCurrentRow = 0;
                roomStartColumn = secureRandom.nextInt(numColumns - roomWidth + 1);
                roomEndColumn = roomStartColumn + roomWidth - 1;
                roomEntranceColumn = secureRandom.nextInt(roomWidth - 2) + roomStartColumn + 1;
                hasOptionalExit = (secureRandom.nextInt(100) < 50);
                if (hasOptionalExit) {
                    do {
                        roomOptionalExitColumn = secureRandom.nextInt(roomWidth - 2) + roomStartColumn + 1;
                    } while (roomOptionalExitColumn == roomEntranceColumn);
                } else {
                    roomOptionalExitColumn = -1;
                }
            } else {
                for (int col = 0; col < numColumns; col++) {
                    int tile;
                    if (col == previousRoomEntranceColumn || col == previousRoomOptionalExitColumn) {
                        tile = generateGroundTile();
                    } else {
                        tile = generateRandomTile();
                    }
                    newRowTiles.add(tile);
                }
                previousRoomEntranceColumn = -1;
                previousRoomOptionalExitColumn = -1;
                for (int i = newRowTiles.size() - 1; i >= 0; i--) {
                    GamePanel.tileLocations.addFirst(newRowTiles.get(i));
                }
                for (int i = 0; i < numColumns; i++) {
                    GamePanel.tileLocations.removeLast();
                }
                return;
            }
        }
        for (int col = 0; col < numColumns; col++) {
            int tile;
            if (col < roomStartColumn || col > roomEndColumn) {
                tile = generateRandomTile();
            } else {
                if (roomCurrentRow == 0) {
                    if (col == roomOptionalExitColumn && hasOptionalExit) {
                        tile = generateOptionalStairTile();
                    } else {
                        tile = generateWallTile();
                    }
                } else if (roomCurrentRow == roomHeight - 1) {
                    if (col == roomEntranceColumn) {
                        tile = generateStairTile();
                    } else {
                        tile = generateWallTile();
                    }
                } else {
                    if (col == roomStartColumn || col == roomEndColumn) {
                        tile = generateWallTile();
                    } else {
                        tile = generateFloorTile();
                    }
                }
            }
            newRowTiles.add(tile);
        }
        roomCurrentRow++;
        if (roomCurrentRow >= roomHeight) {
            inRoom = false;
            previousRoomEntranceColumn = roomEntranceColumn;
            previousRoomOptionalExitColumn = hasOptionalExit ? roomOptionalExitColumn : -1;
        }
        for (int i = newRowTiles.size() - 1; i >= 0; i--) {
            GamePanel.tileLocations.addFirst(newRowTiles.get(i));
        }
        for (int i = 0; i < numColumns; i++) {
            GamePanel.tileLocations.removeLast();
        }
    }
    private int generateRandomTile() {
        int rand = secureRandom.nextInt(100);
        if (rand < 20) {
            return generateWallTile();
        } else {
            return generateGroundTile();
        }
    }
    private int generateGroundTile() {
        int base = secureRandom.nextInt(5) + 1;
        return Integer.parseInt(base + "0");
    }
    private int generateWallTile() {
        int base = secureRandom.nextInt(5) + 1;
        return Integer.parseInt(base + "1");
    }
    private int generateFloorTile() {
        return generateGroundTile();
    }
    private int generateStairTile() {
        return 12;
    }
    private int generateOptionalStairTile() {
        return 13;
    }
}