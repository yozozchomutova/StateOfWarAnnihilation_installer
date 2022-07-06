package eu.jozoproductions.toppanels;

import eu.jozoproductions.Main;
import eu.jozoproductions.ui.ImageUI;
import eu.jozoproductions.ui.WindowBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LoadingPanel extends JLayeredPane {

    public WindowBar mainWindowBar;

    public static float loadingRotation = 0;

    //UI
    public static ImageUI loadingBackground;

    public LoadingPanel() {
        super();

        setLayout(null);
        setBounds(0, 0, Main.WIN_WIDTH, Main.WIN_HEIGHT);
        setOpaque(false);

        //Window Bar
        mainWindowBar = new WindowBar(Main.frame, this, Main.WIN_WIDTH, false, true, Main.TITLE);
        mainWindowBar.setIcon("icon");

        //-<UI>-
        loadingBackground = new ImageUI(this, 0, 0, Main.WIN_WIDTH, Main.WIN_HEIGHT, "black_bcg", true, true, false);
        loadingBackground.addMouseListener(new MouseListener() {
            @Override public void mouseClicked(MouseEvent e){}@Override public void mousePressed(MouseEvent e){}@Override public void mouseReleased(MouseEvent e){}@Override public void mouseEntered(MouseEvent e){}@Override public void mouseExited(MouseEvent e){}
        }); //Prevent trough-click
        loadingBackground.setDrawCallback(g -> {
            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(10));
            g.drawArc((int)(Main.WIN_WIDTH/2f)-64,(int)(Main.WIN_HEIGHT/2f)-64,128, 128, (int)loadingRotation, 180);
            g.drawArc((int)(Main.WIN_WIDTH/2f)-48,(int)(Main.WIN_HEIGHT/2f)-48,96, 96, (int)-loadingRotation, 180);
        });

        Main.frame.add(this);

        repaint();
    }

    public void showLoading() {
        loadingBackground.setVisible(true);
    }

    public void hideLoading() {
        loadingBackground.setVisible(false);
    }
}
