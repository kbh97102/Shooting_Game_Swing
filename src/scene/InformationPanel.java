package scene;

import logic.Logic;

import javax.swing.*;
import java.awt.*;

public class InformationPanel extends JPanel {

    private JLabel userLifeLabel;
    private JLabel bossLifeLabel;
    private JLabel userLife;
    private JLabel bossLife;
    private JLabel scoreLabel;
    private JLabel score;

    public InformationPanel() {
        init();
    }

    private void init() {
        setLayout(new GridLayout(1, 6));
        setBackground(new Color(255,0,0,0));
        userLifeLabel = new JLabel();
        bossLifeLabel = new JLabel();
        userLife = new JLabel();
        bossLife = new JLabel();
        scoreLabel = new JLabel();
        score = new JLabel();

        userLife.setText("3");
        score.setText("0");
        bossLife.setText("100");

        userLife.setFont(new Font("test" , Font.BOLD ,20));
        score.setFont(new Font("test" , Font.BOLD ,20));
        bossLife.setFont(new Font("test" , Font.BOLD ,20));

        userLifeLabel.setFont(new Font("test" , Font.BOLD ,20));
        userLifeLabel.setText("UserLife");
        scoreLabel.setFont(new Font("test1", Font.BOLD, 20));
        scoreLabel.setText("Score");
        bossLifeLabel.setFont(new Font("test1", Font.BOLD, 20));
        bossLifeLabel.setText("BossLife");


        add(userLifeLabel);
        add(userLife);
        add(scoreLabel);
        add(score);
        add(bossLifeLabel);
        add(bossLife);

        Logic.getInstance().setLifeChange(this::setUserLife, this::setBossLife);
    }

    public void setUserLife(String life) {
        userLife.setText(life);
        invalidate();
        repaint();
    }

    public void setBossLife(String life) {
        bossLife.setText(life);
        invalidate();
        repaint();
    }
}
