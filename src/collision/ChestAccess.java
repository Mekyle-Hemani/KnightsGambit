package collision;

import inventory.Inventory;
import main.GamePanel;

import java.security.SecureRandom;
import java.util.*;

public class ChestAccess {
    static GamePanel gp;
    public static List<String> chestItems = new ArrayList<>();
    public static Map<Integer, List<String>> chestContents = new HashMap<>();

    static SecureRandom secureRandom = new SecureRandom();

    public static int chestItemRange = 5;

    public void setup(){
        chestItems.add("Stick");
        chestItems.add("Sword");
        chestItems.add("Shield");
        chestItems.add("Health Potion");
        chestItems.add("Money");
        chestItems.add("Sand");
        chestItems.add("Rocks");
        chestItems.add("Armour");
        chestItems.add("Meat");
        chestItems.add("Effect Potion");
    }

    public ChestAccess(GamePanel gp) {
        ChestAccess.gp = gp;
    }

    public static void logChest(int position, int rarity) {
        int chestItemRangeLocal = secureRandom.nextInt(1, chestItemRange);
        if (rarity == 1) {
            chestItemRangeLocal += 3;
        }

        List<String> itemsInChest = new ArrayList<>();
        for (int i = 0; i < chestItemRangeLocal; i++) {
            itemsInChest.add(chestItems.get(secureRandom.nextInt(chestItems.size())));
        }
        chestContents.put(position, itemsInChest);
    }

    public static void iterateChests() {
        Map<Integer, List<String>> updatedChestContents = new HashMap<>();

        for (Map.Entry<Integer, List<String>> entry : chestContents.entrySet()) {
            int newKey = entry.getKey() + (gp.screenWidth / gp.tileSize);
            updatedChestContents.put(newKey, entry.getValue());
        }

        chestContents = updatedChestContents;

        Iterator<Map.Entry<Integer, List<String>>> iterator = chestContents.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, List<String>> entry = iterator.next();
            if (entry.getKey() > 187) {
                iterator.remove();
            }
        }
    }

    public static void grabItems(int position) {
        //Run the inventory code with these specific items
        System.out.println("Chest at "+position+" being accessed");
        System.out.println("Chest contents: " + chestContents.get(position));

        //Inventory.Inventory.add();
    }
}
