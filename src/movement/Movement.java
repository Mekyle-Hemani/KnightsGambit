package movement; //Add to entity package

import inventory.*;
import mapDevelopmentFunctions.*;
import entity.*;
import entity.Player;
import collision.*;

//Add key listeners
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel; //Get reference to GamePanel.java

public class Movement implements KeyListener {
    GamePanel gp;
    private final Collision collision;
    private final mapGeneration.NextLineGeneration nextLineGeneration; //This is a class that generates a new line of the map

    public int verticalSquaresBackwards = 0; //This is how many squares that the player has gone back. This is used to make sure that player doesn't go back anymore than 5 squares.
    public int spacesCrossed = 0; //This is how many total squares the player has crossed

    public int locationX = (Player.posX / Player.size); //This is the current X location in terms of tiles of the player
    public int locationY = (Player.posY / Player.size); //This is the current Y location in terms of tiles of the player

    public int location;

    public Movement(GamePanel gp) {
        this.gp = gp;
        this.nextLineGeneration = new mapGeneration.NextLineGeneration(gp); //Makes the NextLineGeneration referencable
        this.collision = new Collision(gp);
    }

    @Override
    //Every time a key is pressed
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode(); //This is the most recent key pressed by the player
        String invButton = "i"; //This is the button that activates the inventory
        int targetKeyCode = KeyEvent.getExtendedKeyCodeForChar(invButton.charAt(0)); //Converts the target keycode to a format that can be compared with the getKeyCode function.
        if (keyCode == targetKeyCode) {
            Inventory.isVisible = !Inventory.isVisible; //Invert the visibility of the inventory
        }
    }

    @Override
    //Every time a key is released...
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode(); //This is the most recent key pressed by the player

        //If "up" was pressed
        if (keyCode == KeyEvent.VK_UP) {
            //Check collision using the collision class
            if ((collision.CheckCollision(locationX, locationY, 0))) {
                //If the player is back to the middle of the screen and has not moved backwards...
                if (verticalSquaresBackwards == 0) {
                    spacesCrossed++; //Increase the spaces crossed
                    //
                    nextLineGeneration.generateNextLine(); //Draw the next section of the map
                } else {
                    Player.posY -= Entity.size; //Move the player up on the screen
                    verticalSquaresBackwards--; //Bring the amount of tiles the player is away from the middle down by one
                    spacesCrossed++; //Increase the amount of spaces crossed
                }
            }
        }

        //If "down" was pressed
        if (keyCode == KeyEvent.VK_DOWN) {
            //Check collision using the collision class
            if ((collision.CheckCollision(locationX, locationY, 3))) {
                //If the player is not at the very end...
                if (verticalSquaresBackwards <= 5) {
                    verticalSquaresBackwards++; //Bring the amount of tiles the player is away from the middle up by one
                    spacesCrossed--; //Decrease the amount of spaces crossed
                    Player.posY += Entity.size; //Move the player down on the screen
                }
            }
        }

        //If "left" was pressed
        if (keyCode == KeyEvent.VK_LEFT) {
            //Check collision using the collision class
            if ((collision.CheckCollision(locationX, locationY, 1))) {
                //If the player isn't on the left edge...
                if ((Player.posX) > 0){
                    Player.posX -= Entity.size; //Move the player left on the screen once
                }
            }
            Player.playerImage = Player.playerImageLeft; //Change the player image to the right direction
        }

        //If "right" was pressed
        if (keyCode == KeyEvent.VK_RIGHT) {
            //Check collision using the collision class
            if ((collision.CheckCollision(locationX, locationY, 2))) {
                //If the player isn't on the right edge...
                if ((Player.posX + Entity.size) < (gp.screenWidth)){
                    Player.posX += Entity.size; //Move the player right on the screen once
                }
            }
            Player.playerImage = Player.playerImageRight; //Change the player image to the right direction
        }

        int tileCheckCondition = Character.getNumericValue((Integer.toString(GamePanel.tileLocations.get((Player.posY/Player.size) * (gp.screenWidth / gp.tileSize) + (Player.posX/Player.size)))).charAt(1));
        if (tileCheckCondition == 3 || tileCheckCondition == 9) {
            if (Player.playerImage.equals(Player.playerImageRight)) {
                Player.playerImage = Player.playerImageRightClear;
            } else if (Player.playerImage.equals(Player.playerImageLeft)){
                Player.playerImage = Player.playerImageLeftClear;
            }
        }

        //Update the locations of the player
        locationX = (Player.posX / Entity.size);
        locationY = (Player.posY / Entity.size);
        location = (locationY*(gp.screenWidth/gp.tileSize))+locationX;

        GamePanel.spacesCrossed = spacesCrossed;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}