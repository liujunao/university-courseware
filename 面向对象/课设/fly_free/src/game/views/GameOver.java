package game.views;

import game.controller.GameController;
import game.model.Player;
import game.model.RankingList;
import game.model.Resources;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;

/**
 * 游戏结束界面
 *
 * @author lenovo
 * @date 2017/11/21
 */
public class GameOver extends JFrame {

    Dimension dimension = new Dimension(1000, 600);
    ImageIcon imageIcon = new ImageIcon(Resources.gameoverPNG);

    public static GameOver gameOver = null;

    public GameOver() {
        //设置窗体属性
        setSize(dimension);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(imageIcon.getImage());
        setLayout(null);
        addComponent();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void addComponent(){
        // 设置背景
        JLabel jLabel = new JLabel(imageIcon);
        jLabel.setBounds(0, 0, dimension.width, dimension.height);
        this.getContentPane().add(jLabel);

        JTextField jTextField = new JTextField(20);
        jTextField.setBounds(480, 295, 100, 25);
        jTextField.setBackground(Color.yellow);
        jTextField.setText("请输入姓名");
        jTextField.addFocusListener(new MyFocusListener("请输入姓名",jTextField));
        jTextField.setBorder(new EmptyBorder(0,0,0,0));
        this.getContentPane().add(jTextField);

        JButton jButton = new JButton("提交");
        jButton.setBounds(615, 290, 50, 30);
        jButton.setOpaque(false);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = jTextField.getText();
                int score  = GameController.score;
                RankingList rankingList = new RankingList();
                Player player = new Player(string,score);
                File file = new File("rankingList.txt");
                rankingList.importCsv(file,player);
                new StartMenu();
                GameOver.gameOver = null;
                dispose();
                repaint();
            }
        });
        this.getContentPane().add(jButton);
    }
    /**
     * 内部类
     * 用于实现类似于 placeholder 的功能
     */
    class MyFocusListener implements FocusListener{
        String info;
        JTextField jTextField = null;

        public MyFocusListener(String info, JTextField jTextField) {
            this.info = info;
            this.jTextField = jTextField;
        }

        @Override
        public void focusGained(FocusEvent e) {//获得焦点的时候，清空提示文字
            String tmp = jTextField.getText();
            if (tmp.equalsIgnoreCase(info)){
                jTextField.setText("");
            }
        }

        @Override
        public void focusLost(FocusEvent e) {//失去焦点的时候，判断如果为空，就显示提示文字
            String tmp = jTextField.getText();
            if (tmp.equalsIgnoreCase("")){
                jTextField.setText(info);
            }
        }
    }

}
