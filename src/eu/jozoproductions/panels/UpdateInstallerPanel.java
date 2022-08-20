package eu.jozoproductions.panels;

import eu.jozoproductions.Cached;
import eu.jozoproductions.FileSystem;
import eu.jozoproductions.Main;
import eu.jozoproductions.tasks.DownloadInstallerUpdateTask;
import eu.jozoproductions.tasks.DownloadSOWTask;
import eu.jozoproductions.ui.TextField;
import eu.jozoproductions.ui.*;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

public class UpdateInstallerPanel extends WizardPanel {

    //UI
    public ImageUI headerImage;
    public Text headerTitle;

    public TextField downloadFolder;
    public ImageUI browseDownloadFolder;

    public ImageUI noBtn, yesBtn;

    //Thread
    public DownloadInstallerUpdateTask downloadTask;

    @Override
    public void OnUISetup() {
        try {
            setLayout(null);
            setBackground(Color.DARK_GRAY);

            //-<UI>-

            //Header image
            headerImage = new ImageUI(this, 10, 10, 64, 64, "download_icon", true, true, false);

            //Download folder
            downloadFolder = new TextField(this, 10, 134, 250, 20, "Installation path", FileSystemView.getFileSystemView().getHomeDirectory().getPath());
            browseDownloadFolder = new ImageUI(this, 270, 149, 60, 20, "browse", false, true, true);
            browseDownloadFolder.setClickCallback(() -> {
                File selectedFolder = FileSystem.selectFolder(Main.frame);
                if (selectedFolder != null)
                    downloadFolder.setText(selectedFolder.getPath());
            });

            //Action buttons
            noBtn = new ImageUI(this, Main.WIN_WIDTH-230, Main.WIN_HEIGHT-WindowBar.BAR_HEIGHT - 40, 105, 30, "no", true, true, true);
            yesBtn = new ImageUI(this, Main.WIN_WIDTH-115, Main.WIN_HEIGHT-WindowBar.BAR_HEIGHT - 40, 105, 30, "yes", true, true, true);

            //Listeners
            noBtn.setClickCallback(() -> {
                WizardPanel.ChangePanels(UpdateInstallerPanel.this, Main.welcomePanel, SwipeSide.RIGHT);
            });

            yesBtn.setClickCallback(() -> Main.confirmDLG.showDialog("Start Updating", "Delete old Installer after installing new one.<br>New Installer will be in: </br> " + downloadFolder.getText(), () -> {
                downloadTask = new DownloadInstallerUpdateTask(downloadFolder.getText());
                downloadTask.start();

                Main.loadingPanel.showLoading();
            }));

            //Title
            headerTitle = new Text(this, 84, 10, Main.WIN_WIDTH, 64, "Installer Update");
            headerTitle.setFont(FontManager.exoLightFont, Font.BOLD, 25);
            headerTitle.setVerticalAlignment(SwingConstants.CENTER);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
