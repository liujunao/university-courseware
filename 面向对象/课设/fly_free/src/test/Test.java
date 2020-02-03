package test;

import javax.swing.*;

/**
 * Created by lenovo on 2017/12/5.
 */
public class Test {

    private JFrame frame;
    private JPanel panel;
    private JLabel label;
    public Test() {
        frame = new JFrame();
        panel = new JPanel();
        label = new JLabel("显示一段文字");
        frame.setBounds(10, 10, 200, 200);
        frame.add(panel);
        frame.add(label);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Test();
    }
}
