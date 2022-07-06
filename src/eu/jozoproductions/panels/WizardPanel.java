package eu.jozoproductions.panels;

import eu.jozoproductions.Main;
import eu.jozoproductions.ui.ImageUI;
import eu.jozoproductions.ui.Text;
import eu.jozoproductions.ui.WindowBar;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public abstract class WizardPanel extends JLayeredPane {

    public SwipeSide swipeSide = SwipeSide.RIGHT;
    public float x;

    public WizardPanel() {
        super();

        setLayout(null);
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
        //setBackground(Color.DARK_GRAY);
        x = Main.WIN_WIDTH;
        setBounds((int)x, 0, Main.WIN_WIDTH, Main.WIN_HEIGHT);

        OnUISetup();
        Main.frame.add(this);
        Main.allPanels.add(this);
    }

    public void moveX(float moveX) {
        x += moveX;
        setLocation((int)x, getY());
    }

    public abstract void OnUISetup();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //repaint();
    }

    public static void ChangePanels(WizardPanel oldPanel, WizardPanel newPanel, SwipeSide sw) {
        oldPanel.swipeSide = sw;
        newPanel.swipeSide = sw;

        Main.currentPanel = newPanel;
    }

    public enum SwipeSide {
        LEFT(-1),
        RIGHT(1);

        public int value;

        SwipeSide(int value) {
            this.value = value;
        }
    }
}