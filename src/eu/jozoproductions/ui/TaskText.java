package eu.jozoproductions.ui;

import java.awt.*;

public class TaskText {

    //Animation sources
    public static AnimatedImage.AnimationSource redDot, yellowDot, greenDot;

    public AnimatedImage icon;
    public Text text;

    public TaskText(Container container, int x, int y, int width, int height, String defaulText) {
        icon = new AnimatedImage(container, x, y, 24, 24, redDot, 0, true);
        text = new Text(container, x+27, y+5, width-27, height, "<html>" + defaulText + "</html>");
        text.setFont(FontManager.exoLightFont);
        text.setFontSize(16);
    }

    public void inProgress() {
        icon.changeAnimSource(yellowDot, false);
    }

    public void completed() {
        icon.changeAnimSource(greenDot, false);
    }
}
