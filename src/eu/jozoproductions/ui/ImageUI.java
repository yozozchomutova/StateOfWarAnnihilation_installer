package eu.jozoproductions.ui;

import eu.jozoproductions.EndlessThread;
import eu.jozoproductions.audio.AudioManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ImageUI extends JLabel implements MouseListener {

    private ImageIcon imgOff, imgOn;
    private ClickCallback clickCallback;
    private DrawCallback drawCallback;
    public boolean mouseHovering = false;

    //Rendering
    public float offAlpha = 1f;

    //Classic images
    public ImageUI(Container container, int x, int y, int width, int height, String imgPath, boolean scale, boolean visible, boolean changingTextures) {
        super();

        if (changingTextures) {
            this.imgOff = new ImageIcon(getClass().getResource("/" + imgPath + "_off.png"));
            this.imgOn = new ImageIcon(getClass().getResource("/" + imgPath + "_on.png"));
            addMouseListener(this);
            EndlessThread.imageBtns.add(this);
        } else {
            this.imgOff = new ImageIcon(getClass().getResource("/" + imgPath + ".png"));
            this.imgOn = new ImageIcon(getClass().getResource("/" + imgPath + ".png"));
        }

        if (scale) {
            this.imgOff = new ImageIcon(this.imgOff.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            this.imgOn = new ImageIcon(this.imgOn.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        }

        setBounds(x, y + WindowBar.BAR_HEIGHT, width, height);
        setVerticalAlignment(TOP);

        setVisible(visible);
        container.add(this);
    }

    public void setClickCallback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    public void setDrawCallback(DrawCallback drawCallback) {
        this.drawCallback = drawCallback;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (isEnabled()) {
            mouseHovering = true;
            AudioManager.playAudio("highlight");
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseHovering = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        //Draw
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, isEnabled() ? offAlpha : 0.5f));
        g2d.drawImage(imgOff.getImage(), 0, 0, getWidth(), getHeight(), null);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, isEnabled() ? (1f - offAlpha) : 0f));
        g2d.drawImage(imgOn.getImage(), 0, 0, getWidth(), getHeight(), null);

        if (drawCallback != null) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            drawCallback.OnDraw(g2d);
        }

        repaint();
    }

    @Override public void mouseClicked(MouseEvent e) {
        if (clickCallback != null && isEnabled()) {
            clickCallback.OnClick();
            AudioManager.playAudio("click");
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}

    public static interface ClickCallback {
        void OnClick();
    }

    public static interface DrawCallback {
        void OnDraw(Graphics2D g);
    }
}