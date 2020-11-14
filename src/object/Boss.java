package object;

import logic.Logic;
import main.Game;

import java.util.Random;
import java.util.concurrent.*;

public class Boss extends MoveAble {

    private Bullet.Builder builder;
    private ScheduledExecutorService service;
    private ScheduledFuture<?> moveFuture;
    private ScheduledFuture<?> shootFuture;

    public Boss() {
        x = 800;
        y = 0;
        moveDelta = 150;
        loadImage("images/boss.png");
        service = Executors.newScheduledThreadPool(2);
        builder = new Bullet.Builder()
                .loadImage("images/bossBullet.png")
                .setDirection(-1)
                .setMoveDelta(10);
    }

    @Override
    public void move(Runnable repaint) {
        Random random = new Random();
        moveFuture = service.scheduleAtFixedRate(()->{
            int randomMove = random.nextInt(5);
            if (0 == randomMove) {
                if (y-moveDelta > 0){
                    y -= moveDelta;
                }
            } else if (1 == randomMove) {
                if (y+moveDelta + getImage().getHeight(null) < Game.HEIGHT){
                    y += moveDelta;
                }
            } else if (2 == randomMove) {
                if (x+moveDelta + getImage().getWidth(null) < Game.WIDTH){
                    x += moveDelta;
                }
            } else if (3 == randomMove) {
                if (x-moveDelta > Game.WIDTH/2f)
                x -= moveDelta;
            }
        },0, 200, TimeUnit.MILLISECONDS);
    }

    @Override
    public void shoot() {
        shootFuture = service.scheduleAtFixedRate(()->{
            int bulletXpos = (int) x;
            int bulletYpos = (int) y + getImage().getHeight(null)/3;
            Logic.getInstance().fireBullet(builder.build(bulletXpos, bulletYpos));
        },0, 1500, TimeUnit.MILLISECONDS);
    }

    public void stop(){
        moveFuture.cancel(true);
        shootFuture.cancel(true);
        service.shutdownNow();
    }
}
