package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import main.GamePanel;

import javax.imageio.ImageIO;

public class Player extends Entity{
    GamePanel gp;
    Movement Movement; //Adds the movement class so that movement can modify the variables in this script

    public static int posX; //This is how the x value of the player will be stored
    public static int posY; //This is how the y value of the player will be stored

    public static BufferedImage playerImage; //Creates a player image
    public static BufferedImage playerImageLeft; //Creates a player image
    public static BufferedImage playerImageRight; //Creates a player image

    public Player(GamePanel gamePanel) throws IOException {
        this.gp = gamePanel;
        initialize();
        Movement = new Movement(gp);
        gp.addKeyListener(Movement);
    }

    private void initialize() throws IOException {
        playerImageLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/player/playerLeft.png"))); //Sets an image to the left player side
        playerImageRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/player/playerRight.png"))); //Sets an image to the right player side
        playerImage = playerImageLeft; //sets the current player image to the left image
        size = gp.tileSize; //Sets the player size to the average tile size
        posX = ((gp.screenWidth-size)/2); //Sets the player x to the middle x
        posY = (((gp.screenHeight-size)/2)+(size*2)); //Sets the player y to the middle y
    }

    //Draws the image to the given position and size
    public void draw(Graphics2D g2) {
        g2.drawImage(playerImage, posX, posY, gp.tileSize, gp.tileSize, null);
    }
}