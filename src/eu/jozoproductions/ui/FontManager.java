package eu.jozoproductions.ui;

import eu.jozoproductions.Main;

import java.awt.*;

public class FontManager {

    public static Font exoLightFont;
    public static Font alfphabetIV;

    public static void init(Main main) {
        try {
            //Load fonts
            exoLightFont = Font.createFont(Font.TRUETYPE_FONT, main.getClass().getResourceAsStream("/Exo-Light.otf"));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(exoLightFont);

            alfphabetIV = Font.createFont(Font.TRUETYPE_FONT, main.getClass().getResourceAsStream("/Alfphabet-IV.ttf"));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(alfphabetIV);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
