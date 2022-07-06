package eu.jozoproductions;

import eu.jozoproductions.panels.WizardPanel;
import eu.jozoproductions.ui.AnimatedImage;
import eu.jozoproductions.ui.ImageUI;
import eu.jozoproductions.ui.WindowBar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

import static eu.jozoproductions.toppanels.LoadingPanel.loadingRotation;

public class EndlessThread extends Thread{

    public static ArrayList<ImageUI> imageBtns = new ArrayList<>();
    public static ArrayList<AnimatedImage> animatedImages = new ArrayList<>();

    @Override
    public void run() {
        //Get data
        LinkedList<WindowBar> windowBars = WindowBar.windowBars;

        while (true) {
            try { Thread.sleep(10); } catch (Exception e) {}
            PointerInfo mouseInfo = MouseInfo.getPointerInfo();

            int mouseX = 0, mouseY = 0;

            if (mouseInfo != null) {
                mouseX = mouseInfo.getLocation().x;
                mouseY = mouseInfo.getLocation().y;
            }

            //Image btns offset
            for (ImageUI img : imageBtns) {
                if (img.mouseHovering) {
                    img.offAlpha = Math.max(0, img.offAlpha - 0.1f);
                } else {
                    img.offAlpha = Math.min(1, img.offAlpha + 0.1f);
                }
            }

            //Aniamted images
            for (AnimatedImage image : animatedImages) {
                image.curDelaying++;

                if (image.curDelaying > image.delaying) { //Next frame
                    image.curDelaying = 0;

                    int next = image.currentFrame+1;
                    next = next >= image.maxFrames ? 0 : next;
                    image.currentFrame = next;
                }
            }

            //Move window bars
            for (WindowBar wb : windowBars) {
                if (wb.windowIsMoving) {
                    Point windowPoint = wb.window.getLocation();

                    int newX = windowPoint.x + (mouseX - Main.lastMouseX);
                    int newY = windowPoint.y + (mouseY - Main.lastMouseY);

                    wb.window.setLocation(newX, newY);
                    break;
                }
            }

            //Move panels
            for (WizardPanel panel : Main.allPanels) {
                if (panel == Main.currentPanel) {
                    panel.moveX((-panel.getX()) / 10f);
                } else {
                    panel.moveX((Main.WIN_WIDTH * panel.swipeSide.value - panel.getX()) / 10f);
                }
            }

            //Update loading icon rotation
            loadingRotation += 10f;
            if (loadingRotation >= 360) {
                loadingRotation -= 360;
            }

            Main.lastMouseX = mouseX;
            Main.lastMouseY = mouseY;
        }
    }
}
