package dialog;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class DialogBox extends Entity {
    private GamePanel gp;

    public static String dialogText = "Default dialog message.";

    public static boolean isVisible = true;

    private static final int dialogWidth = 400;
    private static final int dialogHeight = 200;
    private static final int padding = 20;
    private static final Color bgColor = new Color(0, 0, 0, 150);
    private static final Color textColor = Color.white;

    private Font customFont;

    public DialogBox(GamePanel gamePanel) throws IOException, FontFormatException {
        this.gp = gamePanel;
        loadCustomFont();
    }

    private void loadCustomFont() throws IOException, FontFormatException {
        InputStream fontStream = getClass().getResourceAsStream("/assets/font/scoreFont.ttf");
        if (fontStream != null) {
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(Font.PLAIN, 18);
        } else {
            customFont = new Font("Arial", Font.PLAIN, 18);
        }
    }

    public void draw(Graphics2D g2) {
        if (isVisible) {
            int x = (gp.screenWidth - dialogWidth) / 2;
            int y = ((gp.screenHeight/100)*80);

            g2.setColor(bgColor);
            g2.fillRoundRect(x, y, dialogWidth, dialogHeight, 20, 20);

            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(x, y, dialogWidth, dialogHeight, 20, 20);

            g2.setFont(customFont);
            g2.setColor(textColor);

            String[] lines = wrapText(dialogText, dialogWidth - 2 * padding, g2);

            int lineHeight = g2.getFontMetrics().getHeight();
            int textY = y + padding + lineHeight;
            for (String line : lines) {
                g2.drawString(line, x + padding, textY);
                textY += lineHeight;
            }
        }
    }

    private String[] wrapText(String text, int maxWidth, Graphics2D g2) {
        FontMetrics fm = g2.getFontMetrics();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        java.util.List<String> lines = new java.util.ArrayList<>();

        for (String word : words) {
            if (fm.stringWidth(line + word) < maxWidth) {
                line.append(word).append(" ");
            } else {
                lines.add(line.toString());
                line = new StringBuilder(word + " ");
            }
        }
        lines.add(line.toString());

        return lines.toArray(new String[0]);
    }
}
