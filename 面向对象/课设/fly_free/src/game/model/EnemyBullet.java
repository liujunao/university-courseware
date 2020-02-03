package game.model;

import java.awt.image.BufferedImage;

/**
 * 敌人子弹类
 * @author lenovo
 * @date 2017/11/20
 */
public class EnemyBullet extends FlyObject {

    public EnemyBullet(int x, int y, int width, int height, BufferedImage image) {
        super(x, y, width, height, image);
    }

    @Override
    public void move(long time,int index) {
        if (time % 2 == 0){
            x -= 15;
        }
    }
}
