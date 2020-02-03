package game.model;

/**
 * 玩家子弹 2
 * @author lenovo
 * @date 2017/11/20
 */
public class HeroBullet2 extends FlyObject{

    public HeroBullet2(int x, int y) {
        super(x, y, 50, 50, Resources.hBullet2PNG);
    }

    @Override
    public void move(long time,int index) {
        if (time % 2 == 0){
            x += 10;
        }
    }
}
