package main;

import scene.InGamePanel;

import javax.swing.*;
import java.awt.*;

public class Game {

    public static int WIDTH = 1280;
    public static int HEIGHT = 720;

    private final String gameName = "Shooting main.Game";
    private JFrame mainFrame;
    private JPanel contentPanel;
    private JPanel inGamePanel;

    public Game() {
        init();
    }

    private void init(){
        mainFrame = new JFrame(gameName);
        contentPanel = new JPanel();
        inGamePanel = new InGamePanel();

        contentPanel.setLayout(new BorderLayout());

        contentPanel.add(inGamePanel, BorderLayout.CENTER);
        mainFrame.setContentPane(contentPanel);
        initFrame();
    }

    private void initFrame(){
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }
}
