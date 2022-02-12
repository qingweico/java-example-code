package algorithm.base;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zqw
 * @date 2021/11/9
 */
class Permute {
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        boolean[] visited = new boolean[nums.length];
        backtrack(res, nums, new ArrayList<>(), visited);
        return res;
    }

    private static void backtrack(List<List<Integer>> res, int[] nums, ArrayList<Integer> tmp, boolean[] visited) {
        if (tmp.size() == nums.length) {
            res.add(new ArrayList<>(tmp));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (visited[i]) {
                continue;
            }
            visited[i] = true;
            tmp.add(nums[i]);
            backtrack(res, nums, tmp, visited);
            visited[i] = false;
            tmp.remove(tmp.size() - 1);
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> list = permute(new int[]{1, 2, 3, 4});
        for (List<Integer> lists : list) {
            System.out.println(lists);
        }
    }
}
