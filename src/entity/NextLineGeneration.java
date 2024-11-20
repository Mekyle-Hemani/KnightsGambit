package entity;

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
            /*totalTile = Integer.parseInt(Integer.toString(secureRandom.nextInt(5)+1) + 0); //These generate floor blocks
            totalTile = Integer.parseInt(Integer.toString(secureRandom.nextInt(5)+1) + 1); //These generate wall blocks
            totalTile = Integer.parseInt(Integer.toString(1) + 2);*/ //These generate stair blocks

            //GamePanel.tileLocations.addFirst(totalTile); //This is how you add the block to what will be rendered
        }
        for (int i = 0; i < (gp.screenWidth / gp.tileSize); i++) {
            //Remove the oldest tiles
            GamePanel.tileLocations.removeLast();
        }
    }
}