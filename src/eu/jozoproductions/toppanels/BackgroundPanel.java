package eu.jozoproductions.toppanels;

import eu.jozoproductions.Main;
import eu.jozoproductions.ui.ImageUI;
import eu.jozoproductions.ui.WindowBar;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JLayeredPane {

    //UI
    public static ImageUI background;

    public BackgroundPanel() {
        super();

        setLayout(null);

        //-<UI>-
        background = new ImageUI(this, 0, 0, Main.WIN_WIDTH, Main.WIN_HEIGHT, "bcg1", false, true, false);

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        repaint();
    }
}
