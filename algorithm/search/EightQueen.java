package algorithm.search;

/**
 * @author zqw
 * @date 2022/3/10
 */
class EightQueen {
    /**row number of each queen; for example queen[0] = 3 -> the first queen is place in the fourth row*/
    int[] queens = new int[8];
    int maxCol = 8;
    int count = 0;

     boolean solve(int col) {
        if (col == maxCol) {
            return true;
        }
        // Starting from the row
        for (int i = 0; i < maxCol; i++) {
            queens[col] = i;
            boolean flag = true;
            for (int j = 0; j < col; j++) {
                var rowDiff = Math.abs(queens[j] - i);
                var colDiff = col - j;
                if (queens[j] == i || colDiff == rowDiff) {
                    flag = false;
                    break;
                }
            }
            if (flag && solve(col + 1)) {
                count++;
                return true;
            }
        }
        return false;
    }

    void print() {
        for (int i = 0; i < maxCol; i++) {
            for (int j = 0; j < maxCol; j++) {
                if (queens[i] == j) {
                    System.out.print(" Q ");
                } else {
                    System.out.print(" . ");
                }
            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args) {
        var queens = new EightQueen();
        queens.solve(0);
        queens.print();
    }
}
