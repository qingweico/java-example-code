package awt;

import cn.qingweico.concurrent.pool.ThreadObjectPool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author zqw
 * @date 2022/7/9
 */
public class CodeRain extends JPanel {


    /**
     * 字体大小
     */
    private static final int FONT_SIZE = 16;
    /**
     * 字间距
     */
    private static final int SPACE = 40;
    /**
     * 休眠时间(ms)
     */
    private static final int SLEEP_TIME = 130;

    private static final ExecutorService POOL = ThreadObjectPool.newFixedThreadPool(1);

    /**
     * 字符集合(可从文件、外部配置读取) 二位数组
     */
    private String[][] charsetArray;
    /**
     * 列的起始位置
     */
    private int[] pos;
    /**
     * 列的渐变颜色
     */
    private final Color[] colors = new Color[10];

    /**
     * 数据源: 按行读取
     */
    private final List<String> sourceLines;

    public CodeRain(List<String> sourceLines) {
        this.sourceLines = sourceLines;
    }

    /**
     * 按行随机输出
     */
    private void randomFromSourceLines(String[] charset) {
        int lineCount = this.sourceLines.size();

        int length;
        int destPos = 0;
        while (true) {
            int index = ThreadLocalRandom.current().nextInt(lineCount);
            // 从所有行中随机取一行
            String line = sourceLines.get(index);
            //  将这一行打散成一个个的字
            String[] lineArray = line.split("");

            boolean breakFlag;
            if (lineArray.length + destPos >= charset.length) {
                length = charset.length - destPos;
                breakFlag = true;
            } else {
                length = lineArray.length;
                breakFlag = false;
            }

            System.arraycopy(lineArray, 0, charset, destPos, length);
            destPos += length;
            if (breakFlag) {
                break;
            }
        }
    }

    public void init() {
        // 屏幕宽度
        int width = getWidth();
        // 屏幕高度
        int height = getHeight();

        charsetArray = new String[width / SPACE][height / SPACE];
        for (String[] strings : charsetArray) {
            randomFromSourceLines(strings);
        }

        // 随机化列起始位置
        pos = new int[charsetArray.length];
        for (int i = 0; i < pos.length; i++) {
            pos[i] = ThreadLocalRandom.current().nextInt(1000);
        }

        // 生成渐变色: 生成从黑色到绿色的渐变颜色
        for (int i = 0; i < colors.length - 1; i++) {
            colors[i] = new Color(0, 180 / colors.length * (i + 1), 0);
        }

        // 背景颜色
        setBackground(Color.BLACK);
    }

    public void start() {
        init();
        POOL.execute(() -> {
            while (true) {
                try {
                    // 重绘
                    repaint();
                    // 停一会,继续重回,达到一闪一闪的效果
                    TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        POOL.shutdown();

        // 按键盘的任意建退出
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.exit(0);
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        });
    }


    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setFont(new Font(null, Font.BOLD, FONT_SIZE));

        for (int i = 0; i < charsetArray.length; i++) {
            for (int j = 0; j < colors.length; j++) {
                int index = (pos[i] + j) % charsetArray[i].length;
                g2.setColor(colors[j]);
                g2.drawString(charsetArray[i][index], i * SPACE, (index + 1) * SPACE);

            }
            pos[i] = (pos[i] + 1) % charsetArray[i].length;
        }
    }
}
