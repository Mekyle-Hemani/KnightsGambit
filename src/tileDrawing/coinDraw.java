package tileDrawing;

import main.GamePanel;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class coinDraw {
    GamePanel gp;

    public static List<String> coinLocations = new ArrayList<>();

    public coinDraw(GamePanel gamePanel) throws IOException {
        this.gp = gamePanel;
        initialize();
    }

    private void initialize() {

    }

    public void developCoin() {
        //Certain chance of making a coin
        //Add it to array of coins after
    }

    public boolean compileCoins(int index) throws IOException {
        //Check if the given index is found in the coinLocations array
        return true;
    }
}