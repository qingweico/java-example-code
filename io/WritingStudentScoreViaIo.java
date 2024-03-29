package io;

import object.entity.StudentScore;
import util.constants.Constants;

import java.util.TreeSet;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

/**
 * @author zqw
 * @date 2020/1/29
 */
class WritingStudentScoreViaIo {
    public static void main(String[] args) throws IOException {
        String filename = Constants.DEFAULT_FILE_PATH_MAME;
        TreeSet<StudentScore> set = new TreeSet<>((s1, s2) -> {
            int num = s2.getSum() - s1.getSum();
            return num == 0 ? s1.getName().compareTo(s2.getName()) : num;
        });
        System.out.println("------------开始录入学生------------");
        Scanner input = new Scanner(System.in);
        System.out.print("请输入您要录入的学生个数:");
        int number = input.nextInt();
        for (int i = 1; i <= number; i++) {
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入第" + i + "个学生姓名:");
            String name = sc.nextLine();
            System.out.println("请输入第" + i + "个学生的语文成绩:");
            int chinese = sc.nextInt();
            System.out.println("请输入第" + i + "个学生的数学成绩:");
            int math = sc.nextInt();
            System.out.println("请输入第" + i + "个学生的英语成绩:");
            int english = sc.nextInt();
            StudentScore s = new StudentScore();
            s.setName(name);
            s.setChinese(chinese);
            s.setMath(math);
            s.setEnglish(english);
            set.add(s);
        }
        System.out.println("--------------录入学生结束----------------");
        BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
        fw.write("姓名 语文成绩 数学成绩 英语成绩");
        fw.newLine();
        fw.flush();
        for (StudentScore s : set) {
            String ss = s.getName() + " " + s.getChinese() + " " + s.getMath() + " " + s.getEnglish();
            fw.write(ss);
            fw.newLine();
            fw.flush();
        }
        fw.close();
        System.out.println("数据成功写入");
    }
}
