package eu.jozoproductions.dialogwindow;

import eu.jozoproductions.Main;
import eu.jozoproductions.ui.ImageUI;
import eu.jozoproductions.ui.WindowBar;

import javax.swing.*;
import java.awt.*;

public abstract class DialogWindow extends JDialog {

    public WindowBar windowBar;

    private ImageUI background;

    public DialogWindow(JFrame owner, int width, int height, boolean closeAble, String titleText) {
        super(owner, ModalityType.MODELESS);

        setBounds((int) (Main.frame.getWidth()/2f-width/2f), (int) (Main.frame.getHeight()/2f-height/2f), width, height);
        setLayout(null);
        setUndecorated(true);

        //Setup window bar
        windowBar = new WindowBar(this, this, width, true, closeAble, titleText);

        //Other UIs
        setupUI();

        //Background
        background = new ImageUI(this, 0, 0, width,height, "bcg2", true, true, false);
    }

    public abstract void setupUI();
}
