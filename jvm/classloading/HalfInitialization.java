package jvm.classloading;

import util.Print;

/**
 * 对象的半初始化状态
 * 加载 --> 验证 --> 准备 --> 解析 --> 初始化 --> 使用 --> 卸载
 * @author zqw
 * @date 2023/11/11
 */
class HalfInitialization {
    public static void main(String[] args) {
        Print.grace("reality", Score.score.reality);
    }
}

class Score {

    static Score score = new Score(0.1);

    /**
     * 试卷分数 (static 修饰)
     * JVM在链接阶段(准备)为静态变量分配内存并赋值为默认值
     */
    static double paper = 90;
    /**
     * 实际分数
     */

    double reality;


    /*endTerm 实际成绩期末所占比例*/

    public Score(double endTerm) {
        // paper = 0
        // 解决 : 1 交换Score对象初始化和paper声明的位置 2 paper 变量添加 final 关键字修饰, 初始化过程会提前到编译阶段
        Print.grace("paper", paper);
        this.reality = paper * endTerm;
    }
}
