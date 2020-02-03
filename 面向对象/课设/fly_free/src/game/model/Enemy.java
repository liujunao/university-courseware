package game.model;

import java.awt.image.BufferedImage;

/**
 * 敌人类
 * @author lenovo
 * @date 2017/11/20
 */
public class Enemy extends FlyObject {

    public Enemy(int x, int y, int width, int height, BufferedImage image) {
        super(x, y, width, height, image);
    }

    @Override
    public void move(long time,int index) {
        if (time % 2 == 0){
            x -= 3 * index;
        }
    }

    /**
     * 敌人发射子弹
     * @param width
     * @param height
     * @param image
     * @return
     */
    public EnemyBullet fire(int width,int height,BufferedImage image){
        int x = this.x;
        int y = this.y + this.height / 2;
        EnemyBullet enemyBullet = new EnemyBullet(x,y,width,height,image);

        return enemyBullet;
    }
}
