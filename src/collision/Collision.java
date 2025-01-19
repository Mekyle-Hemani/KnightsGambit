package collision;

import dialog.DialogBox;
import main.GamePanel;
import saveFunction.*;

import java.io.IOException;

public class Collision {
    static GamePanel gp;

    public Collision(GamePanel gp) {
        Collision.gp = gp;
    } //This access the gamepanel script where lots of major values are stored such as the screen length and width

    //Sees if the players next movement is into a wall or not
    public static boolean CheckCollision(int Xpos, int Ypos, int direction) throws IOException {
        //Depending on the direction given...
        switch (direction) {
            //Change the given coordinates by one position
            case 0 -> Ypos -= 1; //Up
            case 1 -> Xpos -= 1; //Left
            case 2 -> Xpos += 1; //Right
            case 3 -> Ypos += 1; //Down
        }
        //If the player wants to leave the screen in any direction
        if (((Xpos < 0) || (Xpos >= (gp.screenWidth/gp.tileSize))) || (Ypos >= (gp.screenHeight/gp.tileSize))){
            return false; //Don't allow the requested movement
        }
        //Otherwise...
        //Check if the position that the player wants to go to is a ground tile from the tileLocations array.
        //The value of a ground tile is 1. It checks if the requested movement (From the direction integer) is into a wall or not
        //It then returns if the player can go forward (true) or not (false)

        //IMPORTANT NOTE
        //Tiles are assigned IDs for collision

        //A tile ID with the value of 21 would mean that...
        //1 is the tile type, the wall tile type

        //2 is the asset specifically in correlation to the tile type
        //That means that tile ID 21 will reference the second wall asset

        int item = (GamePanel.tileLocations.get(Ypos * (gp.screenWidth / gp.tileSize) + Xpos)); //This is the tile that the player would like to walk into

        int tileCheckCondition; //This is the condensed value of the tile, the tile type

        //Some tile IDs can have 2 digit tile types 10 rather than 2

        //This script grabs the tile type depending on if the tile ID is 2 or 3 digits long
        if ((String.valueOf(item).length()) == 2) {
            tileCheckCondition = Character.getNumericValue((Integer.toString(item)).charAt(1));
        } else {
            tileCheckCondition = Character.getNumericValue((Integer.toString(item)).charAt(1)+(Integer.toString(item)).charAt(2));
        }

        //If the player is trying to walk into a chest, tile type 10
        if (tileCheckCondition == 10) {
            //Run the item selection script from the chest at the players wanted location (Where the player is trying to go)
            ChestAccess.grabItems(Ypos * (gp.screenWidth / gp.tileSize) + Xpos);
        }

        if (tileCheckCondition == 11) {
            midGameSave.save();
            DialogBox.dialogText = "Game saved";
            DialogBox.isVisible = true;
        }

        //This is the full list of the tiles that the player can't walk into using a full boolean returning value by id
        return (tileCheckCondition != 1)
                && (tileCheckCondition != 4)
                && (tileCheckCondition != 5)
                && (tileCheckCondition != 6)
                && (tileCheckCondition != 7)
                && (tileCheckCondition != 10)
                && (tileCheckCondition != 11)
                && (tileCheckCondition != 13);
    }
}