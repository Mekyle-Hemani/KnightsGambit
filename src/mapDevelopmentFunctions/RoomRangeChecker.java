package mapDevelopmentFunctions;

import main.GamePanel;

import java.util.List;

public class RoomRangeChecker {
    static GamePanel gp;

    public RoomRangeChecker(GamePanel gp) {
        RoomRangeChecker.gp = gp;
    }

    public static boolean isInRange(List<Integer> tileLocations, int gridWidth, int checkIndex, int range, int target) {
        int totalItems = tileLocations.size();
        int gridHeight = totalItems / gridWidth;

        int checkRow = checkIndex / gridWidth;
        int checkCol = checkIndex % gridWidth;

        //Ensure range is even and calculate half-range
        if (range % 2 == 1) {
            range -= 1;
        }
        range = range / 2;

        int startRow = checkRow - range;
        int endRow = checkRow + range;
        int startCol = checkCol - range;
        int endCol = checkCol + range;

        //Check if range goes off-screen
        if (startRow < 0 || endRow >= gridHeight || startCol < 0 || endCol >= gridWidth) {
            return true;
        }

        //Search for the target within the range
        for (int row = startRow; row <= endRow; row++) {
            for (int col = startCol; col <= endCol; col++) {
                int index = row * gridWidth + col;
                if (index < totalItems && tileLocations.get(index) % 10 == target) {
                    return true;
                }
            }
        }
        return false;
    }
}
