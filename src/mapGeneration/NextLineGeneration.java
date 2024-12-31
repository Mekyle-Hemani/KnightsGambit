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
    SecureRandom secureRandom = new SecureRandom(); //Used for getting random values
    public static List<Integer> nextTileLocations = new ArrayList<>(); //This is the array of pre-calculated tiles not yet shown on screen

    public int region = 0; //This is the current region that the player is in
    public int regionCount = 0; //This is how many parts (Rows) of the region have been generated
    public static HashMap<Integer, Integer> regionValues = new HashMap<>(); //This is the hashmap holding the amount of parts (Rows) are in each region
    public int regionRange; //This is the total amount of region for random selection

    //These are the booleans that will change as each region is done
    public boolean regionOneDone = true;
    public boolean regionTwoDone = true;
    public boolean regionThreeDone = true;

    public void setup() {
        //This is called once before the game starts

        regionValues.clear(); //Clear any old values

        //Add the amount of parts (Rows) there are for each region
        regionValues.put(0, 7);
        regionValues.put(1, 19);
        regionValues.put(2, 4);
        regionValues.put(3, 2);

        regionRange = regionValues.size(); //Set the region range

        //Claim all regions are done as none have been drawn yet
        regionOneDone = true;
        regionTwoDone = true;
        regionThreeDone = true;
    }

    //These functions are used to pull values from NextLineGeneration.java to other scripts easier
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

    //Generate the next line to be drawn
    public void generateNextLine() {
        int gridWidth = gp.screenWidth / gp.tileSize; //This is the width of each row

        //If there are not enough pre-rendered tiles...
        if (nextTileLocations.size() / gridWidth <= 7) {
            //Determine how many pre-rendered tile rows should be calculated
            int count = 7 - nextTileLocations.size() / gridWidth;

            //For every row that needs to be rendered
            for (int l = 0; l < count; l++) {
                //For every tile that needs to be rendered
                for (int i = 0; i < gridWidth; i++) {
                    //If the region is 0...
                    if (region == 0) {
                        //The variable doorTile is the tile ID of doors
                        int doorTile = 12;
                        //There is a 50-50 chance of the door being changed to stairs for visual change
                        if (secureRandom.nextInt(2) == 0){
                            doorTile = 18; //Stair tile
                        }
                        int totalTile; //totalTile is the full tile ID to be drawn and added to the array of pre-rendered tiles

                        //This array finds out how many total tiles there are and combines all the rendered ones to the pre-rendered ones for future calculation
                        List<Integer> totalTileLocations = new ArrayList<>(nextTileLocations);
                        totalTileLocations.addAll(GamePanel.tileLocations);

                        //This helps to find out where the rendered and non-rendered tiles are in the totalTileLocations array
                        int indexInTotalTileLocations = nextTileLocations.size();

                        //Room drawing

                        //Range is the length of the room that will be drawn
                        int range = secureRandom.nextInt(1, 4);
                        int compareRange = range;

                        //This part checks the length and makes it divisible by 2, then divides it by 2
                        if (compareRange % 2 == 1) {
                            compareRange -= 1;
                        }
                        compareRange = compareRange / 2;


                        compareRange += 5; //This helps to draw the rooms a little bigger than their actual representation

                        //If the rooms range interferes or collides with any other already drawn rooms etc...
                        if (RoomRangeChecker.isInRange(totalTileLocations, gridWidth, indexInTotalTileLocations, compareRange, 2) || RoomRangeChecker.isInRange(totalTileLocations, gridWidth, indexInTotalTileLocations, compareRange, 1) || RoomRangeChecker.isInRange(totalTileLocations, gridWidth, indexInTotalTileLocations, compareRange, 1)) {
                            //Just draw a walkable ground tile and move on
                            totalTile = Integer.parseInt(secureRandom.nextInt(5) + 1 + "0");
                            nextTileLocations.add(totalTile); //Add it to the pre-rendered tiles array
                        } else {
                            //If the range is too small
                            if (range == 1){
                                //Just draw a walkable ground tile and move on
                                totalTile = Integer.parseInt(secureRandom.nextInt(5) + 1 + "0");
                            } else {
                                //Make a chest appear by 50-50 chance
                                if (secureRandom.nextInt(2) == 0){
                                    totalTile = 110; //Normal chest
                                    ChestAccess.logChest(10-nextTileLocations.size(), 0);
                                } else {
                                    totalTile = 210; //Rare chest
                                    ChestAccess.logChest(10-nextTileLocations.size(), 1);
                                }
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