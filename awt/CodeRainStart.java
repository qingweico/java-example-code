package awt;

import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * @author zqw
 * @date 2022/7/9
 */
public class CodeRainStart {
    public static void main(String[] args) throws IOException {
        List<String> lines = IOUtils.readLines(new FileReader("awt/codeRain.txt"));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth() * 0.85);
        int height = (int) (screenSize.getHeight() * 0.85);
        System.out.printf("屏幕大小为: width:%s height:%s%n", width, height);
        JFrame frame = new JFrame("代码雨");
        // 自定义的代码雨面板对象
        CodeRain panel = new CodeRain(lines);
        // 将面板添加到JFrame中
        frame.add(panel);
        // 设置窗口大小
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 窗体居中显示
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        panel.start();
        panel.requestFocus();
    }
}
