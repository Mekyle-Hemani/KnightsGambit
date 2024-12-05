package mapGeneration;
import entity.RangeChecker;
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
                        int range = secureRandom.nextInt(3,6);
                        if (RangeChecker.isInRange(totalTileLocations, gridWidth, indexInTotalTileLocations, range+2, 2) || RangeChecker.isInRange(totalTileLocations, gridWidth, indexInTotalTileLocations, range+2, 1)) {
                            totalTile = Integer.parseInt(secureRandom.nextInt(5) + 1 + "0");
                            nextTileLocations.add(totalTile);
                        } else {
                            //Check if the iteration is on the edge of screen
                            totalTile = Integer.parseInt("1" + "2");
                            nextTileLocations.add(totalTile);

                            if (range % 2 == 1) {
                                range -= 1;
                            }
                            range = range / 2;


                            //Add wall left
                            for (int j = 0; j < range; j++){
                                totalTile = Integer.parseInt(secureRandom.nextInt(5) + 1 + "0");
                                nextTileLocations.add(totalTile);
                            }
                            nextTileLocations.add(Integer.parseInt(Integer.toString(secureRandom.nextInt(5)+1) + 1));


                            //Add wall right
                            nextTileLocations.set(nextTileLocations.size()-range-range-3, Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + 1));

                            //Add bottom wall
                            nextTileLocations.set(nextTileLocations.size()-((((range+1)*11)+2)+range), Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + 1));

                            i+=range+1;
                        }
                    } else {
                        totalTile = Integer.parseInt(secureRandom.nextInt(5) + 1 + "0");
                        nextTileLocations.add(totalTile);
                    }
                }
                System.out.println(nextTileLocations.size()%11);
            }
        }
        for (int i = 0; i < gridWidth; i++) {
            GamePanel.tileLocations.addFirst(nextTileLocations.removeFirst());
        }

        for (int i = 0; i < gridWidth; i++) {
            GamePanel.tileLocations.removeLast();
        }
    }
}