package entity;

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

    private BufferedImage drawTile;
    private Map<String, BufferedImage> imageCache;

    public TileDraw(GamePanel gamePanel) throws IOException {
        this.gp = gamePanel;
        imageCache = new HashMap<>();
        initialize();
    }

    private void initialize() throws IOException {

    }

    public void draw(Graphics2D g2) throws IOException {
        for (int i = 0; i < (gp.screenHeight / gp.tileSize); i++) {
            for (int j = 0; j < (gp.screenWidth / gp.tileSize); j++) {
                int item = GamePanel.tileLocations.get((i * (gp.screenWidth / gp.tileSize) + j));
                String type = "ground";
                switch (Character.getNumericValue(Integer.toString(item).charAt(1))) {
                    case 0 -> type = "ground";
                    case 1 -> type = "wall";
                    case 2 -> type = "stairs";
                }

                String key = type + Character.getNumericValue(Integer.toString(item).charAt(0));
                if (!imageCache.containsKey(key)) {
                    BufferedImage img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/" + type + Character.getNumericValue(Integer.toString(item).charAt(0)) + ".png")));
                    imageCache.put(key, img);
                }
                drawTile = imageCache.get(key);
                g2.drawImage(drawTile, (j * gp.tileSize), (i * gp.tileSize), gp.tileSize, gp.tileSize, null);
            }
        }
    }
}