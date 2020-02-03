package game.views;

import game.model.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 游戏开始的主界面
 *
 * @author lenovo
 * @date 2017/11/23
 */
public class StartMenu extends JFrame {

    Dimension frameSize = new Dimension(1000, 600);
    ImageIcon imageIcon = new ImageIcon(Resources.startGame);
    ImageIcon imageIconBack = new ImageIcon(Resources.backPNG);
    ImageIcon imageIconTomb = new ImageIcon(Resources.tombPNG);

    public StartMenu() {
        // 设置窗体属性
        setSize(frameSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置图标
        setIconImage(imageIconTomb.getImage());
        // 设置背景
        JLabel jLabel = new JLabel(imageIcon);
        jLabel.setBounds(0, 0, frameSize.width, frameSize.height);
        getContentPane().add(jLabel);

//        JLabel jLabelBack = (JLabel) this.getContentPane();
//        this.getLayeredPane().setLayout(null);
//        jLabelBack.setBounds(20,20,50,50);
//        this.getLayeredPane().add(jLabelBack,imageIconBack);
//        jLabelBack.setLayout(new BorderLayout());

        addComponents();
        //设置窗口居中
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
    }

    private void addComponents() {
        //新游戏
        JButton btn1 = new JButton();
        //排行榜
        JButton btn2 = new JButton();
        //退出游戏
        JButton btn3 = new JButton();
        btn1.setBounds(280, 120, 180, 50);
        btn2.setBounds(280, 250, 180, 50);
        btn3.setBounds(280, 375, 180, 50);
        btn1.setOpaque(false);
        btn2.setOpaque(false);
        btn3.setOpaque(false);
        //新游戏
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StartPattern();
                dispose();
                repaint();
            }
        });
        //排行榜
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Ranking();
                dispose();
                repaint();
            }
        });
        //退出游戏
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.getContentPane().add(btn1);
        this.getContentPane().add(btn2);
        this.getContentPane().add(btn3);
    }

    public static void main(String[] args) {
        new StartMenu();
    }

}
