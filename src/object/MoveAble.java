package object;

import main.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

abstract public class MoveAble {

    public int hp;
    public double x;
    public double y;
    public double moveDelta;
    private Image image;
    private int hpDelta;
    private HashMap<Integer, Consumer<Boolean>> moveMap;
    private boolean upKey = false;
    private boolean downKey = false;
    private boolean leftKey = false;
    private boolean rightKey = false;
    private ExecutorService service;

    public MoveAble(){
        service = Executors.newSingleThreadExecutor();
        hp = 10;
        hpDelta = 1;
        moveDelta = 0.00001;
        moveMap = new HashMap<>();
        setUpMovement();
    }

    public void loadImage(String uri){
        try{
            image = ImageIO.read(new File(uri));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void shoot() {}

    public Image getImage(){
        return image;
    }

    public void reduceHP(){
        hp -= hpDelta;
    }

    private void setUpMovement() {
        moveMap = new HashMap<>();
        moveMap.put(KeyEvent.VK_RIGHT, (b) -> {
            if (x + image.getWidth(null) + moveDelta < Game.WIDTH) {
                rightKey = b;
            }
        });
        moveMap.put(KeyEvent.VK_LEFT, (b) -> {
            if (x - moveDelta > 0) {
                leftKey = b;
            }
        });
        moveMap.put(KeyEvent.VK_UP, (b) -> {
            if (y - moveDelta > 0) {
                upKey = b;
            }
        });
        moveMap.put(KeyEvent.VK_DOWN, (b) -> {
            if (y + image.getHeight(null) + moveDelta < Game.HEIGHT) {
                downKey = b;
            }
        });
    }

    public void addKeyListener(JLabel label){
        label.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (moveMap.containsKey(e.getKeyCode())) {
                    moveMap.get(e.getKeyCode()).accept(true);
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    //shoot
                    shoot();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (moveMap.containsKey(e.getKeyCode())) {
                    moveMap.get(e.getKeyCode()).accept(false);
                }
            }
        });
    }

    public void move(Runnable repaint) {
        service.execute(()->{
            while (true) {
                if (upKey && rightKey) {
                    x += moveDelta;
                    y -= moveDelta;
                } else if (upKey && leftKey) {
                    x -= moveDelta;
                    y -= moveDelta;
                } else if (downKey && rightKey) {
                    x += moveDelta;
                    y += moveDelta;
                } else if (downKey && leftKey) {
                    x -= moveDelta;
                    y += moveDelta;
                } else if (upKey) {
                    y -= moveDelta;
                } else if (downKey) {
                    y += moveDelta;
                } else if (rightKey) {
                    x += moveDelta;
                } else if (leftKey) {
                    x -= moveDelta;
                }
//                repaint.run();
            }
        });
    }

    public void stop(){
        service.shutdownNow();
    }
}
