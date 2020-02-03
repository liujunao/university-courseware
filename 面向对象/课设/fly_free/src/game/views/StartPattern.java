package game.views;

import game.model.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 游戏模式的选择：无尽模式和闯关模式
 * @author lenovo
 * @date 2017/11/23
 */
public class StartPattern extends JFrame {

    Dimension dimension = new Dimension(1000, 600);
    ImageIcon imageIcon = new ImageIcon(Resources.selectPattern);

    public static GameFrame gameFrame = null;

    public StartPattern() throws HeadlessException {
        setSize(dimension);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(imageIcon.getImage());
        setLayout(null);
        JLabel jLabel = new JLabel(imageIcon);
        jLabel.setBounds(0,0,dimension.width,dimension.height);
        this.getContentPane().add(jLabel);
        setLocationRelativeTo(null);
        setVisible(true);

        addComponent();
    }

    public void addComponent(){
        //闯关模式
        JButton jButton1 = new JButton();
        jButton1.setBounds(200,160,210,210);
        jButton1.setOpaque(false);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SelectLevel();
                dispose();
                repaint();
            }
        });
        //无尽模式
        JButton jButton2 = new JButton();
        jButton2.setBounds(600,160,210,210);
        jButton2.setOpaque(false);
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartPattern.gameFrame = new GameFrame(4);
                dispose();
                repaint();
            }
        });

        this.getContentPane().add(jButton1);
        this.getContentPane().add(jButton2);
    }

}
