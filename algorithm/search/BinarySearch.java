package algorithm.search;

/**
 * @author:qiming
 * @date: 2021/10/28
 */
public class BinarySearch {
    // > target's min index
    private int upper(int[] nums, int target) {
        int l = 0, r = nums.length;
        while (l < r) {
            int mid = l + ((r - l) >> 1);
            if (nums[mid] <= target) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return l;
    }

    // ifAbsent return > target 's min index, if == target then return max index
    private int upperCeil(int[] nums, int target) {
        int p = upper(nums, target);
        if (p - 1 >= 0 && nums[p - 1] == target) {
            return p - 1;
        } else {
            return p;
        }
    }

    // >= target's min index
    private int lowerCeil(int[] nums, int target) {
        int l = 0, r = nums.length;
        while (l < r) {
            int mid = l + ((r - l) >> 1);
            if (nums[mid] < target) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return l;
    }

    // < target's max index
    private int lower(int[] nums, int target) {
        int l = -1, r = nums.length - 1;
        while (l < r) {
            int mid = l + ((r - l + 1) >> 1);
            if (nums[mid] < target) {
                l = mid;
            } else {
                r = mid - 1;
            }
        }
        return l;
    }

    // ifAbsent return < target's max index if == target then return min index
    private int lowerFloor(int[] nums, int target) {
        int p = lower(nums, target);
        if (p + 1 < nums.length && nums[p + 1] == target) {
            return p + 1;
        } else {
            return p;
        }
    }

    // <= target's max index
    private int upperFloor(int[] nums, int target) {
        int l = -1, r = nums.length - 1;
        while (l < r) {
            int mid = l + ((r - l + 1) >> 1);
            if (nums[mid] <= target) {
                l = mid;
            } else {
                r = mid - 1;
            }
        }
        return l;
    }
}
