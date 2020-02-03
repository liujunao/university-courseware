package game.model;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 所有 model 的父类，定义含参构造器，自动移动函数和 draw 函数
 * @author lenovo
 * @date 2017/11/20
 */
public abstract class FlyObject {

    public int x;
    public int y;
    public int width;
    public int height;
    public BufferedImage image;

    /**
     *  含参构造器，包含坐标和长宽，以及image
     * @param x
     * @param y
     * @param width
     * @param height
     * @param image
     */
    public FlyObject(int x, int y, int width, int height, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public FlyObject(int x, int y, int width, int height, BufferedImage image,int stage) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    /**
     * 无参构造器
     */
    public FlyObject() {}

    /**
     *  自动移动的函数
     * @param time 时间间隔
     * @param index 根据关卡的变化进而改变移动速度
     */
    public abstract void move(long time,int index);

    /**
     * 将指定图片画出
     * @param graphics
     */
    public void draw(Graphics graphics){
        graphics.drawImage(image,x,y,width,height,null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
