package entity;

import java.util.ArrayList;
import java.util.List;

public class RangeChecker {
    public static boolean isInRange(List<Integer> tileLocations, int gridWidth, int checkIndex, int range, int target) {
        int totalItems = tileLocations.size();
        int gridHeight = totalItems / gridWidth;

        int checkRow = checkIndex / gridWidth;
        int checkCol = checkIndex % gridWidth;

        if (range % 2 == 1) {
            range -= 1;
        }
        range = range / 2;

        for (int row = Math.max(0, checkRow - range); row < Math.min(gridHeight, checkRow + range + 1); row++) {
            for (int col = Math.max(0, checkCol - range); col < Math.min(gridWidth, checkCol + range + 1); col++) {
                int index = row * gridWidth + col;
                if (index < totalItems) {
                    if (tileLocations.get(index) / 10 == target / 10) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        List<Integer> tileLocations = new ArrayList<>();

        for (int i = 0; i < 187; i++) {
            tileLocations.add(10);
        }

        tileLocations.set(100, 50);
        tileLocations.set(103, 50);

        int gridWidth = 11;
        int checkIndex = 104;

        System.out.println(checkIndex + "? " + isInRange(tileLocations, gridWidth, checkIndex, 7, 5));
    }
}