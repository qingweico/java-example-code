package thinking.annotation;

import thinking.atunit.Test;

/**
 * @author:qiming
 * @date: 2021/4/8
 */
public class Testable {
    public void execute() {
        System.out.println("Executing...");
    }
    @Test
    void testExecute() {
        execute();
    }
}
