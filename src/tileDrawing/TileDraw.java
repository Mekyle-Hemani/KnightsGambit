package tileDrawing;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TileDraw {
    GamePanel gp;

    private BufferedImage drawTile; //This is what image will be drawn at the current moment
    private Map<String, BufferedImage> imageCache; //This hashmap is being treated like an image cache to help redundant image loading

    BufferedImage coinImg = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/coin/coin.png")));

    public TileDraw(GamePanel gamePanel) throws IOException {
        this.gp = gamePanel;
        imageCache = new HashMap<>(); //Finalize the hashmap
        initialize();
    }

    private void initialize() {

    }

    public void draw(Graphics2D g2) throws IOException {
        for (int i = 0; i < (gp.screenHeight / gp.tileSize); i++) {
            //For each tile on the y axis...
            for (int j = 0; j < (gp.screenWidth / gp.tileSize); j++) {
                //For each tile on the x axis...

                //Set the current item being drawn to the y axis position times length added to the x axis position in reference to the array
                //This just helps us treat 2 integers as reference to one item in an array
                int item = GamePanel.tileLocations.get((i * (gp.screenWidth / gp.tileSize) + j));

                //Have a default tile type in case of the switch statement not catching correct items
                String type = "ground";

                int checkInt;
                if (String.valueOf(item).length() == 2){
                    checkInt = Character.getNumericValue(Integer.toString(item).charAt(1));
                } else {
                    checkInt = Integer.parseInt(Integer.toString(Character.getNumericValue(Integer.toString(item).charAt(1)))+Integer.toString(Character.getNumericValue(Integer.toString(item).charAt(2))));
                }

                //This gets the second character. The second character tells us what type of item it is (Ground, Wall, Stairs)
                switch (checkInt) {
                    case 0 -> type = "ground";
                    case 1 -> type = "wall";
                    case 2 -> type = "door";
                    case 3 -> type = "docks";
                    case 4 -> type = "tree";
                    case 5 -> type = "water";
                    case 6 -> type = "waterEdge";
                    case 7 -> type = "waterCorner";
                    case 8 -> type = "stairs";
                    case 9 -> type = "docksEdge";
                    case 10 -> type = "chest";
                }

                //Since the script has many different assets for single tile types, the first number of the itemLocations array tells us what image was requested to be loaded
                int firstInteger = Character.getNumericValue(Integer.toString(item).charAt(0));

                //The key is the tile type combined with the asset id
                String key = type + firstInteger;

                //If this specific tile type and asset id combination has not been referenced before...
                if (!imageCache.containsKey(key)) {
                    //Create the image with the type and asset id
                    BufferedImage img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/" + type +"/"+ firstInteger + ".png")));

                    //Save that image to the image cache hashmap
                    imageCache.put(key, img);
                }
                //Find the requested image using the key and set the current image to be drawn to the requested one
                drawTile = imageCache.get(key);

                //Draw the image with a regulated size and given position based off of the i and j variables. (X and Y variables)
                g2.drawImage(drawTile, (j * gp.tileSize), (i * gp.tileSize), gp.tileSize, gp.tileSize, null);

                if (coinDraw.compileCoins(item)) {
                    g2.drawImage(coinImg, (j * gp.tileSize), (i * gp.tileSize), gp.tileSize, gp.tileSize, null);
                }
            }
        }
    }
}