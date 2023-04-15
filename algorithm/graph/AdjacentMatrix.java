package algorithm.graph;

import util.Print;

/**
 * 邻接矩阵 -> 有向图
 *
 * @author zqw
 * @date 2023/4/4
 */
class AdjacentMatrix {
    private final static int[][] ADJ = {
        {0, 1, 0, 0},
        {0, 0, 1, 1},
        {1, 0, 0, 1},
        {1, 0, 0, 0},
    };

    static void print(int val) {
        Print.printf("%d", val);
    }
    public static void main(String[] args) {
        int pos = 0;
        int howTimes = ADJ.length;
        int cur = 0;
        for(;;) {
            int x = (int) (Math.random() * ADJ.length);
            while (ADJ[pos][x] != 1) {
                x = (int) (Math.random() * ADJ.length);
            }
            print(x);
            if(++cur == howTimes) {break;}
            pos = x;
        }
    }
}
