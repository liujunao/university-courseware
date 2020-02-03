package game.model;

import java.awt.image.BufferedImage;

/**
 * BOSS 子弹类
 *
 * @author lenovo
 * @date 2017/12/5
 */
public class BossBullet extends FlyObject {

    public BossBullet(int x, int y, int width, int height, BufferedImage image) {
        super(x, y, width, height, image);
    }

    @Override
    public void move(long time, int index) {
    }

    public void move_1(long time, int index, int cla) {
        if (time % 2 == 0) {
            x -= 10 * index;
            y -= (15 - 5 * cla) * index;
        }
    }
}
