package saveFunction;

import main.GamePanel;

import java.io.IOException;
import java.util.ArrayList;

public class midGameSave {
    public static void save() throws IOException {
        java.util.List<String> savingValues = new ArrayList<>();
        savingValues.add(Integer.toString(GamePanel.spacesCrossed));
        save.save(savingValues, "save.txt");


        //java.util.List<String> savingValues = new ArrayList<>();
        //savingValues.add("1");
        //save.save(haha, "save.txt");
        //System.out.println(save.load("save.txt"));

        System.out.println(save.load("save.txt"));
    }
}
