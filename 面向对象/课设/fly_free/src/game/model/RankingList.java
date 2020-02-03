package game.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 排行榜
 *
 * @author lenovo
 * @date 2017/11/24
 */
public class RankingList {

    //根据分数进行排序
    public List<Player> sortPlayer(List<Player> players){

        Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return -(o1.getScore() - o2.getScore());
            }
        });
        return players;
    }

    //读取文件内容
    public List<Player> exportCsv(File file) {
        List<Player> dateList = new ArrayList<Player>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(" : ");
                Player player = new Player();
                player.setName(strings[0]);
                player.setScore(Integer.parseInt(strings[1]));
                dateList.add(player);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dateList;
    }

    //写数据进文件
    public void importCsv(File file, Player player) {
        OutputStream outputStream = null;
        Writer writer = null;
        BufferedWriter bufferedWriter = null;
        try {
            outputStream = new FileOutputStream(file,true);
            writer = new OutputStreamWriter(outputStream);
            bufferedWriter = new BufferedWriter(writer);
                bufferedWriter.write(player.getName() + " : " + player.getScore() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
                writer.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
