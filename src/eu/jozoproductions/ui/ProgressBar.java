package eu.jozoproductions.ui;

import javax.swing.*;
import java.awt.*;

public class ProgressBar extends JProgressBar {

    public JLabel title;

    public ProgressBar(Container container, int x, int y, int width, int height, String titleText) {
        super();

        setBounds(x, y + (titleText != null ? 13 : 0), width, height);
        setForeground(Color.BLUE);
        setBackground(Color.WHITE);
        setMaximum(100);
        setMinimum(0);
        setValue(0);

        if (titleText != null) {
            title = new JLabel(titleText);
            title.setVerticalAlignment(SwingConstants.TOP);
            title.setForeground(Color.WHITE);
            title.setBounds(x, y, width, 20);
            title.setFont(new Font("Arial", Font.PLAIN, 12));

            container.add(title);
        }

        container.add(this);
    }
}
