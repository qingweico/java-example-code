package jcip;

import java.util.Arrays;

/**
 * @author zqw
 * @date 2021/4/8
 */
class UnsafeStates {

    /*To cause the internal mutable state to escape, don't do this!*/


    private final String[] states = new String[]{
            "AK", "AL"
    };

    public String[] getStates() {
        return states;
    }

    public static void main(String[] args) {
        UnsafeStates us = new UnsafeStates();
        String[] states = us.getStates();
        states[0] = "KL";
        // broken
        System.out.println(Arrays.toString(us.getStates()));
    }
}
