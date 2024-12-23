package mapGeneration;
import collision.ChestAccess;
import mapDevelopmentFunctions.*;
import main.GamePanel;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NextLineGeneration {
    static GamePanel gp;
    SecureRandom secureRandom = new SecureRandom();
    public static List<Integer> nextTileLocations = new ArrayList<>();

    public int region = 0;
    public int regionCount = 0;
    public static HashMap<Integer, Integer> regionValues = new HashMap<>();
    public int regionRange;
    public boolean regionOneDone = true;
    public boolean regionTwoDone = true;
    public boolean regionThreeDone = true;

    public void setup() {
        regionValues.clear();
        regionValues.put(0, 7);
        regionValues.put(1, 19);
        regionValues.put(2, 4);
        regionValues.put(3, 2);

        regionRange = regionValues.size();

        regionOneDone = true;
        regionTwoDone = true;
        regionThreeDone = true;
    }

    public static int getTreeRegionLength(){
        return regionValues.get(1);
    }
    public static int getEntranceRegionLength(){
        return regionValues.get(2);
    }
    public static int getBridgeRegionLength(){
        return regionValues.get(3);
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
                        int doorTile = 12;
                        if (secureRandom.nextInt(2) == 0){
                            doorTile = 18;
                        }
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
                                totalTile = 110; //Chest
                                ChestAccess.logChest(10-nextTileLocations.size());
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
                            nextTileLocations.set(middleBottomWall, doorTile);

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
                                        if (doorTile == 18){
                                            doorTile = 28;
                                        }
                                        nextTileLocations.set(topWallHeight - n, doorTile);
                                    } else {
                                        nextTileLocations.set(topWallHeight - n, wallType);
                                    }
                                } else {
                                    nextTileLocations.set(topWallHeight - n, wallType);
                                }
                            }
                            i += range + 1;
                        }




                    } else if (region == 1 && regionOneDone) {
                        int[] grid = TreeLocationGeneration.generateTree();
                        for (int value : grid) {
                            if (value == 0) {
                                nextTileLocations.add(Integer.parseInt(secureRandom.nextInt(5) + 1 + "0"));
                            } else {
                                nextTileLocations.add(14);
                            }
                        }
                        regionOneDone = false;

                    } else if (region == 2 && regionTwoDone) {
                        ArrayList<Integer> grid = EntranceLocationGeneration.generateEntrance();
                        nextTileLocations.addAll(grid);
                        regionTwoDone = false;
                    } else if (region == 3 && regionThreeDone) {
                        ArrayList<Integer> bridgeLocations = BridgeLocationGeneration.generateBridge();
                        nextTileLocations.addAll(bridgeLocations);
                        regionThreeDone = false;
                    }
                }
                regionCount += 1;
                if (regionCount >= regionValues.get(region)) {
                    if (region == 1 || region == 2 || region == 3) {
                        for (int i = 0; i < gridWidth*2; i++) {
                            nextTileLocations.add(Integer.parseInt(secureRandom.nextInt(5) + 1 + "0"));
                        }
                    }
                    region = secureRandom.nextInt(regionValues.size());
                    regionCount = 0;
                    regionOneDone = true;
                    regionTwoDone = true;
                    regionThreeDone = true;
                }
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