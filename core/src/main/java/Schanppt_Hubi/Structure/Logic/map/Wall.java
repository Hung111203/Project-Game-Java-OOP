package Schanppt_Hubi.Structure.Logic.map;

public class Wall extends House {
    private int currentState;

    public int getWallState() {
        return currentState;
    }

    @Override
    public int[][] findNeighbors(House[][] house, int rowIndex, int colIndex) {
        int[][] result = new int[2][2];
        int[][] step = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        int curRow = rowIndex;
        int curCol = colIndex;
        int currentIndex = 0;
        for (int i = 0; i < 4; i++) {
            int nextRow = step[i][0] + curRow;
            int nextCol = step[i][1] + curCol;
            if (nextRow >= 1 && nextRow <= 7 && nextCol >= 1 && nextCol <= 7) {
                if (house[nextRow][nextCol] instanceof Room) {
                    result[currentIndex][0] = nextRow;
                    result[currentIndex][1] = nextCol;
                    currentIndex++;
                }
            }
        }
        return result;
    }
}
