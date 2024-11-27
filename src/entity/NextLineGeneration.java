package entity;
import main.GamePanel;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class NextLineGeneration {
    static GamePanel gp;
    SecureRandom secureRandom = new SecureRandom();
    public static List<Integer> nextTileLocations = new ArrayList<>();

    public NextLineGeneration(GamePanel gp) {
        NextLineGeneration.gp = gp;
    }

    public void generateNextLine() {
        int gridWidth = gp.screenWidth / gp.tileSize;

        if (nextTileLocations.size() / gridWidth <= 7) {
            int count = 7 - nextTileLocations.size() / gridWidth;
            for (int l = 0; l < count; l++) {
                for (int i = 0; i < gridWidth; i++) {
                    int tileType = secureRandom.nextInt(100);
                    int totalTile;

                    // Build totalTileLocations
                    List<Integer> totalTileLocations = new ArrayList<>(nextTileLocations);
                    totalTileLocations.addAll(GamePanel.tileLocations);

                    // Index of the tile we are generating in totalTileLocations
                    int indexInTotalTileLocations = nextTileLocations.size();

                    if (tileType <= 20) {
                        // Try to place a stair tile
                        int range = 10; // Specify the range to check
                        if (RangeChecker.isInRange(totalTileLocations, gridWidth, indexInTotalTileLocations, range, 2)) {
                            // Stair tile nearby, place a ground tile instead
                            totalTile = Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + "0");
                        } else {
                            // No stair tile nearby, place a stair tile
                            totalTile = Integer.parseInt("1" + "2");
                        }
                    } else {
                        // Place a ground tile
                        totalTile = Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + "0");
                    }
                    nextTileLocations.add(totalTile);
                }
            }
        }

        // Add new tiles to GamePanel.tileLocations
        //int gridWidth = gp.screenWidth / gp.tileSize;
        for (int i = 0; i < gridWidth; i++) {
            GamePanel.tileLocations.addFirst(nextTileLocations.remove(0));
        }

        // Remove the oldest tiles
        for (int i = 0; i < gridWidth; i++) {
            GamePanel.tileLocations.removeLast();
        }
    }
}

