package game.controller;

import game.model.*;

import java.util.List;

/**
 * 不同模式游戏的相同方法类
 *
 * @author lenovo
 * @date 2017/11/24
 */
public class CommonController {

    /**
     * 敌人移动
     *
     * @param time     自定义时间间隔
     * @param enemies1 敌人1
     * @param enemies2 敌人2
     * @param enemies3 敌人3
     * @param boss
     * @param index    关卡标记
     */
    public void enemyMove(long time, List<Enemy> enemies1, List<Enemy> enemies2, List<Enemy> enemies3, Boss boss, int index) {
        for (Enemy enemy1 : enemies1) {
            enemy1.move(time, index);
        }
        for (Enemy enemy2 : enemies2) {
            enemy2.move(time, index);
        }
        for (Enemy enemy3 : enemies3) {
            enemy3.move(time, index);
        }
        if (boss != null) {
            boss.move(time, index);
        }
    }

    /**
     * 敌人与玩家相撞
     *
     * @param i             敌人容器(List)的尺寸
     * @param enemies       敌人容器(List)
     * @param hero          玩家
     * @param lives         血条容器
     * @param enemyClassify 敌人类别 1：敌人1，2：敌人2，3：敌人3
     */
    public void enemyVSHero(int i, List<Enemy> enemies, Hero hero, List<Lives> lives, int enemyClassify) {
        int hx = hero.getX();
        int hy = hero.getY();
        int ex = enemies.get(i).x;
        int ey = enemies.get(i).y;
        if (hx - ex < 43 * enemyClassify && ex - hx < 120 && hy - ey < 43 * enemyClassify && ey - hy < 120) {
            enemies.remove(enemies.get(i));
            GameController.life--;
            if (GameController.life > 0 && GameController.life < lives.size()) {
                lives.remove(GameController.life);
            }
        }
        if (ey > 600 || ey < 0 || ex < 0) {
            enemies.remove(enemies.get(i));
        }
    }

    /**
     * 判断玩家子弹与敌人子弹的关系
     *
     * @param heroBullet1s  玩家子弹1
     * @param heroBullet2s  玩家子弹2
     * @param enemyBullet1s 敌人子弹1
     * @param enemyBullet2s 敌人子弹2
     * @param enemyBullet3s 敌人子弹3
     */
    public void enemyBulletVSHeroBullet(List<HeroBullet1> heroBullet1s, List<HeroBullet2> heroBullet2s, List<EnemyBullet> enemyBullet1s,
                                        List<EnemyBullet> enemyBullet2s, List<EnemyBullet> enemyBullet3s,List<BossBullet> bossBullets) {
        //遍历玩家子弹1
        for (int i = 0; i < heroBullet1s.size(); i++) {
            int hx = heroBullet1s.get(i).x;
            int hy = heroBullet1s.get(i).y;
            //遍历子弹1
            heroBulletAndEnemyBullets(i, hx, hy, heroBullet1s, heroBullet2s, enemyBullet1s, 1);
            //遍历子弹2
            heroBulletAndEnemyBullets(i, hx, hy, heroBullet1s, heroBullet2s, enemyBullet2s, 1);
            //遍历子弹3
            heroBulletAndEnemyBullets(i, hx, hy, heroBullet1s, heroBullet2s, enemyBullet3s, 1);
            //遍历 boss 子弹
            heroBulletAndBossBullets(i,hx,hy,heroBullet1s,heroBullet2s,bossBullets,1);
        }
        //遍历玩家子弹2
        for (int i = 0; i < heroBullet2s.size(); i++) {
            int hx = heroBullet2s.get(i).x;
            int hy = heroBullet2s.get(i).y;
            //遍历子弹1
            heroBulletAndEnemyBullets(i, hx, hy, heroBullet1s, heroBullet2s, enemyBullet1s, 2);
            //遍历子弹2
            heroBulletAndEnemyBullets(i, hx, hy, heroBullet1s, heroBullet2s, enemyBullet2s, 2);
            //遍历子弹3
            heroBulletAndEnemyBullets(i, hx, hy, heroBullet1s, heroBullet2s, enemyBullet3s, 2);
            //遍历 boss 子弹
            heroBulletAndBossBullets(i,hx,hy,heroBullet1s,heroBullet2s,bossBullets,2);
        }

    }

    /**
     * 遍历敌人子弹与玩家子弹的关系
     *
     * @param i            玩家子弹容器的一个子弹编号
     * @param hx           玩家子弹横坐标
     * @param hy           玩家子弹纵坐标
     * @param heroBullet1s 玩家子弹1容器
     * @param heroBullet2s 玩家子弹2容器
     * @param enemyBullets 敌人子弹
     * @param classify     对玩家不同子弹的判定，1：子弹1，2：子弹2
     */
    public void heroBulletAndEnemyBullets(int i, int hx, int hy, List<HeroBullet1> heroBullet1s, List<HeroBullet2> heroBullet2s,
                                          List<EnemyBullet> enemyBullets, int classify) {
        for (int j = 0; j < enemyBullets.size(); j++) {
            int ex = enemyBullets.get(j).x;
            int ey = enemyBullets.get(j).y;
            if (hx - ex < 20 && ex - hx < 20 && hy - ey < 20 && ey - hy < 20) {
                if (classify == 1) {
                    heroBullet1s.remove(i);
                }
                if (classify == 2) {
                    heroBullet2s.remove(i);
                }
                enemyBullets.remove(j);
                continue;
            }
        }
    }

    /**
     * 遍历 boss 子弹与玩家子弹的关系
     *
     * @param i            玩家子弹容器的一个子弹编号
     * @param hx           玩家子弹横坐标
     * @param hy           玩家子弹纵坐标
     * @param heroBullet1s 玩家子弹1容器
     * @param heroBullet2s 玩家子弹2容器
     * @param bossBullets boss子弹
     * @param classify     对玩家不同子弹的判定，1：子弹1，2：子弹2
     */
    public void heroBulletAndBossBullets(int i, int hx, int hy, List<HeroBullet1> heroBullet1s, List<HeroBullet2> heroBullet2s,
                                          List<BossBullet> bossBullets, int classify) {
        for (int j = 0; j < bossBullets.size(); j++) {
            int ex = bossBullets.get(j).x;
            int ey = bossBullets.get(j).y;
            if (hx - ex < 20 && ex - hx < 20 && hy - ey < 20 && ey - hy < 20) {
                if (classify == 1) {
                    heroBullet1s.remove(i);
                }
                if (classify == 2) {
                    heroBullet2s.remove(i);
                }
                bossBullets.remove(j);
                continue;
            }
        }
    }

    /**
     * 子弹与玩家相撞
     *
     * @param i            敌人子弹容器尺寸
     * @param enemyBullets 敌人子弹容器
     * @param hero         玩家
     * @param lives        血条容器
     */
    public void enemyBulletVSHero(int i, List<EnemyBullet> enemyBullets, Hero hero, List<Lives> lives) {
        int hx = hero.getX();
        int hy = hero.getY();
        int ex = enemyBullets.get(i).getX();
        int ey = enemyBullets.get(i).getY();
        if (hx - ex < 20 && ex - hx < 120 && hy - ey < 20 && ey - hy < 120) {
            enemyBullets.remove(enemyBullets.get(i));
            GameController.life--;
            if (GameController.life > 0 && GameController.life < lives.size()) {
                lives.remove(GameController.life);
            }
        }
        if (ey > 600 - 20 || ey < 0 || ex < 0) {
            enemyBullets.remove(enemyBullets.get(i));
        }
    }

    /**
     *  boss 子弹与玩家相撞
     * @param i 子弹容器中遍历到相应子弹的编号
     * @param bossBullets
     * @param hero
     * @param lives 血条容器
     */
    public void bossBulletVSHero(int i,List<BossBullet> bossBullets,Hero hero,List<Lives> lives){
        int hx = hero.getX();
        int hy = hero.getY();
        int bx = bossBullets.get(i).getX();
        int by = bossBullets.get(i).getY();
        if (hx - bx < 20 && bx - hx < 120 && hy - by < 20 && by - hy < 120){
            bossBullets.remove(bossBullets.get(i));
            GameController.life--;
            if (GameController.life > 0 && GameController.life < lives.size()) {
                lives.remove(GameController.life);
            }
        }
        if (by > 600 - 20 || by < 0 || bx < 0){
            bossBullets.remove(bossBullets.get(i));
        }
    }

    /**
     * 玩家子弹 1 与敌人相撞
     *
     * @param i           玩家子弹1尺寸
     * @param heroBullet1 玩家子弹1
     * @param enemy1      敌人容器1
     * @param enemy2      敌人容器2
     * @param enemy3      敌人容器3
     * @param heroBullet2 玩家子弹2
     * @param boss
     */
    public void heroBullet1VSEnemy(int i, List<HeroBullet1> heroBullet1, List<Enemy> enemy1, List<Enemy> enemy2,
                                   List<Enemy> enemy3, List<HeroBullet2> heroBullet2, Boss boss, int stage) {
        int hx = heroBullet1.get(i).getX();
        int hy = heroBullet1.get(i).getY();
        if (hx > 1000) {
            heroBullet1.remove(heroBullet1.get(i));
        }
        //enemy1
        traverseEnemy(i, hx, hy, 1, enemy1, heroBullet1, heroBullet2, 1);
        //enemy2
        traverseEnemy(i, hx, hy, 1, enemy2, heroBullet1, heroBullet2, 2);
        //enemy3
        traverseEnemy(i, hx, hy, 1, enemy3, heroBullet1, heroBullet2, 3);
        if (stage != 0) {
            //boss
            if (GameController.mScore > 200) {
                heroBulletVSBoss(i, hx, hy, boss, 1, heroBullet1, heroBullet2, stage);
            }
        }

    }

    /**
     * 玩家子弹 2 与敌人相撞
     *
     * @param i           玩家子弹2容器
     * @param heroBullet2 玩家子弹2
     * @param heroBullet1 玩家子弹1
     * @param enemy1      敌人类1
     * @param enemy2      敌人类2
     * @param enemy3      敌人类3
     * @param boss
     */
    public void heroBullet2VSEnemy(int i, List<HeroBullet2> heroBullet2, List<HeroBullet1> heroBullet1,
                                   List<Enemy> enemy1, List<Enemy> enemy2, List<Enemy> enemy3, Boss boss, int stage) {
        int hx = heroBullet2.get(i).getX();
        int hy = heroBullet2.get(i).getY();
        if (hx > 1000) {
            heroBullet2.remove(heroBullet2.get(i));
        }
        //enemy1
        traverseEnemy(i, hx, hy, 2, enemy1, heroBullet1, heroBullet2, 1);
        //enemy2
        traverseEnemy(i, hx, hy, 2, enemy2, heroBullet1, heroBullet2, 2);
        //enemy3
        traverseEnemy(i, hx, hy, 2, enemy3, heroBullet1, heroBullet2, 3);
        if (stage != 0) {
            //boss
            if (GameController.score > 200) {
                heroBulletVSBoss(i, hx, hy, boss, 2, heroBullet1, heroBullet2, stage);
            }
        }
    }

    /**
     * 遍历不同种类的敌人与玩家子弹的关系
     *
     * @param i             子弹容器尺寸
     * @param hx            玩家子弹横坐标
     * @param hy            玩家子弹纵坐标
     * @param classify      对玩家不同子弹的处理判断 1：玩家子弹1，0：玩家子弹2
     * @param enemies       敌人容器
     * @param heroBullet1s  玩家子弹1容器
     * @param heroBullet2s  玩家子弹2
     * @param enemyClassify 不同的敌人，1：敌人1，2：敌人2，3：敌人3
     */
    public void traverseEnemy(int i, int hx, int hy, int classify, List<Enemy> enemies,
                              List<HeroBullet1> heroBullet1s, List<HeroBullet2> heroBullet2s, int enemyClassify) {
        for (int j = 0; j < enemies.size(); j++) {
            int ex = enemies.get(j).getX();
            int ey = enemies.get(j).getY();
            if (ex - hx < 20 && hx - ex < 43 * enemyClassify && ey - hy < 20 && hy - ey < 43 * enemyClassify) {
                enemies.remove(enemies.get(j));
                if (classify == 1) {
                    heroBullet1s.remove(heroBullet1s.get(i));
                } else if (classify == 2) {
                    heroBullet2s.remove(heroBullet2s.get(i));
                }
                GameController.money += 20 * enemyClassify;
                GameController.score += 20 * enemyClassify;
                GameController.mScore += 20 * enemyClassify;
            }
        }
    }

    /**
     * 判断玩家子弹与 boos 的关系
     * @param i            玩家子弹容器尺寸
     * @param hx           玩家子弹横坐标
     * @param hy           玩家子弹纵坐标
     * @param boss         boss类
     * @param classify     玩家子弹类别
     * @param heroBullets1 玩家子弹1容器
     * @param heroBullet2s 玩家子弹2容器
     */
    public void heroBulletVSBoss(int i, int hx, int hy, Boss boss, int classify, List<HeroBullet1> heroBullets1,
                                 List<HeroBullet2> heroBullet2s, int stage) {
        int bx = boss.getX();
        int by = boss.getY();
        if (GameController.bossBlood1 >= 0 || GameController.bossBlood2 >= 0 || GameController.bossBlood3 >= 0) {
            if (hx - bx < 200 && bx - hx < 20 && hy - by < 200 && by - hy < 20) {
                if (classify == 1) {
                    heroBullets1.remove(heroBullets1.get(i));
                } else if (classify == 2) {
                    heroBullet2s.remove(heroBullet2s.get(i));
                }
                GameController.money += 90;
                GameController.score += 100;
                GameController.mScore += 100;
                if (stage == 1) {
                    GameController.bossBlood1 -= 40;
                } else if (stage == 2) {
                    GameController.bossBlood2 -= 40;
                } else if (stage == 3) {
                    GameController.bossBlood3 -= 40;
                }
            }
        }
    }

    /**
     * 敌人发射子弹
     * @param time         时间间隔
     * @param enemy1       敌人1
     * @param enemy2       敌人2
     * @param enemy3       敌人3
     * @param enemyBullet1 敌人1子弹
     * @param enemyBullet2 敌人2子弹
     * @param enemyBullet3 敌人3子弹
     */
    public void enemyFire(long time, List<Enemy> enemy1, List<Enemy> enemy2, List<Enemy> enemy3,
                          List<EnemyBullet> enemyBullet1, List<EnemyBullet> enemyBullet2, List<EnemyBullet> enemyBullet3) {
        if (time % 300 == 0) {
            for (Enemy enemy : enemy1) {
                EnemyBullet enemyBullet = enemy.fire(20, 20, Resources.eBullet1PNG);
                enemyBullet1.add(enemyBullet);
            }
        }
        if (time % 400 == 0) {
            for (Enemy enemy : enemy2) {
                EnemyBullet enemyBullet = enemy.fire(20, 20, Resources.eBullet2PNG);
                enemyBullet2.add(enemyBullet);
            }
        }
        if (time % 500 == 0) {
            for (Enemy enemy : enemy3) {
                EnemyBullet enemyBullet = enemy.fire(20, 20, Resources.eBullet3PNG);
                enemyBullet3.add(enemyBullet);
            }
        }
    }

    /**
     * boss 发射子弹
     * @param time 时间间隔
     * @param bossBullets boss 子弹容器
     * @param boss
     * @param stage 关卡
     */
    public void bossFire(long time,List<BossBullet> bossBullets,Boss boss,int stage){
        if (time % 100 == 0 && boss != null){
            for (int i = 0; i < 7; i++) {
                BossBullet bossBullet = boss.fire(20,20,Resources.eBullet4PNG);
                bossBullets.add(bossBullet);
            }
        }
    }

    /**
     * 出现新的敌人
     *
     * @param time   时间间隔
     * @param enemy1 敌人1
     * @param enemy2 敌人2
     * @param enemy3 敌人3
     * @param stage  当前关卡
     */
    public void addNewEnemy(long time, List<Enemy> enemy1, List<Enemy> enemy2, List<Enemy> enemy3, int stage) {
        //设置敌人出现的纵坐标的随机值
        int ey = (int) (Math.random() * 600 - 50) >= 0 ? (int) (Math.random() * 600 - 50) : 0;
        if (enemy1.size() < 7 && time % 300 == 0) {
            Enemy enemy = null;
            if (stage == 4) {
                enemy = new Enemy(1000, ey, 45, 45, Resources.enemy11PNG);
            }
            if (stage == 1) {
                enemy = new Enemy(1000, ey, 45, 45, Resources.enemy11PNG);
            }
            if (stage == 2) {
                enemy = new Enemy(1000, ey, 45, 45, Resources.enemy21PNG);
            }
            if (stage == 3) {
                enemy = new Enemy(1000, ey, 45, 45, Resources.enemy31PNG);
            }
            enemy1.add(enemy);
        } else if (enemy2.size() < 5 && time % 400 == 0) {
            Enemy enemy = null;
            if (stage == 4) {
                enemy = new Enemy(1000, ey, 90, 90, Resources.enemy12PNG);
            }
            if (stage == 1) {
                enemy = new Enemy(1000, ey, 90, 90, Resources.enemy12PNG);
            }
            if (stage == 2) {
                enemy = new Enemy(1000, ey, 90, 90, Resources.enemy22PNG);
            }
            if (stage == 3) {
                enemy = new Enemy(1000, ey, 90, 90, Resources.enemy32PNG);
            }
            enemy2.add(enemy);
        } else if (enemy3.size() < 3 && time % 500 == 0) {
            Enemy enemy = null;
            if (stage == 4) {
                enemy = new Enemy(1000, ey, 135, 135, Resources.enemy13PNG);
            }
            if (stage == 1) {
                enemy = new Enemy(1000, ey, 135, 135, Resources.enemy13PNG);
            }
            if (stage == 2) {
                enemy = new Enemy(1000, ey, 135, 135, Resources.enemy23PNG);
            }
            if (stage == 3) {
                enemy = new Enemy(1000, ey, 135, 135, Resources.enemy33PNG);
            }
            enemy3.add(enemy);
        }
    }

    /**
     * 出现新的加血
     *
     * @param time
     * @param addLife
     */
    public void addNewLife(long time, List<AddLife> addLife) {
        if (time % 1000 == 0) {
            AddLife addLife1 = new AddLife(1000, (int) (Math.random() * 600));
            addLife.add(addLife1);
        }
    }

    /**
     * 加血移动
     *
     * @param time
     * @param addLife
     */
    public void addLifeMove(long time, List<AddLife> addLife) {
        for (AddLife addLife1 : addLife) {
            addLife1.move(time);
        }
    }

    /**
     * 玩家获取加血
     *
     * @param i       加血容器尺寸
     * @param hero    玩家
     * @param addLife 加血类
     * @param lives   血量条
     */
    public void addLifeVSHero(int i, Hero hero, List<AddLife> addLife, List<Lives> lives) {
        int hx = hero.getX();
        int hy = hero.getY();
        int ax = addLife.get(i).getX();
        int ay = addLife.get(i).getY();
        if (hx - ax < 50 && ax - hx < 140 && hy - ay < 50 && ay - hy < 140) {
            GameController.score += 20;
            GameController.mScore += 20;
            GameController.money += 20;
            addLife.remove(i);
            if (GameController.life < 3) {
                lives.add(new Lives(10 + lives.size() * 30, 10));
                GameController.life++;
            }
        }
        if (ax < 0 || ay > 600 - 50 || ax < 0) {
            addLife.remove(addLife.get(i));
        }
    }

}
