package tileDrawing;

import main.GamePanel;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class TileDistanceDraw {
    private final GamePanel gamePanel;
    private Font customFont;

    public TileDistanceDraw(GamePanel gamePanel) throws IOException, FontFormatException {
        this.gamePanel = gamePanel;
        loadCustomFont();
    }

    private void loadCustomFont() throws IOException, FontFormatException {
        InputStream fontStream = getClass().getResourceAsStream("/assets/font/scoreFont.ttf");
        if (fontStream != null) {
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(Font.BOLD, 38);
        } else {
            customFont = new Font("Arial", Font.BOLD, 38);
        }
    }

    public void draw(Graphics2D g2) {
        g2.setFont(customFont);
        g2.setColor(Color.WHITE);
        String distanceText = Integer.toString(GamePanel.spacesCrossed);
        g2.drawString(distanceText, (gamePanel.getWidth() - g2.getFontMetrics().stringWidth(distanceText)) / 2, gamePanel.tileSize);
    }
}