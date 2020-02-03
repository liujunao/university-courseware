package game.model;

/**
 * 玩家信息
 * @author lenovo
 * @date 2017/11/24
 */
public class Player {

    private  String name;//玩家姓名
    private int score;//玩家分数

    public Player(String name,int score){
        this.name=name;
        this.score=score;
    }

    public Player() {
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
}
