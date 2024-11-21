package entity;

import java.util.ArrayList;
import java.util.List;

public class RangeChecker {
    public static boolean isInRange(List<String> tileLocations, int gridWidth, int checkIndex) {
        int totalItems = tileLocations.size();
        int gridHeight = totalItems / gridWidth;

        int checkRow = checkIndex / gridWidth;
        int checkCol = checkIndex % gridWidth;

        for (int row = Math.max(0, checkRow - 3); row < Math.min(gridHeight, checkRow + 4); row++) {
            for (int col = Math.max(0, checkCol - 3); col < Math.min(gridWidth, checkCol + 4); col++) {
                int index = row * gridWidth + col;
                if (index < totalItems && tileLocations.get(index).startsWith("h")) {
                    return true;
                }
            }
        }

        return false;
    }

    // Helper function to print the grid, with "x" at the checkIndex
    public static void printGrid(List<String> tileLocations, int gridWidth, int checkIndex) {
        for (int i = 0; i < tileLocations.size(); i++) {
            // Replace the value at checkIndex with "x"
            if (i == checkIndex) {
                System.out.print("x ");
            } else {
                System.out.print(tileLocations.get(i) + " ");
            }

            if ((i + 1) % gridWidth == 0) {
                System.out.println(); // New line after each row
            }
        }
    }

    public static void main(String[] args) {
        List<String> tileLocations = new ArrayList<>();

        // Example data
        for (int i = 0; i < 187; i++) {
            tileLocations.add("a");
        }
        tileLocations.set(100, "h"); // Example: Place "h" at index 100
        tileLocations.set(103, "h"); // Another "h" for testing

        int gridWidth = 11; // Width of the grid (e.g., 11 columns)
        int checkIndex = 107; // Example index to check

        // Print the grid for visualization with "x" at the checkIndex
        System.out.println("Grid Representation (with 'x' at checkIndex):");
        printGrid(tileLocations, gridWidth, checkIndex);

        // Call the function and print the result
        boolean result = isInRange(tileLocations, gridWidth, checkIndex);
        System.out.println("\nIs 'h' in range of index " + checkIndex + "? " + result);
    }
}
