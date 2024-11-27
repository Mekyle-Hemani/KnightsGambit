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

                    List<Integer> totalTileLocations = new ArrayList<>(nextTileLocations);
                    totalTileLocations.addAll(GamePanel.tileLocations);

                    int indexInTotalTileLocations = nextTileLocations.size();

                    if (tileType <= 20) {
                        int range = 7;
                        if (RangeChecker.isInRange(totalTileLocations, gridWidth, indexInTotalTileLocations, range, 2)) {
                            totalTile = Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + "0");
                        } else {
                            totalTile = Integer.parseInt("1" + "2");
                        }
                    } else {
                        totalTile = Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + "0");
                    }
                    nextTileLocations.add(totalTile);
                }
            }
        }
        for (int i = 0; i < gridWidth; i++) {
            GamePanel.tileLocations.addFirst(nextTileLocations.removeFirst());
        }

        // Remove the oldest tiles
        for (int i = 0; i < gridWidth; i++) {
            GamePanel.tileLocations.removeLast();
        }
    }
}

