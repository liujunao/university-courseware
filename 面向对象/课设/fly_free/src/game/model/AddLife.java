package game.model;

/**
 * 添加血量的类
 * @author lenovo
 * @date 2017/11/20
 */
public class AddLife extends FlyObject{

    public AddLife(int x, int y) {
        super(x, y, 50, 50, Resources.addLifePNG);
    }

    @Override
    public void move(long time,int index) {
    }

    public void move(long time) {
        if (time % 2 == 0){
            x -= 8;
            int ax = (int) ((4 -Math.random() * 8) + x);
            if (ax > 10 && ax < 400 - 50){
                x = ax;
            }
        }
    }

}
