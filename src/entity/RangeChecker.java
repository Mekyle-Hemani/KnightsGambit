package entity;

import java.util.ArrayList;
import java.util.List;

public class RangeChecker {
    public static boolean isInRange(List<String> tileLocations, int gridWidth, int checkIndex, int range) {
        int totalItems = tileLocations.size();
        int gridHeight = totalItems / gridWidth;

        int checkRow = checkIndex / gridWidth;
        int checkCol = checkIndex % gridWidth;

        for (int row = Math.max(0, checkRow - range); row < Math.min(gridHeight, checkRow + range + 1); row++) {
            for (int col = Math.max(0, checkCol - range); col < Math.min(gridWidth, checkCol + range + 1); col++) {
                int index = row * gridWidth + col;
                if (index < totalItems && tileLocations.get(index).startsWith("h")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void printGrid(List<String> tileLocations, int gridWidth, int checkIndex) {
        for (int i = 0; i < tileLocations.size(); i++) {
            if (i == checkIndex) {
                System.out.print("x ");
            } else {
                System.out.print(tileLocations.get(i) + " ");
            }

            if ((i + 1) % gridWidth == 0) {
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        List<String> tileLocations = new ArrayList<>();

        for (int i = 0; i < 187; i++) {
            tileLocations.add("a");
        }
        tileLocations.set(100, "h");
        tileLocations.set(103, "h");

        int gridWidth = 11;
        int checkIndex = 107;

        System.out.println("Grid Representation (with 'x' at checkIndex):");
        printGrid(tileLocations, gridWidth, checkIndex);

        boolean result = isInRange(tileLocations, gridWidth, checkIndex, 3);
        System.out.println("\nIs 'h' in range of index " + checkIndex + "? " + result);
    }
}
