package mapGeneration;
import collision.ChestAccess;
import entity.Enemy;
import mapDevelopmentFunctions.*;
import main.GamePanel;
import tileDrawing.CoinDraw;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NextLineGeneration {
    static GamePanel gp; //This allows static reference to the gamepanel file
    SecureRandom secureRandom = new SecureRandom(); //Used for getting random values in world generation
    public static List<Integer> nextTileLocations = new ArrayList<>(); //This is the array of pre-calculated tiles not yet shown on screen

    public int region = 0; //This is the current region that the player is in
    public int regionCount = 0; //This is how many parts (Rows) of the region have been generated
    public static HashMap<Integer, Integer> regionValues = new HashMap<>(); //This is the hashmap holding the amount of parts (Rows) are in each region
    public int regionRange; //This is the total amount of region for random selection

    public static int lastTileSave = 0; //This is the last tile the game saved at

    //These are the booleans that will change as each region is done to know when to generate the next region
    public boolean regionOneDone = true;
    public boolean regionTwoDone = true;
    public boolean regionThreeDone = true;
    public boolean regionFourDone = true;

    public void setup() {
        //This is called once before the game starts
        regionValues.clear(); //Clear any old values

        //Add the amount of parts (Rows) there are for each region. This helps to control the actual length of the regions
        regionValues.put(0, 7);
        regionValues.put(1, 20);
        regionValues.put(2, 4);
        regionValues.put(3, 2);
        regionValues.put(4, 17);

        regionRange = regionValues.size(); //Set the region range to allow for region random generation

        //Claim all regions are done as none have been drawn yet
        regionOneDone = true;
        regionTwoDone = true;
        regionThreeDone = true;
        regionFourDone = true;
    }

    //These functions are used to pull values from NextLineGeneration.java to other scripts easier like how long the regions should develop
    public static int getTreeRegionLength(){
        return regionValues.get(1);
    }
    public static int getEntranceRegionLength(){
        return regionValues.get(2);
    }
    public static int getBridgeRegionLength(){
        return regionValues.get(3);
    }
    public static int getSaveRegionLength(){
        return regionValues.get(4);
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
                        //This is mainly used for indexing at specific locations of arrays
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
                        //The reason for this is to treat the range like a radius rather than a diameter as the script draws out in equidistant points from the middle
                        //The reason it's another variable is so that it can keep the original range for room drawing and the compareRange for comparing if other rooms will collide with it

                        compareRange += 5; //This helps to observe other items and check their collision with the to-be-drawn rooms a little bigger than they will actually br drawn

                        //If the rooms range interferes or collides with any other already drawn rooms or walls etc...
                        if (RoomRangeChecker.isInRange(totalTileLocations, gridWidth, indexInTotalTileLocations, compareRange, 2) || RoomRangeChecker.isInRange(totalTileLocations, gridWidth, indexInTotalTileLocations, compareRange, 1) || RoomRangeChecker.isInRange(totalTileLocations, gridWidth, indexInTotalTileLocations, compareRange, 1)) {
                            //Just draw a walkable ground tile and move on to the next iteration
                            totalTile = Integer.parseInt(secureRandom.nextInt(5) + 1 + "0"); //This is to generate a random ground tile //This is to generate a random ground tile
                            nextTileLocations.add(totalTile); //Add it to the pre-rendered tiles array
                        } else {
                            //If the range is too small
                            if (range == 1){
                                //Just draw a walkable ground tile and move on
                                totalTile = Integer.parseInt(secureRandom.nextInt(5) + 1 + "0"); //This is to generate a random ground tile
                            } else {
                                //Make a chest appear by 50-50 chance
                                int rarity; //This is the chest rarity (1 or 2)
                                if (secureRandom.nextInt(2) == 0){
                                    totalTile = 110; //Normal chest
                                    rarity=1;
                                } else {
                                    totalTile = 210; //Rare chest
                                    rarity=2;
                                }
                                //Save that chest combo to an array with a matching rarity to the chest tile
                                ChestAccess.logChest(10-nextTileLocations.size(), rarity);
                            }
                            nextTileLocations.add(totalTile); //Add the chest as a tile location

                            //This take the actual original range of the rooms to draw the room rather than the extended range to observe nearby walls
                            //It uses the same first calculation as the compareRange variable
                            if (range % 2 == 1) {
                                range -= 1;
                            }
                            range = range / 2;

                            //Add position left
                            //This iterates to the left for however long the range is to draw the left most wall
                            for (int j = 0; j < range + 1; j++) {
                                totalTile = Integer.parseInt(secureRandom.nextInt(5) + 1 + "0"); //This is to generate a random ground tile
                                nextTileLocations.add(totalTile);
                            }
                            int wallType = Integer.parseInt(Integer.toString(secureRandom.nextInt(5) + 1) + 1); //This is to generate a random wall tile and save it for other walls of the same room to use

                            //Add bottom door
                            int middleBottomWall = nextTileLocations.size() - (((range + 1) * gridWidth) + 2 + range); //The middle bottom wall will actually be a door rather than a wall. This allows players to enter/exit rooms
                            nextTileLocations.set(middleBottomWall, doorTile); //Add the door to the calculated tiles array

                            for (int n = 0; n < (range + 1) * gridWidth; n++) {
                                totalTile = Integer.parseInt(secureRandom.nextInt(5) + 1 + "0"); //This is to generate a random ground tile
                                nextTileLocations.add(totalTile); //This adds the ground tiles for the entire room range
                                //That way the algorithm only has to set old tile instead of iterating and setting new tiles 
                            }

                            //Bottom wall, top wall, right wall
                            //Since the left wall was already drawn...
                            int topWallHeight = 0; //This sets whatever the top wall's Y position is for possible future door adding
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
                            //This finds out if the room will have another door at the top half of it
                            int secondDoorGenerationPossibility = secureRandom.nextInt(2);
                            for (int n = 1; n < range * 2 + 3; n++) {
                                if (n == range + 1) {
                                    if (secondDoorGenerationPossibility == 0) {
                                        //If the door tile is actually a stair, draw the inverse sprite the second time
                                        if (doorTile == 18){
                                            doorTile = 28;
                                        }
                                        //If there is an exit door...
                                        nextTileLocations.set(topWallHeight - n, doorTile);
                                        //Set the wall where the exit door should be to a door
                                    } else {
                                        //If there is not an exit door...
                                        nextTileLocations.set(topWallHeight - n, wallType);
                                        //Set the wall where the exit door should be to a wall
                                    }
                                } else {
                                    //If there is not an exit door...
                                    nextTileLocations.set(topWallHeight - n, wallType);
                                    //Set the wall where the exit door should be to a wall
                                }
                            }
                            i += range + 1; //Iterate over by an extra tile from the range for proper indexing purposes. This keeps the items in each row combined to not have unfinished rows/columns
                        }
                    } else {
                        //If the selected region was not region 0
                        ArrayList<Integer> grid = new ArrayList<>(); //Create an array where the other region values will be stored
                        //If region 1 was selected...
                        if (region == 1 && regionOneDone) {
                            //Set the array to the values from the region 1 class generation function
                            grid = TreeLocationGeneration.generateTree();
                            regionOneDone = false; //Say the region is done calculating
                        //If region 2 was selected...
                        } else if (region == 2 && regionTwoDone) {
                            //Set the array to the values from the region 2 class generation function
                            grid = EntranceLocationGeneration.generateEntrance();
                            regionTwoDone = false; //Say the region is done calculating
                        //If region 3 was selected...
                        } else if (region == 3 && regionThreeDone) {
                            //Set the array to the values from the region 3 class generation function
                            grid = BridgeLocationGeneration.generateBridge();
                            regionThreeDone = false; //Say the region is done calculating
                        //If region 4 was selected...
                        } else if (region == 4 && regionFourDone) {
                            System.out.println("Pass save"); //Add debugging line
                            //Set the array to the values from the region 4 class generation function
                            grid = saveRegion.generateSaveRegion();
                            regionFourDone = false; //Say the region is done calculating
                        }
                        nextTileLocations.addAll(grid); //Add the calculated region to the pre-generated array
                        regionFourDone = false; //Say region 4 is done as it may set itself on in certain situations during region switching
                    }
                }
                //Say that whatever the region is, has progressed by an extra output
                regionCount += 1;

                //If the region has hit how many tiles it is supposed to output
                if (regionCount >= regionValues.get(region)) {
                    //And if its one of the regions that are not located in this class...
                    if (region == 1 || region == 2 || region == 3 || region == 4) {
                        //Add a small gap between regions to ensure 100% possibility to progress forever
                        for (int i = 0; i < gridWidth*2; i++) {
                            nextTileLocations.add(Integer.parseInt(secureRandom.nextInt(5) + 1 + "0")); //Adds a ground tile
                        }
                    }
                    //Set the next region to 4
                    region = 4;
                    //If the user has crossed the normal save region location or at least approached it...
                    if (GamePanel.spacesCrossed>=(lastTileSave+saveRegion.tileSaveInterval)) {
                        //Come up with the next value at which the save region will appear
                        lastTileSave+=saveRegion.tileSaveInterval; //This might need to be added by a larger difference
                        System.out.println("Last tile save: " + lastTileSave); //Print the save file
                    } else {
                        //Keep regenerating a region and only cross if its not the save region
                        while (region == 4) {
                            region = secureRandom.nextInt(regionValues.size());
                        }
                    }
                    regionCount = 0; //Set the region count to 0 to ensure that the next region doesn't think it's done before it even generates
                    //Claim all regions to be available to be generated
                    regionOneDone = true;
                    regionTwoDone = true;
                    regionThreeDone = true;
                    regionFourDone = true;
                }
            }
        }
        //If at any point there are columns/rows that aren't completely generated...
        if (nextTileLocations.size()%gridWidth != 0){
            //Print an error and the difference
            System.out.println("Error with nextTileLocations iteration: "+nextTileLocations.size()%gridWidth);
        }
        //Every time a new layer needs to be drawn
        for (int i = 0; i < gridWidth; i++) {
            //Add the new tile locations to the tiles that will be drawn and delete them from the first array they came from of pre-rendered arrays
            GamePanel.tileLocations.addFirst(nextTileLocations.removeFirst());
            //This is the next tile that will be drawn's ID
            int item = nextTileLocations.getFirst();
            int checkInt; //This is the tile type
            //If the tile ID is 2 digit
            if (String.valueOf(item).length() == 2){
                //Save the second digit as the ID
                checkInt = Character.getNumericValue(Integer.toString(item).charAt(1));
            } else {
                //Save the second and third digits ad the ID
                checkInt = Integer.parseInt(Integer.toString(Character.getNumericValue(Integer.toString(item).charAt(1)))+Integer.toString(Character.getNumericValue(Integer.toString(item).charAt(2))));
            }
            //If the ID is a ground tile...
            if (checkInt == 0){
                //Develop a possible coin
                CoinDraw.developCoin(i);
                //Develop a possible enemy
                Enemy.developEnemy(i);
            }
        }

        //Remove the next on-screen tiles that no longer need to be displayed
        //This shifts the entire index down by a row which can give the game the scrolling affect
        for (int i = 0; i < gridWidth; i++) {
            GamePanel.tileLocations.removeLast();
        }
    }
}