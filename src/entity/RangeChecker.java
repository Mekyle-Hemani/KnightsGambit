package entity;

import main.GamePanel;

import java.util.ArrayList;
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
                        tileLocations.set(index, target);
                        found = true;
                    }
                }
            }
        }
        return found;
    }

    public static void printGrid(List<Integer> tileLocations, int gridWidth, int checkIndex) {
        int gridHeight = tileLocations.size() / gridWidth;

        for (int row = 0; row < gridHeight; row++) {
            for (int col = 0; col < gridWidth; col++) {
                int index = row * gridWidth + col;
                if (index == checkIndex) {
                    System.out.print(" x ");
                } else {
                    int tileValue = tileLocations.get(index);
                    if (tileValue % 10 == 5) {
                        System.out.print(" y ");
                    } else {
                        System.out.print(" " + tileValue + " ");
                    }
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        List<Integer> tileLocations = new ArrayList<>();

        for (int i = 0; i < 187; i++) {
            tileLocations.add(10);
        }

        tileLocations.set(100, 15);
        tileLocations.set(103, 25);
        tileLocations.set(104, 35);

        int gridWidth = 11;
        int checkIndex = 104;
        int range = 7;
        int target = 5;

        System.out.println(isInRange(tileLocations, gridWidth, checkIndex, range, target));

        printGrid(tileLocations, gridWidth, checkIndex);
    }
}