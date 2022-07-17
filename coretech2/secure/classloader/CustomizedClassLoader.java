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
    private JTextField keyField = new JTextField("3", 4);
    private JTextField nameField = new JTextField("Calculator", 30);
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    public ClassLoaderFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLayout(new GridBagLayout());
        add(new JLabel("Class"), new GBC(0, 0).setAnchor(GBC.WEST));
        // TODO
    }

}
