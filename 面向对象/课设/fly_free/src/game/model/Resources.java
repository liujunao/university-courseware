package game.model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author lenovo
 * @date 2017/11/20
 */
public class Resources {

    //加血
    public static BufferedImage addLifePNG;
    //背景
    public static BufferedImage background1JPG;
    public static BufferedImage background2JPG;
    public static BufferedImage background3JPG;
    public static BufferedImage backgroundEverJPG;
    //boss
    public static BufferedImage boss1PNG;
    public static BufferedImage boss2PNG;
    public static BufferedImage boss3PNG;
    //敌人子弹
    public static BufferedImage eBullet1PNG;
    public static BufferedImage eBullet2PNG;
    public static BufferedImage eBullet3PNG;
    public static BufferedImage eBullet4PNG;
    public static BufferedImage eBullet5PNG;
    public static BufferedImage eBullet6PNG;
    public static BufferedImage eBullet7PNG;
    //敌人
    public static BufferedImage enemy11PNG;
    public static BufferedImage enemy12PNG;
    public static BufferedImage enemy13PNG;
    public static BufferedImage enemy21PNG;
    public static BufferedImage enemy22PNG;
    public static BufferedImage enemy23PNG;
    public static BufferedImage enemy31PNG;
    public static BufferedImage enemy32PNG;
    public static BufferedImage enemy33PNG;
   //结束图片
   public static BufferedImage gameoverPNG;
    //英雄子弹
    public static BufferedImage hBullet2PNG;
    public static BufferedImage hBullet1PNG;
    //英雄
    public static BufferedImage heroPNG;
    //血条
    public static BufferedImage livesPNG;
    //选关
    public static BufferedImage selectLevel;
    //选择模式
    public static BufferedImage selectPattern;
    //开始界面
    public static BufferedImage startGame;
    //图标
    public static BufferedImage tombPNG;
    //排行榜
    public static BufferedImage rankingListPNG;

    //返回图片
    public static BufferedImage backPNG;
    //停止图片
    public static BufferedImage stopPNG;

    static {
        try {
            //加血
            addLifePNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/addlife.png"));
            //背景
            background1JPG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/background1.jpg"));
            background2JPG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/background2.jpg"));
            background3JPG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/background3.jpg"));
            backgroundEverJPG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/backgroundEver.jpg"));
            //boss
            boss1PNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/boss1.png"));
            boss2PNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/boss2.png"));
            boss3PNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/boss3.png"));
            //敌人子弹
            eBullet1PNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/ebullet1.png"));
            eBullet2PNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/ebullet2.png"));
            eBullet3PNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/ebullet3.png"));
            eBullet4PNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/ebullet4.png"));
            eBullet5PNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/ebullet5.png"));
            eBullet6PNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/ebullet6.png"));
            eBullet7PNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/ebullet7.png"));
            //敌人
            enemy11PNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/enemy11.png"));
            enemy12PNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/enemy12.png"));
            enemy13PNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/enemy13.png"));
            enemy21PNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/enemy31.png"));
            enemy22PNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/enemy22.png"));
            enemy23PNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/enemy23.png"));
            enemy31PNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/enemy31.png"));
            enemy32PNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/enemy32.png"));
            enemy33PNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/enemy33.png"));
            //结束图片
            gameoverPNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/gameover.jpg"));
            //英雄子弹
            hBullet2PNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/hbullet2.png"));
            hBullet1PNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/hbullet1.png"));
            //英雄
            heroPNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/hero.png"));
            //血条
            livesPNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/lives.png"));
            //选关
            selectLevel = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/selectLevel.jpg"));
            //选择模式
            selectPattern = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/selectPattern.jpg"));
            //开始界面
            startGame = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/startGame.jpg"));
            //图标
            tombPNG = ImageIO.read(Resources.class
                    .getResourceAsStream("/game/img/tomb.png"));
            //排行榜
            rankingListPNG = ImageIO.read(Resources.class.getResourceAsStream("/game/img/rankingList.jpg"));

            //返回图片
            backPNG = ImageIO.read(Resources.class.getResourceAsStream("/game/img/back.jpg"));
            //暂停图片
            stopPNG = ImageIO.read(Resources.class.getResourceAsStream("/game/img/stop.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
