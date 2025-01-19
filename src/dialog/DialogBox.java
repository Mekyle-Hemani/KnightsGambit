package dialog;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class DialogBox extends Entity {
    private GamePanel gp;

    public static String dialogText = "Default dialog message."; //This is the string that can be edited to change the words displayed on the text box

    public static boolean isVisible = false; //This can be changed for the visibility of the dialog box

    private static final int dialogWidth = 400; //This is the dialog box width
    private static final int dialogHeight = 200; //This is the dialog box height
    private static final int padding = 20; //This is the padding size of the text in the box
    private static final Color bgColor = new Color(0, 0, 0, 150); //This is the dialog box background colour
    private static final Color textColor = Color.white; //This is the text colour

    private Font customFont; //This is the font that will be used

    public DialogBox(GamePanel gamePanel) throws IOException, FontFormatException {
        this.gp = gamePanel;
        customFont = GamePanel.loadCustomFont(18, Font.PLAIN); //Load the custom font
    }

    public void draw(Graphics2D g2) {
        if (isVisible) { //If the box is supposed to be visible
            int x = (gp.screenWidth - dialogWidth) / 2; //This is the x position that the box will be drawn in (The middle)
            int y = ((gp.screenHeight/100)*80); //This is the y position that the box will be drawn in (The lower section)

            g2.setColor(bgColor); //This sets the box colour
            g2.fillRoundRect(x, y, dialogWidth, dialogHeight, 20, 20); //Draws the round rectangle using the variables specified

            g2.setColor(Color.white); //This sets the outline colour
            g2.setStroke(new BasicStroke(2)); //This is the border length (2 pixels)
            g2.drawRoundRect(x, y, dialogWidth, dialogHeight, 20, 20); //This draws the outline of the box

            g2.setFont(customFont); //This sets the text font
            g2.setColor(textColor); //This sets the text colour

            String[] lines = wrapText(dialogText, dialogWidth - 2 * padding, g2); //This indents the text depending on the length of it

            int lineHeight = g2.getFontMetrics().getHeight(); //This gets the font height
            int textY = y + padding + lineHeight; //This sets the y of where the text will draw
            for (String line : lines) { //For each split line in the input text...
                g2.drawString(line, x + padding, textY); //Draw the line
                textY += lineHeight; //Change the height for the next line
            }
        }
    }

    private String[] wrapText(String text, int maxWidth, Graphics2D g2) {
        FontMetrics fm = g2.getFontMetrics(); //Get the dimensions of the font
        String[] words = text.split(" "); //Split by words
        StringBuilder line = new StringBuilder(); //This is where the line will be returned
        java.util.List<String> lines = new java.util.ArrayList<>(); //This is where each line is developed

        for (String word : words) { //For every seperated word...
            if (fm.stringWidth(line + word) < maxWidth) { //If it is less than the maximum length that can be drawn on that line
                line.append(word).append(" "); //Add a space and add the word to the line
            } else { //If it doesn't fit
                lines.add(line.toString()); //Add the completed line to the list of lines
                line = new StringBuilder(word + " "); //Start a new line with the current word
            }
        }
        lines.add(line.toString()); //Add the string to the result

        return lines.toArray(new String[0]); //Return the result
    }
}
