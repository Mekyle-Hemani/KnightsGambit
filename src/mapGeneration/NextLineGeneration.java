package mapGeneration;
import mapDevelopmentFunctions.*;
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
                    int totalTile;

                    List<Integer> totalTileLocations = new ArrayList<>(nextTileLocations);
                    totalTileLocations.addAll(GamePanel.tileLocations);

                    int indexInTotalTileLocations = nextTileLocations.size();

                    if (true) {
                        int range = secureRandom.nextInt(3,6);
                        if (RangeChecker.isInRange(totalTileLocations, gridWidth, indexInTotalTileLocations, range+4, 2) || RangeChecker.isInRange(totalTileLocations, gridWidth, indexInTotalTileLocations, range+4, 1)) {
                            totalTile = Integer.parseInt(secureRandom.nextInt(5) + 1 + "0");
                            nextTileLocations.add(totalTile);
                        } else {
                            //Check if the iteration is on the edge of screen
                            totalTile = Integer.parseInt(secureRandom.nextInt(5) + 1 + "0");
                            nextTileLocations.add(totalTile);

                            if (range % 2 == 1) {
                                range -= 1;
                            }
                            range = range / 2;

                            //Add wall left
                            for (int j = 0; j < range+1; j++){
                                totalTile = Integer.parseInt(secureRandom.nextInt(5) + 1 + "0");
                                nextTileLocations.add(totalTile);
                            }
                            int wallType = Integer.parseInt(Integer.toString(secureRandom.nextInt(5)+1) + 1);

                            //Add bottom door
                            int middleBottomWall = nextTileLocations.size()-(((range+1)*11)+2+range);
                            nextTileLocations.set(middleBottomWall, 12);

                            for (int n = 0; n<(range+1)*11; n++){
                                totalTile = Integer.parseInt(secureRandom.nextInt(5) + 1 + "0");
                                nextTileLocations.add(totalTile);
                            }

                            //Bottom wall, left wall, right wall
                            int topWallHeight = 0;
                            for (int k = 0; k<range+1; k++){
                                nextTileLocations.set(middleBottomWall+k+1, wallType);
                                nextTileLocations.set(middleBottomWall-k-1, wallType);
                                if (k==range){
                                    for (int m = 1; m<range*2+3; m++){
                                        nextTileLocations.set(middleBottomWall+k+1+(11*m), wallType);
                                        nextTileLocations.set(middleBottomWall-k-1+(11*m), wallType);
                                        topWallHeight = middleBottomWall+k+1+(11*m);
                                    }
                                }
                            }
                            System.out.println(range);
                            for (int n = 1; n<range*2+3; n++) {
                                nextTileLocations.set(topWallHeight - n, wallType);
                            }

                            i+=range+1;
                        }
                    } else {
                        totalTile = Integer.parseInt(secureRandom.nextInt(5) + 1 + "0");
                        nextTileLocations.add(totalTile);
                    }
                }
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