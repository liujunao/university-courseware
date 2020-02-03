package game.model;

/**
 * 血条类
 * @author lenovo
 * @date 2017/11/20
 */
public class Lives extends FlyObject {

    public Lives(int x, int y) {
        super(x, y, 30, 30, Resources.livesPNG);
    }

    @Override
    public void move(long time,int index) {

    }
}
