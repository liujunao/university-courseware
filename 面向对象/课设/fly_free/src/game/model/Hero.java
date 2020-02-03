package game.model;

/**
 * 玩家类
 * @author lenovo
 * @date 2017/11/20
 */
public class Hero extends FlyObject {

    public Hero(int x, int y) {
        super(x, y, 145, 145, Resources.heroPNG);
    }

    @Override
    public void move(long time,int index) {}

    public HeroBullet1 fire(){
        HeroBullet1 heroBullet1 = new HeroBullet1(this.x + this.width,this.y + this.height / 2);
        return heroBullet1;
    }

    public HeroBullet2 launch(){
        HeroBullet2 heroBullet2 = new HeroBullet2(this.x + this.width,this.y + this.height / 2);
        return heroBullet2;
    }

    /**
     * 上移
     * @param distance
     */
    public void moveUp(int distance){
        y -= distance;
        if (y < 0){
            y += distance;
        }
    }

    /**
     * 下移
     * @param distance
     */
    public void moveDown(int distance){
        y += distance;
        if (y > 600 - 145){
            y -= distance;
        }
    }

    /**
     * 左移
     * @param distance
     */
    public void moveLeft(int distance){
        x -= distance;
        if (x < 0){
            x += distance;
        }
    }

    /**
     * 右移
     * @param distance
     */
    public void moveRight(int distance){
        x += distance;
        if (x > 1000 - 145){
            x -= distance;
        }
    }

}
