package eu.jozoproductions.ui;

import eu.jozoproductions.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class WindowBar implements MouseListener {

    public static final int BAR_HEIGHT = 20;

    public static LinkedList<WindowBar> windowBars = new LinkedList<>();

    public ImageUI windowBar, close, minimize;

    public Window window;

    public boolean windowIsMoving = false;

    public JLabel windowIcon;
    public JLabel windowTItleBar;

    public WindowBar(Window parent, Container container, int frameWidth, boolean isDialog, boolean closeAble, String titleText) {
        this.window = parent;

        windowIcon = new JLabel();
        windowIcon.setForeground(Color.WHITE);
        windowIcon.setBounds(2, 0, WindowBar.BAR_HEIGHT, WindowBar.BAR_HEIGHT);
        windowIcon.setVerticalAlignment(SwingConstants.TOP);
        windowIcon.setVisible(false);
        container.add(windowIcon);

        windowTItleBar = new JLabel(titleText);
        windowTItleBar.setForeground(Color.WHITE);
        windowTItleBar.setBounds(3, 0, 1500, WindowBar.BAR_HEIGHT);
        windowTItleBar.setVerticalAlignment(SwingConstants.TOP);
        container.add(windowTItleBar);

        if (closeAble) {
            close = new ImageUI(container, frameWidth - 50, -BAR_HEIGHT, 50, BAR_HEIGHT, "btn_close", false, true, true);
            close.setClickCallback(() -> {
                if (!isDialog) { //Probably it's main window
                    Main.confirmDLG.showDialog("Exit", "Are you sure to exit and terminate all current running tasks?", new ImageUI.ClickCallback() {
                        @Override
                        public void OnClick() {
                            Main.shutdown();
                        }
                    });
                } else {
                    window.setVisible(false);
                }
            });
        }

        if (!isDialog) {
            minimize = new ImageUI(container, frameWidth - 100, -BAR_HEIGHT, 50, BAR_HEIGHT, "btn_minimize", false, true, true);
            minimize.setClickCallback(new ImageUI.ClickCallback() {
                @Override
                public void OnClick() {
                    ((JFrame) window).setExtendedState( JFrame.ICONIFIED );
                }
            });
        }

        windowBar = new ImageUI(container, 0, -BAR_HEIGHT, frameWidth, BAR_HEIGHT, "window_bar_bcg",true, true, false);
        windowBar.addMouseListener(this);

        windowBars.add(this);
    }

    public void setIcon(String path) {
        windowTItleBar.setBounds(BAR_HEIGHT+3, 0, 1500, WindowBar.BAR_HEIGHT);

        Image iconImg = new ImageIcon(getClass().getResource("/" + path + ".png")).getImage().getScaledInstance(BAR_HEIGHT, BAR_HEIGHT, Image.SCALE_FAST);
        windowIcon.setIcon(new ImageIcon(iconImg));
        windowIcon.setVisible(true);
    }

    public void updateUI(int frameWidth) {
        close.setBounds(frameWidth - 50, -BAR_HEIGHT, 50, BAR_HEIGHT);
        minimize.setBounds(frameWidth - 100, -BAR_HEIGHT, 50, BAR_HEIGHT);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        windowIsMoving = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        windowIsMoving = false;

        //Normalize (don't let it go off the bounds)
        window.setLocation(
                Math.min(window.getX(), Main.SCREEN_WIDTH - 50),
                Math.min(window.getY(), Main.SCREEN_HEIGHT - Main.TASKBAR_HEIGHT - BAR_HEIGHT));
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
