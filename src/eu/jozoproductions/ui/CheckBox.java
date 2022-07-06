package eu.jozoproductions.ui;

import eu.jozoproductions.audio.AudioManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CheckBox extends JCheckBox implements MouseListener {

    public boolean mouseHovering = false;

    //Rendering
    public float offAlpha = 1f;

    public CheckBox(Container container, int x, int y, int width, int height, String titleText) {
        this(container, x, y, width, height, "cb1", titleText);
    }

    public CheckBox(Container container, int x, int y, int width, int height, String imgPath, String titleText) {
        super(titleText);

        setBounds(x, y + WindowBar.BAR_HEIGHT, width, height);

        setForeground(Color.WHITE);
        setBackground(new Color(0, 0, 0, 0));

        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setBorder(null);

        setIcon(new ImageIcon(getClass().getResource("/"  + imgPath + "_off.png")));
        setRolloverIcon(new ImageIcon(getClass().getResource("/"  + imgPath + "_on.png")));

        setSelectedIcon(new ImageIcon(getClass().getResource("/"  + imgPath + "_sel_off.png")));
        setRolloverSelectedIcon(new ImageIcon(getClass().getResource("/"  + imgPath + "_sel_on.png")));

        addMouseListener(this);

        container.add(this);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseHovering = true;
        AudioManager.playAudio("highlight");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseHovering = false;
    }

    /*@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        //Draw
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, offAlpha));
        g2d.drawImage(imgOff.getImage(), 0, 0, getWidth(), getHeight(), null);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f - offAlpha));
        g2d.drawImage(imgOn.getImage(), 0, 0, getWidth(), getHeight(), null);

        repaint();
    }*/

    @Override public void mouseClicked(MouseEvent e) {
        AudioManager.playAudio("click");

        /*if (clickCallback != null) {
            clickCallback.OnClick();
            AudioManager.playAudio("click");
        }*/
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
}
