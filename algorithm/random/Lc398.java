package algorithm.random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * lc398:随机数索引
 *
 * @author zqw
 * @date 2022/11/15
 */
public class Lc398 {

    private final HashMap<Integer, ArrayList<Integer>> map;
    private final Random random;

    public Lc398(int[] nums) {
        map = new HashMap<>();
        random = new Random();
        for (int i = 0; i < nums.length; i++) {
            map.computeIfAbsent(nums[i], k -> new ArrayList<>());
            map.get(nums[i]).add(i);
        }
    }

    public int pick(int target) {
        int size = map.get(target).size();
        int rnd = random.nextInt(size);
        return map.get(target).get(rnd);
    }

    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 3, 3};
        Lc398 lc398 = new Lc398(array);
        System.out.println(lc398.pick(1));
        System.out.println(lc398.pick(2));
        System.out.println(lc398.pick(3));
    }
}
