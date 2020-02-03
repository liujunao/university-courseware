package game.model;

import java.awt.image.BufferedImage;

/**
 * 背景类
 * @author lenovo
 * @date 2017/11/20
 */
public class Background extends FlyObject {

    public Background(int x, int y, BufferedImage image) {
        super(x, y, 1000, 600, image);
    }

    @Override
    public void move(long time,int index) {

    }
}
