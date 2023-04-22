package algorithm.graph;

/**
 * 拓扑排序 : 在一个有向无环图中找到一个拓扑序列的过程称为拓扑排序
 *
 * @author zqw
 * @date 2023/4/22
 */
class TopologicalOrder {
    public static void main(String[] args) {

        /*      D --> B <-- C
         *             |
         *             V
         *             A          */
        int[][] adj = {{0, 0, 0, 0}, {1, 0, 0, 0}, {0, 1, 0, 0}, {0, 1, 0, 0}};

        int[] visited = {0, 0, 0, 0};
        int[] degree = {0, 0, 0, 0};

        for (int[] e : adj) {
            for (int j = 0; j < e.length; j++) {
                if (e[j] == 1) {
                    ++degree[j];
                }
            }
        }
        for (; ; ) {
            int u = -1;
            for (int i = 0; i < adj.length; i++) {
                if (visited[i] == 0 && degree[i] == 0) {
                    u = i;
                    break;
                }
            }
            if (u == -1) {
                break;
            }
            visited[u] = -1;
            System.out.print((char) ('A' + u));
            for (int i = 0; i < adj.length; i++) {
                if (adj[u][i] == 1) {
                    --degree[i];
                }
            }
        }
    }
}
