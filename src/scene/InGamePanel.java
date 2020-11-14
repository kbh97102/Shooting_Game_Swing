package scene;

import logic.Logic;

import javax.swing.*;
import java.awt.*;

public class InGamePanel extends JPanel {

    private InformationPanel infoPanel;
    private PlayPanel playPanel;

    public InGamePanel() {
        init();
    }

    private void init() {
        infoPanel = new InformationPanel();
        playPanel = new PlayPanel();
        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.NORTH);
        add(playPanel, BorderLayout.CENTER);
    }
}
