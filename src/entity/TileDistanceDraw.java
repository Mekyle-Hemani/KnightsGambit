package entity;

import main.GamePanel;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;

public class TileDistanceDraw {
    private final GamePanel gamePanel;

    public TileDistanceDraw(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void draw(Graphics2D g2) {
        // Set font and color
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.setColor(Color.WHITE);

        // Get the width and height of the time string
        int textWidth = g2.getFontMetrics().stringWidth("test");
        int textHeight = g2.getFontMetrics().getHeight();

        int x = (gamePanel.getWidth() - textWidth) / 2;
        int y = (gamePanel.getHeight() + textHeight) / 2;

        g2.drawString("test", x, y);
    }
}