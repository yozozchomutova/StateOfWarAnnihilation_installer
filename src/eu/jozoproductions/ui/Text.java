package eu.jozoproductions.ui;

import javax.swing.*;
import java.awt.*;

public class Text extends JLabel {

    public Text(Container container, int x, int y, int width, int height, String defaultValue) {
        super(defaultValue);

        setBounds(x, y + WindowBar.BAR_HEIGHT, width, height);
        setVerticalAlignment(SwingConstants.TOP);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.PLAIN, 15));

        container.add(this);
    }

    public void setFontSize(int size) {
        setFontProps(size, getFont().getStyle());
    }

    public void setFontStyle(int style) {
        setFontProps(getFont().getSize(), style);
    }

    public void setFontProps(int size, int style) {
        setFont(getFont().deriveFont(style, size));
    }

    public void setFont(Font font, int style, int size) {
        setFont(font);
        setFontProps(size, style);
    }
}
