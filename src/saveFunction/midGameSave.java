package saveFunction;

import main.GamePanel;
import mapGeneration.NextLineGeneration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class midGameSave {
    public static void save() throws IOException {
        java.util.List<String> savingValuesTiles = new ArrayList<>();
        java.util.List<String> savingValuesCrossed = new ArrayList<>();
        java.util.List<String> savingValuesInventory = new ArrayList<>();

        List<Integer> totalTileLocations = new ArrayList<>(NextLineGeneration.nextTileLocations);
        totalTileLocations.addAll(GamePanel.tileLocations);
        savingValuesTiles.add(totalTileLocations.toString());
        save.save((savingValuesTiles), "tiles.txt");

        savingValuesCrossed.add(String.valueOf(GamePanel.spacesCrossed));
        save.save((savingValuesCrossed), "spaces.txt");

        System.out.println(save.load("tiles.txt"));
        System.out.println(save.load("spaces.txt"));
    }
}
