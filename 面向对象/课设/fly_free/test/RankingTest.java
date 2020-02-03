import com.sun.istack.internal.NotNull;
import game.model.Player;
import game.model.RankingList;
import org.junit.Test;

import javax.swing.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lenovo on 2017/11/24.
 */
public class RankingTest {

    RankingList rankingList = new RankingList();

    @Test
    public void sortPlayer() throws Exception {
        File file = new File("rankingList.txt");
        List<Player> player = rankingList.exportCsv(file);
        List<Player> players = rankingList.sortPlayer(player);
        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).getName() + " : " + players.get(i).getScore());
        }
    }

    @Test
    public void exportCsv() throws Exception {
        File file = new File("rankingList.txt");
        List<Player> players = rankingList.exportCsv(file);
        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).getName() + " : " + players.get(i).getScore());
        }
    }

    @Test
    public void importCsv() throws Exception {

        List<Player> players = new LinkedList<>();
        players.add(new Player("liu", 20));
        players.add(new Player("li", 30));
        players.add(new Player("qw", 40));
        players.add(new Player("wer", 50));
        players.add(new Player("weqw", 60));
        players.add(new Player("qwe", 70));
        players.add(new Player("wert", 80));
        players.add(new Player("dfg", 90));

        File file = new File("rankingList.txt");
        for (int i = 0; i < players.size(); i++) {
            rankingList.importCsv(file, players.get(i));
        }
    }

    @Test
    public void test() throws Exception {
        InputStream in = new FileInputStream("hello.txt");
        Reader reader = new InputStreamReader(in);
        BufferedReader bufferedReader = new BufferedReader(reader);

        OutputStream out = new FileOutputStream("hello5.txt");
        Writer writer = new OutputStreamWriter(out);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);

        String str = null;
        int i = 0;
        while ((str = bufferedReader.readLine()) != null) {
            if (i != 0) {
                bufferedWriter.write("\n");
            }

            bufferedWriter.write(str);
            i++;
        }

        in.close();
        reader.close();
        bufferedReader.close();

        bufferedWriter.close();
        writer.close();
        out.close();
    }

    @Test
    public void test2() {

        @NotNull
        ImageIcon imageIcon = new ImageIcon(this.getClass().getClassLoader().getResource("/game/img/startGame.jpg"));
//        ImageIcon imageIcon1 = new ImageIcon(this.getClass().getClassLoader().getResource("D:\\workCode\\fly_free\\src\\game\\img\\startGame.jpg"));
        System.out.println(imageIcon.getImage());
    }

}