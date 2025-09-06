package misc;

import cn.qingweico.supplier.RandomDataGenerator;
import jodd.chalk.Chalk;
import jodd.chalk.Chalk256;

/**
 * @author zqw
 * @date 2025/9/6
 */
class ChalkColor {
    public static void main(String[] args) {
        Chalk<Chalk256> chalk = new Chalk256();
        chalk.yellow();
        chalk.underline();
        chalk.bold();
        chalk.print(RandomDataGenerator.address());
    }
}
