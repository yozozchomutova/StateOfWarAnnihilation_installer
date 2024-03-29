package eu.jozoproductions.ui;

import javax.swing.*;
import java.awt.*;

public class TextField extends JTextField {

    public JLabel title;

    public TextField(Container container, int x, int y, int width, int height, String titleText) {
        this(container, x, y, width, height, titleText, "");
    }

    public TextField(Container container, int x, int y, int width, int height, String titleText, String defaultValue) {
        super();

        setBounds(x, y + (titleText != null ? 13 : 0) + WindowBar.BAR_HEIGHT, width, height);
        setForeground(Color.WHITE);
        setBackground(Color.BLACK);
        setCaretColor(Color.WHITE);

        setText(defaultValue);

        if (titleText != null) {
            title = new JLabel(titleText);
            title.setVerticalAlignment(SwingConstants.TOP);
            title.setForeground(Color.WHITE);
            title.setBounds(x, y + WindowBar.BAR_HEIGHT, width, 20);
            title.setFont(new Font("Arial", Font.PLAIN, 12));

            container.add(title);
        }

        container.add(this);
    }
}
