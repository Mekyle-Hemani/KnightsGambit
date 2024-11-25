package entity;

import main.GamePanel;

import java.util.List;

public class RangeChecker {
    static GamePanel gp;

    public RangeChecker(GamePanel gp) {
        RangeChecker.gp = gp;
    }

    public static boolean isInRange(List<Integer> tileLocations, int gridWidth, int checkIndex, int range, int target) {
        int totalItems = tileLocations.size();
        int gridHeight = totalItems / gridWidth;

        int checkRow = checkIndex / gridWidth;
        int checkCol = checkIndex % gridWidth;

        if (range % 2 == 1) {
            range -= 1;
        }
        range = range / 2;

        boolean found = false;

        for (int row = Math.max(0, checkRow - range); row < Math.min(gridHeight, checkRow + range + 1); row++) {
            for (int col = Math.max(0, checkCol - range); col < Math.min(gridWidth, checkCol + range + 1); col++) {
                int index = row * gridWidth + col;
                if (index < totalItems) {
                    int tileValue = tileLocations.get(index);
                    if (tileValue % 10 == target) {
                        found = true;
                    }
                }
            }
        }
        return found;
    }
}