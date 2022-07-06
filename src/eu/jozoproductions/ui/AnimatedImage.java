package eu.jozoproductions.ui;

import eu.jozoproductions.EndlessThread;
import eu.jozoproductions.audio.AudioManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AnimatedImage extends JLabel {

    private AnimationSource animationSource;

    public int delaying;
    public int curDelaying;

    public int maxFrames;
    public int currentFrame;

    //Classic images
    public AnimatedImage(Container container, int x, int y, int width, int height, AnimationSource animSource, int delaying, boolean visible) {
        super();

        this.animationSource = animSource;

        this.delaying = delaying;
        curDelaying = 0;

        maxFrames = animSource.images.length;
        currentFrame = 0;

        setBounds(x, y + WindowBar.BAR_HEIGHT, width, height);
        setVerticalAlignment(TOP);

        setVisible(visible);

        EndlessThread.animatedImages.add(this);
        container.add(this);
    }

    public void changeAnimSource(AnimationSource animationSource, boolean resetFrames) {
        this.animationSource = animationSource;

        if (resetFrames) {
            curDelaying = 0;
            currentFrame = 0;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        //Draw
        g2d.drawImage(animationSource.images[currentFrame].getImage(), 0, 0, getWidth(), getHeight(), null);

        repaint();
    }

    public static class AnimationSource {

        private ImageIcon[] images;

        public AnimationSource(String folder, int imgCount) {
            images = new ImageIcon[imgCount];
            for (int i = 1; i < imgCount+1; i++) {
                int thousands = (int)((i) / 1000f);
                int hundreds = (int)((i - thousands * 1000) / 100f);
                int tens = (int)((i - thousands * 1000 - hundreds * 100) / 10f);
                int ones = (i - thousands * 1000 - hundreds * 100 - tens * 10);

                String fileName = thousands + "" + hundreds + "" + tens + "" + ones;

                images[i-1] = new ImageIcon(getClass().getResource("/" + folder + "/" + fileName + ".png"));
            }
        }

        public static void init() {
            //Animation sources
            TaskText.redDot = new AnimatedImage.AnimationSource("red_dot", 100);
            TaskText.yellowDot = new AnimatedImage.AnimationSource("yellow_dot", 100);
            TaskText.greenDot = new AnimatedImage.AnimationSource("green_dot", 100);
        }
    }
}