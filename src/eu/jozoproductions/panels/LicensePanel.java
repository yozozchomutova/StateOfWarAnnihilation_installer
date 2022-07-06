package eu.jozoproductions.panels;

import eu.jozoproductions.Main;
import eu.jozoproductions.ui.FontManager;
import eu.jozoproductions.ui.ImageUI;
import eu.jozoproductions.ui.Text;
import eu.jozoproductions.ui.WindowBar;

import javax.swing.*;
import java.awt.*;

public class LicensePanel extends WizardPanel {

    public ImageUI headerIcon;
    public Text headerTitle;
    public JScrollPane licenseAreaSP;
    public JTextArea licenseArea;
    public ImageUI exitBtn, agreeBtn;

    @Override
    public void OnUISetup() {
        //Header icon
        headerIcon = new ImageUI(this, 10, 10, 32, 32, "list", true, true, false);

        //Title
        headerTitle = new Text(this, 52, 16, Main.WIN_WIDTH, 32, "");
        headerTitle.setFont(FontManager.exoLightFont, Font.BOLD, 25);

        //Area
        licenseArea = new JTextArea();
        licenseArea.setBounds(10, 50 + WindowBar.BAR_HEIGHT, Main.WIN_WIDTH-20, Main.WIN_HEIGHT-WindowBar.BAR_HEIGHT - 100);
        licenseArea.setBackground(Color.BLACK);
        licenseArea.setForeground(Color.WHITE);
        licenseArea.setLineWrap(true);
        licenseArea.setWrapStyleWord(true);
        licenseArea.setEditable(false);

        licenseAreaSP = new JScrollPane(licenseArea);
        licenseAreaSP.setBounds(licenseArea.getBounds());
        add(licenseAreaSP);

        //Agree button
        agreeBtn = new ImageUI(this, Main.WIN_WIDTH-115, Main.WIN_HEIGHT- WindowBar.BAR_HEIGHT - 40, 105, 30, "agree", true, true, true);
        agreeBtn.setClickCallback(new ImageUI.ClickCallback() {
            @Override
            public void OnClick() {
                WizardPanel.ChangePanels(LicensePanel.this, Main.welcomePanel, SwipeSide.RIGHT);
            }
        });
    }

    public void setValues(String title, String text) {
        headerTitle.setText(title);
        licenseArea.setText(text);
    }
}
