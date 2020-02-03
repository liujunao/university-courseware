package game.views;

import game.model.Player;
import game.model.RankingList;
import game.model.Resources;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *  显示排行榜类
 *
 * @author lenovo
 * @date 2017/12/4
 */
public class Ranking extends JFrame{

    Dimension frameSize = new Dimension(1000, 600);
    ImageIcon imageIcon = new ImageIcon(Resources.rankingListPNG);

    //获取排行榜信息，并排序
    public List<Player> getInfo(){
        RankingList rankingList = new RankingList();
        List<Player> players = new ArrayList<>();
        File file = new File("rankingList.txt");
        List<Player> player = rankingList.exportCsv(file);
        players = rankingList.sortPlayer(player);

        return players;
    }

    private void addComponent(){
        //打印出相应的信息
        JLabel jLabelSort = null;
        JLabel jLabelName = null;
        JLabel jLabelScore = null;
        List<Player> players = getInfo();
        int playerSize;
        if (players.size() <= 8){
            playerSize = players.size();
        }else {
            playerSize = 8;
        }
        for (int i = 0; i < playerSize; i++) {
            jLabelSort = new JLabel(String.valueOf(i + 1));
            jLabelName = new JLabel(players.get(i).getName());
            jLabelScore = new JLabel(String.valueOf(players.get(i).getScore()));
            jLabelSort.setBounds(290,180 + 30 * i,30,25);
            jLabelName.setBounds(390,180 + 30 * i,100,25);
            jLabelScore.setBounds(520,180 + 30 * i,80,25);
            jLabelScore.setFont(new Font("",Font.BOLD,20));
            jLabelName.setFont(new Font("",Font.BOLD,20));
            jLabelSort.setFont(new Font("",Font.BOLD,20));
            getContentPane().add(jLabelName);
            getContentPane().add(jLabelScore);
            getContentPane().add(jLabelSort);
        }
    }

    public Ranking() {
        //设置背景
        JLabel jLabel = new JLabel(imageIcon);
        jLabel.setBounds(0,0,frameSize.width,frameSize.height);
        imageIcon.getIconHeight();

        JPanel jPanel = (JPanel) this.getContentPane();
        jPanel.setOpaque(false);
        this.getLayeredPane().setLayout(null);
        this.getLayeredPane().add(jLabel,new Integer(Integer.MIN_VALUE));
        jPanel.setLayout(new BorderLayout());

        //设置窗体属性
        setSize(frameSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(imageIcon.getImage());
        addComponent();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
