package game.model;

/**
 * 玩家子弹 1
 * @author lenovo
 * @date 2017/11/20
 */
public class HeroBullet1 extends FlyObject {
    public HeroBullet1(int x, int y) {
        super(x, y, 20, 20, Resources.hBullet1PNG);
    }

    /**
     * 子弹 1 移动速度
     * @param time 时间间隔
     */
    @Override
    public void move(long time,int index) {
        if (time % 2 == 0){
            x += 15;
        }
    }
}
