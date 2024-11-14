package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import main.GamePanel;

import javax.imageio.ImageIO;

public class Player extends Entity{
    GamePanel gp;
    Movement Movement;

    public static int posX;
    public static int posY;

    public static BufferedImage playerImage;
    public static BufferedImage playerImageLeft;
    public static BufferedImage playerImageRight;

    public Player(GamePanel gamePanel) throws IOException {
        this.gp = gamePanel;
        initialize();
        Movement = new Movement(gp, this);
        gp.addKeyListener(Movement);
    }

    private void initialize() throws IOException {
        playerImageLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/player/playerLeft.png")));
        playerImageRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/player/playerRight.png")));
        playerImage = playerImageLeft;
        size = gp.tileSize;
        posX = ((gp.screenWidth-size)/2);
        posY = (((gp.screenHeight-size)/2)+(size*2));
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(playerImage, posX, posY, gp.tileSize, gp.tileSize, null);
    }
}