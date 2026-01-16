package design.creational.prototype;

import cn.qingweico.supplier.RandomDataGenerator;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 原型模式通过复制现有对象(原型)来创建新对象, 而不是通过 new 关键字实例化
 * 这种方式避免了重复的初始化过程, 提高了对象创建的效率
 *
 * @author zqw
 * @date 2026/1/15
 */
class Start {
    private static final QuestionBank QUESTION_BANK;

    public static void main(String[] args) throws CloneNotSupportedException {
        System.out.println(createPaper(RandomDataGenerator.name(true), RandomStringUtils.randomNumeric(12)));
        System.out.println(createPaper(RandomDataGenerator.name(true), RandomStringUtils.randomNumeric(12)));
        System.out.println(createPaper(RandomDataGenerator.name(true), RandomStringUtils.randomNumeric(12)));
    }

    static {
        /*初始化题库*/
        QUESTION_BANK = new QuestionBank();
        Map<String, String> map01 = new HashMap<>();
        map01.put("A", "秦朝");
        map01.put("B", "汉朝");
        map01.put("C", "唐朝");
        map01.put("D", "明朝");
        map01.put("E", "清朝");

        Map<String, String> map02 = new HashMap<>();
        map02.put("A", "长江");
        map02.put("B", "黄河");
        map02.put("C", "珠江");
        map02.put("D", "黑龙江");

        Map<String, String> map03 = new HashMap<>();
        map03.put("A", "埃及");
        map03.put("B", "印度");
        map03.put("C", "中国");
        map03.put("D", "希腊");

        Map<String, String> map04 = new HashMap<>();
        map04.put("A", "亚洲");
        map04.put("B", "欧洲");
        map04.put("C", "非洲");
        map04.put("D", "南极洲");

        Map<String, String> map05 = new HashMap<>();
        map05.put("A", "喜马拉雅山脉");
        map05.put("B", "阿尔卑斯山脉");
        map05.put("C", "安第斯山脉");
        map05.put("D", "落基山脉");
        QUESTION_BANK.append(new ChoiceQuestion("中国历史上第一个统一的封建王朝是", map01, "A"))
                .append(new ChoiceQuestion("中国最长的河流是", map02, "A"))
                .append(new ChoiceQuestion("世界四大文明古国不包括", map03, "D"))
                .append(new ChoiceQuestion("面积最大的大陆是", map04, "A"))
                .append(new ChoiceQuestion("世界上海拔最高的山脉是", map05, "A"))
                .append(new AnswerQuestion("郑和下西洋发生在哪个朝代", "明朝"))
                .append(new AnswerQuestion("我国陆地面积居世界第几位", "第三位"))
                .append(new AnswerQuestion("赤道穿过哪三个大洲", "非洲、亚洲和南美洲"))
                .append(new AnswerQuestion("我国的首都是哪里", "北京"));
    }

    public static String createPaper(String candidate, String number) throws CloneNotSupportedException {
        QuestionBank questionBankClone = (QuestionBank) QUESTION_BANK.clone();
        questionBankClone.setCandidate(candidate);
        questionBankClone.setNumber(number);
        return questionBankClone.toString();
    }

}
