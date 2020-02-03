package game.views;

import game.model.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 闯关模式的关卡选择
 *
 * @author lenovo
 * @date 2017/11/23
 */
public class SelectLevel extends JFrame {

    Dimension dimension = new Dimension(1000, 600);
    ImageIcon imageIcon = new ImageIcon(Resources.selectLevel);

    public SelectLevel() throws HeadlessException {
        setSize(dimension);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(imageIcon.getImage());
        setLayout(null);
        addComponent();
    }

    public void addComponent() {
        JLabel jLabel = new JLabel(imageIcon);
        jLabel.setBounds(0, 0, dimension.width, dimension.height);
        this.getContentPane().add(jLabel);
        setLocationRelativeTo(null);
        setVisible(true);
        //关卡1
        JButton jButton1 = new JButton();
        jButton1.setBounds(120, 70, 160, 160);
        jButton1.setOpaque(false);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartPattern.gameFrame = new GameFrame(1);
                dispose();
                repaint();
            }
        });
        //关卡2
        JButton jButton2 = new JButton();
        jButton2.setBounds(400, 270, 160, 160);
        jButton2.setOpaque(false);
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartPattern.gameFrame = new GameFrame(2);
                dispose();
                repaint();
            }
        });
        //关卡3
        JButton jButton3 = new JButton();
        jButton3.setBounds(720, 150, 160, 160);
        jButton3.setOpaque(false);
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartPattern.gameFrame = new GameFrame(3);
                dispose();
                repaint();
            }
        });

        this.getContentPane().add(jButton1);
        this.getContentPane().add(jButton2);
        this.getContentPane().add(jButton3);
    }

}
