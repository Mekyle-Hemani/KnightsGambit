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
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.setColor(Color.WHITE);
        g2.drawString(Integer.toString(gamePanel.spacesCrossed), (gamePanel.getWidth() - (g2.getFontMetrics().stringWidth(Integer.toString(gamePanel.spacesCrossed)))) / 2, gamePanel.tileSize);
    }
}