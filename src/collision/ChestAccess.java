package collision;

import main.GamePanel;

import java.util.ArrayList;
import java.util.List;

public class ChestAccess {
    static GamePanel gp;
    public static List<String> chestItems = new ArrayList<>();

    public void setup(){
        chestItems.add("Stick");
        chestItems.add("Stick");
        chestItems.add("Stick");
        chestItems.add("Stick");
        chestItems.add("Stick");
        chestItems.add("Stick");
        chestItems.add("Stick");
        chestItems.add("Stick");
        chestItems.add("Stick");
        chestItems.add("Stick");
    }

    public ChestAccess(GamePanel gp) {
        ChestAccess.gp = gp;
    }

    public static void logChest(int chestX, int chestY) {

    }

    public static void iterateChests() {

    }

    public static void grabItems(int chestX, int chestY) {
        //Run the inventory code with these specific items
        System.out.println("Chess being accessed! at: " + chestX + ", " + (chestY * (gp.screenWidth / gp.tileSize)));
    }
}
