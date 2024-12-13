package mapGeneration;
import mapDevelopmentFunctions.*;
import main.GamePanel;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class NextLineGeneration {
    static GamePanel gp;
    SecureRandom secureRandom = new SecureRandom();
    public static List<Integer> nextTileLocations = new ArrayList<>();

    TreeLocationGeneration treeLocationGeneration = new TreeLocationGeneration(gp);

    public int region = 0;
    public int regionCount = 0;
    public static HashMap<Integer, Integer> regionValues = new HashMap<>();
    public int regionRange;

    public void setup() {
        regionValues.clear();
        regionValues.put(0, 7);
        regionValues.put(1, 7);

        regionRange = regionValues.size();
    }

    public static int getTreeRegionLength(){
        return regionValues.get(1);
    }

    public NextLineGeneration(GamePanel gp) {
        NextLineGeneration.gp = gp;
    }

    public void generateNextLine() {
        int gridWidth = gp.screenWidth / gp.tileSize;

        if (nextTileLocations.size() / gridWidth <= 7) {
            int count = 7 - nextTileLocations.size() / gridWidth;

            for (int l = 0; l < count; l++) {
                for (int i = 0; i < gridWidth; i++) {
                    if (region == 0) {
                        int totalTile;

                        List<Integer> totalTileLocations = new ArrayList<>(nextTileLocations);
                        totalTileLocations.addAll(GamePanel.tileLocations);

                        int indexInTotalTileLocations = nextTileLocations.size();

                        int range = secureRandom.nextInt(1, 4);
                        int compareRange = range;

                        if (compareRange % 2 == 1) {
                            compareRange -= 1;
                        }
                        compareRange = compareRange / 2;

                        compareRange += 5;

                        if (RoomRangeChecker.isInRange(totalTileLocations, gridWidth, indexInTotalTileLocations, compareRange, 2) || RoomRangeChecker.isInRange(totalTileLocations, gridWidth, indexInTotalTileLocations, compareRange, 1) || RoomRangeChecker.isInRange(totalTileLocations, gridWidth, indexInTotalTileLocations, compareRange, 1)) {
                            totalTile = Integer.parseInt(secureRandom.nextInt(5) + 1 + "0");
                            nextTileLocations.add(totalTile);
                        } else {
                            if (range == 1){
                                totalTile = Integer.parseInt(secureRandom.nextInt(5) + 1 + "0");
                            } else {
                                totalTile = 14;
                            }
                            nextTileLocations.add(totalTile);

                            if (range % 2 == 1) {
                                range -= 1;
                            }
                            range = range / 2;

                            //Add position left
                            for (int j = 0; j < range + 1; j++) {
                                totalTile = Integer.parseInt(secureRandom.nextInt(5) + 1 + "0");
                                nextTileLocations.add(totalTile);
                            }
                            int wallType = Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + 1);

                            //Add bottom door
                            int middleBottomWall = nextTileLocations.size() - (((range + 1) * gridWidth) + 2 + range);
                            nextTileLocations.set(middleBottomWall, 12);

                            for (int n = 0; n < (range + 1) * gridWidth; n++) {
                                totalTile = Integer.parseInt(secureRandom.nextInt(5) + 1 + "0");
                                nextTileLocations.add(totalTile);
                            }

                            //Bottom wall, left wall, right wall
                            int topWallHeight = 0;
                            for (int k = 0; k < range + 1; k++) {
                                nextTileLocations.set(middleBottomWall + k + 1, wallType);
                                nextTileLocations.set(middleBottomWall - k - 1, wallType);
                                if (k == range) {
                                    for (int m = 1; m < range * 2 + 3; m++) {
                                        nextTileLocations.set(middleBottomWall + k + 1 + (gridWidth * m), wallType);
                                        nextTileLocations.set(middleBottomWall - k - 1 + (gridWidth * m), wallType);
                                        topWallHeight = middleBottomWall + k + 1 + (gridWidth * m);
                                    }
                                }
                            }
                            int randomValue = secureRandom.nextInt(2);
                            for (int n = 1; n < range * 2 + 3; n++) {
                                if (n == range + 1) {
                                    if (randomValue == 0) {
                                        nextTileLocations.set(topWallHeight - n, 12);
                                    } else {
                                        nextTileLocations.set(topWallHeight - n, wallType);
                                    }
                                } else {
                                    nextTileLocations.set(topWallHeight - n, wallType);
                                }
                            }

                            i += range + 1;
                        }
                    } else if (region == 1) {
                        int[] grid = TreeLocationGeneration.generateTree();
                        System.out.println(Arrays.toString(grid));
                        int tileType = secureRandom.nextInt(1, 100);
                        if (tileType >= 80){
                            nextTileLocations.add(14);
                        } else {
                            //nextTileLocations.add(13);
                            nextTileLocations.add(Integer.parseInt(secureRandom.nextInt(5) + 1 + "0"));
                        }
                    }
                }
                regionCount += 1;
                if (regionCount >= regionValues.get(region)) {
                    if (region == 1) {
                        for (int i = 0; i < gridWidth*2; i++) {
                            //nextTileLocations.add(Integer.parseInt(secureRandom.nextInt(5) + 1 + "0"));
                            //Make sure these blocks are walkable
                            nextTileLocations.add(13);
                        }
                    }
                    region = secureRandom.nextInt(regionValues.size());
                    regionCount = 0;
                }
                //region = 1;
                System.out.println("Region: " + region);
            }
        }
        if (nextTileLocations.size()%gridWidth != 0){
            System.out.println("Error with nextTileLocations iteration: "+nextTileLocations.size()%gridWidth);
        }
        for (int i = 0; i < gridWidth; i++) {
            GamePanel.tileLocations.addFirst(nextTileLocations.removeFirst());
        }

        for (int i = 0; i < gridWidth; i++) {
            GamePanel.tileLocations.removeLast();
        }
    }
}