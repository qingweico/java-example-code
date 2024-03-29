package coretech2.secure.classloader;

import javax.swing.*;
import java.awt.*;

/**
 * @author zqw
 * @date 2022/7/14
 */
class CustomizedClassLoader {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var frame = new ClassLoaderFrame();
            frame.setTitle("CustomizedClassLoader");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
class ClassLoaderFrame extends JFrame {
    private final JTextField keyField = new JTextField("3", 4);
    private final JTextField nameField = new JTextField("Calculator", 30);
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    public ClassLoaderFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLayout(new GridBagLayout());
        add(new JLabel("Class"), new Gbc(0, 0).setAnchor(Gbc.WEST));
        // TODO
    }

}
