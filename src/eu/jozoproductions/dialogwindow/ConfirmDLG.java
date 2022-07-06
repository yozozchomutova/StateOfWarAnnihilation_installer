package eu.jozoproductions.dialogwindow;

import eu.jozoproductions.Main;
import eu.jozoproductions.ui.FontManager;
import eu.jozoproductions.ui.ImageUI;
import eu.jozoproductions.ui.Text;

import javax.swing.*;

public class ConfirmDLG extends DialogWindow {

    private ImageUI noButton, yesButton;
    private Text descriptionText;

    //Callbacks
    private ImageUI.ClickCallback yesCallback;

    public ConfirmDLG(JFrame owner) {
        super(owner, 400, 150, false, "Confirm");
    }

    public void showDialog(String title, String desc, ImageUI.ClickCallback yesCallback) {
        this.yesCallback = yesCallback;

        windowBar.windowTItleBar.setText(title);
        descriptionText.setText("<html>" + desc + "</html>");
        setLocation((int)(Main.SCREEN_WIDTH/2f) - (int)(getWidth()/2f), (int)(Main.SCREEN_HEIGHT/2f) - (int)(getHeight()/2f));

        Main.loadingPanel.showLoading();
        setVisible(true);
    }

    @Override
    public void setupUI() {
        descriptionText = new Text(this, 5, 0, 400, 150, "");
        descriptionText.setFont(FontManager.exoLightFont);
        descriptionText.setFontSize(14);

        noButton = new ImageUI(this, 10, 90, 105, 30, "no",true, true, true);
        noButton.setClickCallback(() -> {
            setVisible(false);
            Main.loadingPanel.hideLoading();
        });

        yesButton = new ImageUI(this, 285, 90, 105, 30, "yes",true, true, true);
        yesButton.setClickCallback(() -> {
            yesCallback.OnClick();
            setVisible(false);
            Main.loadingPanel.hideLoading();
        });
    }
}
