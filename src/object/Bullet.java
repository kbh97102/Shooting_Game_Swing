package object;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Bullet {

    private Image bulletImage;
    private int x;
    private int y;
    private int moveDelta;
    private int direction;
    private JLabel bulletLabel;

    public static class Builder {
        private Image image;
        private int x;
        private int y;
        private int moveDelta;
        private int direction;
//        private JLabel label;

        public Builder(){
            moveDelta = 10;
//            label = new JLabel();
        }

        public Builder loadImage(String uri){
            try{
                image = ImageIO.read(new File(uri));
            }catch (IOException e){
                e.printStackTrace();
            }
            return this;
        }

        public Builder setBulletLocation(int x, int y){
            this.x = x;
            this.y = y;
//            label.setLocation(x, y);
            return this;
        }

        public Builder setDirection(int direction){
            this.direction = direction;
            return this;
        }

        public Builder setMoveDelta(int moveDelta){
            this.moveDelta = moveDelta;
            return this;
        }

        public Bullet build(int x, int y){
            Bullet bullet = new Bullet();
            bullet.bulletImage = image;
//            bullet.bulletLabel = new JLabel();
//            bullet.bulletLabel.setSize(image.getWidth(null), image.getHeight(null));
//            bullet.bulletLabel.setIcon(new ImageIcon(image));
            bullet.direction = direction;
            bullet.x = x;
            bullet.y = y;
//            bullet.getBulletLabel().setLocation(x, y);
            bullet.moveDelta = moveDelta;
            return bullet;
        }
    }

    public void bulletMove(){
        x += moveDelta*direction;
    }

    public void draw(){
        bulletLabel.setLocation(x, y);
    }

    public JLabel getBulletLabel(){
        return bulletLabel;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Image getBulletImage() {
        return bulletImage;
    }

    public int getDirection(){
        return direction;
    }
}

