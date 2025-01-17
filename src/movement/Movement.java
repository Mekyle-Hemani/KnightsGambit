package movement; //Add to entity package

import dialog.DialogBox;
import inventory.*;
import tileDrawing.*;
import entity.*;
import entity.Player;
import collision.*;
import saveFunction.*;

//Add key listeners
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.GamePanel; //Get reference to GamePanel.java

public class Movement implements KeyListener {
    GamePanel gp;
    private final Collision collision;
    private final mapGeneration.NextLineGeneration nextLineGeneration; //This is a class that generates a new line of the map

    public int verticalSquaresBackwards = 0; //This is how many squares that the player has gone back. This is used to make sure that player doesn't go back anymore than 5 squares.
    public static int spacesCrossed = 0; //This is how many total squares the player has crossed

    public int locationX = (Player.posX / Player.size); //This is the current X location in terms of tiles of the player
    public int locationY = (Player.posY / Player.size); //This is the current Y location in terms of tiles of the player

    private final List<Integer> continueKeys = new ArrayList<Integer>();

    public int location;

    public Movement(GamePanel gp) {
        this.gp = gp;
        this.nextLineGeneration = new mapGeneration.NextLineGeneration(gp); //Makes the NextLineGeneration referencable
        this.collision = new Collision(gp);
        setup();
    }

    public void setup(){
        continueKeys.add(KeyEvent.VK_W); //W
        continueKeys.add(KeyEvent.VK_A); //A
        continueKeys.add(KeyEvent.VK_S); //S
        continueKeys.add(KeyEvent.VK_D); //D

        continueKeys.add(KeyEvent.VK_UP); //Up
        continueKeys.add(KeyEvent.VK_DOWN); //Down
        continueKeys.add(KeyEvent.VK_LEFT); //Left
        continueKeys.add(KeyEvent.VK_RIGHT); //Right

        continueKeys.add(KeyEvent.VK_I); //I
        continueKeys.add(KeyEvent.VK_SPACE); //Space
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
        if (continueKeys.contains(keyCode)) {
            if (DialogBox.isVisible) {
                DialogBox.isVisible = false;
            } else if (!Inventory.isVisible) {
                //If "up" was pressed
                if (keyCode == KeyEvent.VK_UP||keyCode == KeyEvent.VK_W) {
                    //Check collision using the collision class
                    try {
                        if ((collision.CheckCollision(locationX, locationY, 0))) {
                            //If the player is back to the middle of the screen and has not moved backwards...
                            if (verticalSquaresBackwards == 0) {
                                spacesCrossed++; //Increase the spaces crossed
                                ChestAccess.iterateChests();
                                Enemy.iterateEnemy();
                                CoinDraw.iterateCoins();
                                nextLineGeneration.generateNextLine(); //Draw the next section of the map
                            } else {
                                Player.posY -= Entity.size; //Move the player up on the screen
                                verticalSquaresBackwards--; //Bring the amount of tiles the player is away from the middle down by one
                                spacesCrossed++; //Increase the amount of spaces crossed
                            }
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                //If "down" was pressed
                if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                    //Check collision using the collision class
                    try {
                        if ((collision.CheckCollision(locationX, locationY, 3))) {
                            //If the player is not at the very end...
                            if (verticalSquaresBackwards <= 5) {
                                verticalSquaresBackwards++; //Bring the amount of tiles the player is away from the middle up by one
                                spacesCrossed--; //Decrease the amount of spaces crossed
                                Player.posY += Entity.size; //Move the player down on the screen
                            }
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                //If "left" was pressed
                if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                    //Check collision using the collision class
                    try {
                        if ((collision.CheckCollision(locationX, locationY, 1))) {
                            //If the player isn't on the left edge...
                            if ((Player.posX) > 0) {
                                Player.posX -= Entity.size; //Move the player left on the screen once
                            }
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Player.playerImage = Player.playerImageLeft; //Change the player image to the right direction
                }

                //If "right" was pressed
                if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                    //Check collision using the collision class
                    try {
                        if ((collision.CheckCollision(locationX, locationY, 2))) {
                            //If the player isn't on the right edge...
                            if ((Player.posX + Entity.size) < (gp.screenWidth)) {
                                Player.posX += Entity.size; //Move the player right on the screen once
                            }
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Player.playerImage = Player.playerImageRight; //Change the player image to the right direction
                }

                int tileCheckCondition = Character.getNumericValue((Integer.toString(GamePanel.tileLocations.get((Player.posY / Player.size) * (gp.screenWidth / gp.tileSize) + (Player.posX / Player.size)))).charAt(1));
                if (tileCheckCondition == 3 || tileCheckCondition == 9) {
                    if (Player.playerImage.equals(Player.playerImageRight)) {
                        Player.playerImage = Player.playerImageRightClear;
                    } else if (Player.playerImage.equals(Player.playerImageLeft)) {
                        Player.playerImage = Player.playerImageLeftClear;
                    }
                }

                //Update the locations of the player
                locationX = (Player.posX / Entity.size);
                locationY = (Player.posY / Entity.size);
                location = (locationY * (gp.screenWidth / gp.tileSize)) + locationX;

                CoinDraw.collectCoins(location);

                GamePanel.spacesCrossed = spacesCrossed;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}