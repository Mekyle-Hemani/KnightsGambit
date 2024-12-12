package testing;

public class treeLocationGeneration {
    public static void main(String[] args) {
        int rows = 7;
        int cols = 11;

        char[][] grid = new char[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = 'O';
            }
        }

        int[] openPaths = {2, 5, 8};

        java.util.Random rand = new java.util.Random();

        for (int r = 1; r < rows - 1; r++) {
            for (int c = 0; c < cols; c++) {
                if (!contains(openPaths, c)) {
                    if (rand.nextInt(100) < 20) {
                        grid[r][c] = 'B';
                    }
                }
            }
        }

        for (int pathCol : openPaths) {
            for (int r = 0; r < rows; r++) {
                grid[r][pathCol] = 'O';
            }
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                System.out.print(grid[r][c]);
                if (c < cols - 1) System.out.print(",");
            }
            System.out.println();
        }
    }

    private static boolean contains(int[] arr, int val) {
        for (int i : arr) {
            if (i == val) return true;
        }
        return false;
    }
}
