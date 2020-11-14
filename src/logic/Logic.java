package logic;

import object.Boss;
import object.Bullet;
import object.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Logic {

    private Vector<Bullet> bullets;
    private ScheduledThreadPoolExecutor service;
    private ScheduledFuture<?> moveFuture;
    private ScheduledFuture<?> drawFuture;
    private JPanel playPanel;
    private Collider collider;
    private Consumer<String> userLifeChange;
    private Consumer<String> bossLifeChange;
    private User user;
    private Boss boss;

    private Logic() {
        collider = new Collider();
        bullets = new Vector<>();
        startThread();
    }

    public void setObjects(Boss boss, User user) {
        this.user = user;
        this.boss = boss;
    }

    public void setLifeChange(Consumer<String> userLifeChange, Consumer<String> bossLifeChange) {
        this.userLifeChange = userLifeChange;
        this.bossLifeChange = bossLifeChange;
    }

    private static class LogicHolder {
        public static final Logic instance = new Logic();
    }

    public static Logic getInstance() {
        return LogicHolder.instance;
    }

    public Vector<Bullet> getBullets() {
        return bullets;
    }

    public void setPlayPanel(JPanel panel) {
        playPanel = panel;
    }

    public void fireBullet(Bullet bullet) {
        bullets.add(bullet);
//        playPanel.add(bullet.getBulletLabel());
    }

    private void drawBullets() {
        for (Bullet bullet : bullets) {
            bullet.draw();
            playPanel.revalidate();
            playPanel.repaint();
        }
        System.out.println(drawFuture.isCancelled());
        System.out.println(bullets.size());
    }

    private void startThread() {
        service = new ScheduledThreadPoolExecutor(5);

        moveFuture = service.scheduleAtFixedRate(this::moveBullets, 0, 1000 / 60, TimeUnit.MILLISECONDS);
        drawFuture = service.scheduleAtFixedRate(this::drawBullets, 0, 1000 / 90, TimeUnit.MILLISECONDS);

        ArrayList<Bullet> removeBullets = new ArrayList<>();
        service.scheduleAtFixedRate(() -> {
            for (Bullet bullet : bullets) {
                if (collider.isBulletCollideAtScreen(bullet.getX())) {
                    removeBullets.add(bullet);
                    playPanel.remove(bullet.getBulletLabel());
                } else if (bullet.getDirection() == 1 && collider.isUserBulletCollide(bullet.getX(), bullet.getY(), (int) boss.x, (int) boss.y, (int) boss.y + boss.getImage().getHeight(null))) {
                    removeBullets.add(bullet);
                    boss.reduceHP();
                    bossLifeChange.accept(Integer.toString(boss.hp));
                } else if (collider.isBossBulletCollide(bullet.getX(), bullet.getY(), (int) user.x, (int) user.y, (int) user.y + user.getImage().getHeight(null))) {
                    removeBullets.add(bullet);
                    user.reduceHP();
                    userLifeChange.accept(Integer.toString(user.hp));
                }
            }
            bullets.removeAll(removeBullets);
            for (Bullet bullet : removeBullets) {
                bullet.getBulletLabel().setVisible(false);
                playPanel.remove(bullet.getBulletLabel());
            }
            removeBullets.clear();
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    private void shutdownThread() {
        moveFuture.cancel(true);
        drawFuture.cancel(true);
        service.shutdown();
    }

    private void moveBullets() {
        for (Bullet bullet : bullets) {
            bullet.bulletMove();
        }
    }

}
