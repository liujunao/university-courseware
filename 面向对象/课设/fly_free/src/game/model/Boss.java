package game.model;

import java.awt.image.BufferedImage;

/**
 * BOSS类
 * @author lenovo
 * @date 2017/11/20
 */
public class Boss extends FlyObject {

    boolean flag = true;

    public Boss(int x, int y, BufferedImage bufferedImage) {
        super(x, y, 220, 220, bufferedImage);
    }

    @Override
    public void move(long time, int index) {
        if (time % 2 == 0) {
            if (flag){
                y -= index;
                if (y <= 0){
                    flag = false;
                }
            }
            if (!flag) {
                y += index;
                if (y >= 400){
                    flag = true;
                }
            }
        }
    }

    public BossBullet fire(int width,int height,BufferedImage bufferedImage){
        int x = this.x;
        int y = this.y + height / 2;
        BossBullet bossBullet = new BossBullet(x,y,width,height,bufferedImage);

        return bossBullet;
    }
}
