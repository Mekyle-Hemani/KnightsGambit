package collision;

import main.GamePanel;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChestAccess {
    static GamePanel gp;
    public static List<String> chestItems = new ArrayList<>();
    public static Map<Integer, List<String>> chestContents = new HashMap<>();

    static SecureRandom secureRandom = new SecureRandom();

    public static int chestItemRange = 5;

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

    public static void logChest(int position) {
        int chestItemRangeLocal = secureRandom.nextInt(1, chestItemRange);
        System.out.println("Chest at " + position + " logged");

        List<String> itemsInChest = new ArrayList<>();
        for (int i = 0; i < chestItemRangeLocal; i++) {
            itemsInChest.add(chestItems.get(secureRandom.nextInt(chestItems.size())));
        }
        chestContents.put(position, itemsInChest);

        System.out.println("Chest contents: " + chestContents.get(position));
    }

    public static void iterateChests() {
        Map<Integer, List<String>> updatedChestContents = new HashMap<>();

        for (Map.Entry<Integer, List<String>> entry : chestContents.entrySet()) {
            int newKey = entry.getKey() + (gp.screenWidth / gp.tileSize);
            updatedChestContents.put(newKey, entry.getValue());
        }

        chestContents = updatedChestContents;

        System.out.println("Updated chest positions: " + chestContents.keySet());
    }

    public static void grabItems(int position) {
        //Run the inventory code with these specific items
        System.out.println("Chest at "+position+" being accessed");
        System.out.println("Chest contents: " + chestContents.get(position));
    }
}
