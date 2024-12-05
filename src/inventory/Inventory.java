package inventory;
import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class Inventory extends Entity {
    GamePanel gp;

    public static boolean isVisible; //This will change depending on if the inventory is showing or not

    public Inventory(GamePanel GamePanel) {
        this.gp = GamePanel;
        initialize();
        isVisible = false; //Make sure the inventory starts off hidden
    }
    private void initialize() {
        //Nothing needs to initialize
    }

    public void draw(Graphics2D g2) {
        if (!isVisible) {
            return; //Do nothing if not visible
        }
        //Otherwise show a green square as an inventory mockup
        g2.setColor(Color.green);
        //Draw the square in the middle of the screen
        g2.fillRect((gp.screenWidth-size)/2, (gp.screenHeight-size)/2, gp.tileSize, gp.tileSize);
    }
}
