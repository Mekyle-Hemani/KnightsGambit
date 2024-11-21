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

        String targetStr = String.valueOf(target);

        // Loop over the grid and print the result for range checking
        for (int row = Math.max(0, checkRow - range); row < Math.min(gridHeight, checkRow + range + 1); row++) {
            for (int col = Math.max(0, checkCol - range); col < Math.min(gridWidth, checkCol + range + 1); col++) {
                int index = row * gridWidth + col;
                if (index < totalItems) {
                    String tileStr = String.valueOf(tileLocations.get(index));
                    if (tileStr.startsWith(targetStr)) {
                        tileLocations.set(index, target); // Mark it with the target
                    }
                }
            }
        }

        return false;
    }

    public static void printGrid(List<Integer> tileLocations, int gridWidth, int checkIndex) {
        int gridHeight = tileLocations.size() / gridWidth;

        for (int row = 0; row < gridHeight; row++) {
            for (int col = 0; col < gridWidth; col++) {
                int index = row * gridWidth + col;
                if (index == checkIndex) {
                    System.out.print(" x "); // Mark the checkIndex with 'x'
                } else {
                    String tileStr = String.valueOf(tileLocations.get(index));
                    if (tileStr.startsWith("5")) { // Mark target items with 'y'
                        System.out.print(" y ");
                    } else {
                        System.out.print(" " + tileStr + " ");
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

        tileLocations.set(100, 50);
        tileLocations.set(103, 50);

        int gridWidth = 11;
        int checkIndex = 104;
        int range = 7;
        int target = 5;

        System.out.println(checkIndex + "? " + isInRange(tileLocations, gridWidth, checkIndex, range, target));

        // Print the grid with modifications
        printGrid(tileLocations, gridWidth, checkIndex);
    }
}
