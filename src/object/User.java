package object;

import logic.Logic;

import javax.swing.*;
import java.util.concurrent.ScheduledFuture;

public class User extends MoveAble {

    private JLabel moveLabel;
    private Bullet.Builder builder;

    public User() {
        loadImage("images/user_image.png");
        moveLabel = new JLabel();
        moveLabel.setBounds(0,0,0,0);
        moveLabel.setFocusable(true);
        addKeyListener(moveLabel);

        builder = new Bullet.Builder()
                .loadImage("images/bullet.png")
                .setDirection(1);
    }

    public JLabel getMoveLabel(){
        return moveLabel;
    }

    @Override
    public void shoot() {
        int bulletXpos = (int)x + getImage().getWidth(null);
        int bulletYpos = (int)y + getImage().getHeight(null)/2;
        Logic.getInstance().fireBullet(builder.build(bulletXpos, bulletYpos));
    }

}
