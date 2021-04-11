package jcip;

/**
 * @author:qiming
 * @date: 2021/4/8
 */
public class UnsafeStates {

    // To cause the internal mutable state to escape, don't do this!
    private final String[] states = new String[]{
            "AK", "AL"
    };

    public String[] getStates() {return states;}
}
