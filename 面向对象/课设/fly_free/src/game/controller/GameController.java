package game.controller;

import game.model.*;
import game.views.GameOver;
import game.views.StartPattern;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 游戏的 controller 类
 *
 * @author lenovo
 * @date 2017/11/20
 */
public class GameController extends JPanel implements KeyListener, Serializable {

    //血量显示
    private List<Lives> lives = new ArrayList<Lives>();
    //血量 +1
    private List<AddLife> addLife = new ArrayList<AddLife>();
    //敌人
    private List<Enemy> enemy1 = new ArrayList<Enemy>();
    private List<Enemy> enemy2 = new ArrayList<Enemy>();
    private List<Enemy> enemy3 = new ArrayList<Enemy>();

    private Boss boss;
    public static int bossBlood1 = 2000;
    public static int bossBlood2 = 2000;
    public static int bossBlood3 = 2000;

    private List<BossBullet> bossBullets = new ArrayList<>();

    private List<EnemyBullet> enemyBullet1 = new ArrayList<EnemyBullet>();
    private List<EnemyBullet> enemyBullet2 = new ArrayList<EnemyBullet>();
    private List<EnemyBullet> enemyBullet3 = new ArrayList<EnemyBullet>();

    private Hero hero;
    private List<HeroBullet1> heroBullet1 = new ArrayList<HeroBullet1>();
    private List<HeroBullet2> heroBullet2 = new ArrayList<HeroBullet2>();

    private Background background;

    //英雄生命值
    public static int life = 3;
    //金币
    public static int money = 0;
    //总的分数
    public static int score = 0;
    //每个当前场景的分数
    public static int mScore = 0;
    //关卡
    public int stage;

    boolean flag = true;

    //判断键盘的状态参数
    private boolean vkUp = false;
    private boolean vkDown = false;
    private boolean vkLeft = false;
    private boolean vkRight = false;
    private boolean vkFire = false;
    private boolean vkLaunch = false;

    // 3 个键盘响应函数
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                vkLeft = true;
                break;
            case KeyEvent.VK_D:
                vkRight = true;
                break;
            case KeyEvent.VK_W:
                vkUp = true;
                break;
            case KeyEvent.VK_S:
                vkDown = true;
                break;
            case KeyEvent.VK_J:
                vkFire = true;
                break;
            case KeyEvent.VK_K:
                if (money > 50) {
                    vkLaunch = true;
                    money -= 50;
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                vkLeft = false;
                break;
            case KeyEvent.VK_D:
                vkRight = false;
                break;
            case KeyEvent.VK_W:
                vkUp = false;
                break;
            case KeyEvent.VK_S:
                vkDown = false;
                break;
            case KeyEvent.VK_J:
                vkFire = false;
                break;
            case KeyEvent.VK_K:
                vkLaunch = false;
                break;
        }
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        background.draw(graphics);
        Font font = new Font("Microsoft Yahei", Font.BOLD, 20);
        graphics.setFont(font);
        graphics.setColor(Color.white);
        graphics.drawString("score: " + score, 10, 60);
        graphics.drawString("money: " + money, 10, 85);

        for (Lives lives : lives) {
            lives.draw(graphics);
        }
        for (AddLife addLife : addLife) {
            addLife.draw(graphics);
        }
        for (Enemy enemy1 : enemy1) {
            enemy1.draw(graphics);
        }
        for (Enemy enemy2 : enemy2) {
            enemy2.draw(graphics);
        }
        for (Enemy enemy3 : enemy3) {
            enemy3.draw(graphics);
        }
        for (EnemyBullet enemyBullet1 : enemyBullet1) {
            enemyBullet1.draw(graphics);
        }
        for (EnemyBullet enemyBullet2 : enemyBullet2) {
            enemyBullet2.draw(graphics);
        }
        for (EnemyBullet enemyBullet3 : enemyBullet3) {
            enemyBullet3.draw(graphics);
        }
        for (HeroBullet1 heroBullet1 : heroBullet1) {
            heroBullet1.draw(graphics);
        }
        for (HeroBullet2 heroBullet2 : heroBullet2) {
            heroBullet2.draw(graphics);
        }
        hero.draw(graphics);

        if (stage != 4) {
            if (mScore >= 200) {
                if (bossBlood1 >= 0 || bossBlood2 >= 0 || bossBlood3 >= 0) {
                    for (BossBullet bossBullet : bossBullets){
                        bossBullet.draw(graphics);
                    }
                    boss.draw(graphics);
                    graphics.setColor(Color.red);
                    graphics.drawString("bossBlood: ", 10, 135);
                    int bossBlood = 0;
                    if (stage == 1) {
                        bossBlood = bossBlood1;
                    } else if (stage == 2) {
                        bossBlood = bossBlood2;
                    } else if (stage == 3) {
                        bossBlood = bossBlood3;
                    }
                    graphics.drawString(bossBlood + "/2000", 10, 160);
                }
            }
        }
    }

    //判断键盘响应
    public void vk(long time) {
        if (vkLeft) {
            hero.moveLeft(10);
        }
        if (vkRight) {
            hero.moveRight(10);
        }
        if (vkDown) {
            hero.moveDown(10);
        }
        if (vkUp) {
            hero.moveUp(10);
        }
        if (vkFire && time % 10 == 0) {
            HeroBullet1 bullet1 = hero.fire();
            heroBullet1.add(bullet1);
        }
        if (vkLaunch) {
            HeroBullet2 bullet2 = hero.launch();
            heroBullet2.add(bullet2);
        }
    }

    //判断游戏中的各种响应关系
    public void judge(long time, int stage) {
        CommonController commonController = new CommonController();
        //初始化背景
        if (stage == 4) {
            background = new Background(0, 0, Resources.backgroundEverJPG);
        }
        if (stage == 1) {
            background = new Background(0, 0, Resources.background1JPG);
        }
        if (stage == 2) {
            background = new Background(0, 0, Resources.background2JPG);
        }
        if (stage == 3) {
            background = new Background(0, 0, Resources.background3JPG);
        }
        //初始化 boss
        if (boss == null) {
            if (stage == 1) {
                boss = new Boss(800, 200, Resources.boss1PNG);
            } else if (stage == 2) {
                boss = new Boss(800, 200, Resources.boss2PNG);
            } else if (stage == 3) {
                boss = new Boss(800, 200, Resources.boss3PNG);
            }
        }
        //boss 发射子弹
        commonController.bossFire(time,bossBullets,boss,stage);
        //出现新的敌人
        commonController.addNewEnemy(time, enemy1, enemy2, enemy3, stage);
        //敌人移动
        commonController.enemyMove(time, enemy1, enemy2, enemy3, boss, stage);
        //敌人发射子弹
        commonController.enemyFire(time, enemy1, enemy2, enemy3, enemyBullet1, enemyBullet2, enemyBullet3);
        //判断玩家子弹与敌人子弹的关系
        commonController.enemyBulletVSHeroBullet(heroBullet1, heroBullet2, enemyBullet1, enemyBullet2, enemyBullet3,bossBullets);
        //出现新的加血
        commonController.addNewLife(time, addLife);
        //加血移动
        commonController.addLifeMove(time, addLife);
        //玩家获得加血
        for (int i = 0; i < addLife.size(); i++) {
            commonController.addLifeVSHero(i, hero, addLife, lives);
        }
        //敌人是否和玩家相撞
        for (int i = 0; i < enemy1.size(); i++) {
            commonController.enemyVSHero(i, enemy1, hero, lives, 1);
        }
        for (int i = 0; i < enemy2.size(); i++) {
            commonController.enemyVSHero(i, enemy2, hero, lives, 2);
        }
        for (int i = 0; i < enemy3.size(); i++) {
            commonController.enemyVSHero(i, enemy3, hero, lives, 3);
        }
        //boss 子弹是否射中玩家
        for (int i = 0; i < bossBullets.size(); i++) {
            bossBullets.get(i).move_1(time,stage,i);
            commonController.bossBulletVSHero(i,bossBullets,hero,lives);
        }
        //敌人子弹是否射中玩家
        for (int i = 0; i < enemyBullet1.size(); i++) {
            enemyBullet1.get(i).move(time, 1);
            commonController.enemyBulletVSHero(i, enemyBullet1, hero, lives);
        }
        for (int i = 0; i < enemyBullet2.size(); i++) {
            enemyBullet2.get(i).move(time, 1);
            commonController.enemyBulletVSHero(i, enemyBullet2, hero, lives);
        }
        for (int i = 0; i < enemyBullet3.size(); i++) {
            enemyBullet3.get(i).move(time, 1);
            commonController.enemyBulletVSHero(i, enemyBullet3, hero, lives);
        }
        //玩家是否射中敌人
        //子弹 1
        for (int i = 0; i < heroBullet1.size(); i++) {
            heroBullet1.get(i).move(time, 1);
            commonController.heroBullet1VSEnemy(i, heroBullet1, enemy1, enemy2, enemy3, heroBullet2, boss, stage);
        }
        //子弹 2
        for (int i = 0; i < heroBullet2.size(); i++) {
            heroBullet2.get(i).move(time, 1);
            commonController.heroBullet2VSEnemy(i, heroBullet2, heroBullet1, enemy1, enemy2, enemy3, boss, stage);
        }
    }

    //窗口转换之间的条件
    public void winChange() {
        if (bossBlood1 <= 0 && stage == 1) {
            if (StartPattern.gameFrame != null) {
                StartPattern.gameFrame.setVisible(false);
            }
            GameOver.gameOver = new GameOver();
            repaint();
        } else if (bossBlood2 <= 0 && stage == 2) {
            if (StartPattern.gameFrame != null) {
                StartPattern.gameFrame.setVisible(false);
            }
            GameOver.gameOver = new GameOver();
            repaint();
        } else if (bossBlood3 <= 0 && stage == 3) {
            if (StartPattern.gameFrame != null) {
                StartPattern.gameFrame.setVisible(false);
            }
            GameOver.gameOver = new GameOver();
            repaint();
        }
    }

    public GameController(int stage) {
        bossBlood1 = 2000;
        bossBlood2 = 2000;
        bossBlood3 = 2000;
        money = 0;
        life = 3;
        score = 0;
        mScore = 0;
        this.stage = stage;
        this.addKeyListener(this);
        hero = new Hero(50, 300);

        for (int i = 0; i < life; i++) {
            lives.add(new Lives(10 + i * 30, 10));
        }

        //游戏运行时的线程
        Thread thread = new Thread() {
            @Override
            public void run() {
                long time = 0;
                while (true) {
                    //防止游戏运行时间过长，导致溢出异常
                    if (time >= Long.MAX_VALUE - 10) {
                        time = 0;
                    }
                    //判断游戏中的各种响应关系
                    judge(time, stage);
                    //键盘响应
                    vk(time);
                    time += 2;
                    //窗口转换
                    if (life < 1) {
                        if (StartPattern.gameFrame != null) {
                            StartPattern.gameFrame.setVisible(false);
                        }
                        GameOver.gameOver = new GameOver();
                        repaint();
                        while (life < 1) {
                            try {
                                sleep(40);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (life > 0) {
                        //窗口转换之间的条件
                        winChange();
                        while (bossBlood1 <= 0 || bossBlood2 <= 0 || bossBlood3 <= 0) {
                            try {
                                sleep(40);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    repaint();
                    try {
                        sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }
}
