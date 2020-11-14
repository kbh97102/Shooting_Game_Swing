package scene;

import logic.Logic;
import object.Boss;
import object.Bullet;
import object.User;

import javax.swing.*;
import java.awt.*;

public class PlayPanel extends JPanel {

    private Image back = new ImageIcon("images/background_image.jpg").getImage();
    private User user;
    private Boss boss;

    public PlayPanel() {
        setBackground(new Color(255, 0, 0, 0));
        init();

        Logic.getInstance().setPlayPanel(this);
    }

    private void init() {
        setLayout(null);
        boss = new Boss();
        user = new User();
        add(user.getMoveLabel());
        user.move(this::requestRepaint);
        Logic.getInstance().setObjects(boss, user);

//        boss.move(null);
//        boss.shoot();
    }

    private void requestRepaint(){
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(back, 0,0, null);
        g.drawImage(user.getImage(), (int)user.x, (int)user.y, null);
        g.drawImage(boss.getImage(), (int)boss.x, (int)boss.y, null);
        for(Bullet bullet : Logic.getInstance().getBullets()){
            g.drawImage(bullet.getBulletImage(), bullet.getX(), bullet.getY(), null);
        }
        System.out.println(Logic.getInstance().getBullets().size());
    }
}
