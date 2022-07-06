package eu.jozoproductions.panels;

import eu.jozoproductions.Cached;
import eu.jozoproductions.FileSystem;
import eu.jozoproductions.Main;
import eu.jozoproductions.audio.AudioManager;
import eu.jozoproductions.tasks.DownloadSOWTask;
import eu.jozoproductions.ui.*;
import eu.jozoproductions.ui.TextField;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

public class DownloadSOWPanel extends WizardPanel {

    //UI
    public ImageUI headerImage;
    public Text headerTitle;

    public SelectBox versionSelect;
    public TextField downloadFolder;
    public ImageUI browseDownloadFolder;
    public CheckBox createDesktopShortcut;

    public ImageUI backBtn, startBtn;

    //Thread
    public DownloadSOWTask downloadTask;

    @Override
    public void OnUISetup() {
        try {
            setLayout(null);
            setBackground(Color.DARK_GRAY);

            //-<UI>-

            //Header image
            headerImage = new ImageUI(this, 10, 10, 64, 64, "download_icon", true, true, false);

            //Version select
            versionSelect = new SelectBox(this, new String[]{}, 10, 84, 250, 20, "Version");

            //Download folder
            downloadFolder = new TextField(this, 10, 134, 250, 20, "Installation path", FileSystemView.getFileSystemView().getDefaultDirectory().getPath());
            browseDownloadFolder = new ImageUI(this, 270, 149, 60, 20, "browse", false, true, true);
            browseDownloadFolder.setClickCallback(() -> {
                File selectedFolder = FileSystem.selectFolder(Main.frame);
                if (selectedFolder != null)
                    downloadFolder.setText(selectedFolder.getPath());
            });

            //Create desktop shortcut
            createDesktopShortcut = new CheckBox(this, 10, 184, 200, 20, "Create desktop shortcut");
            createDesktopShortcut.setSelected(true);

            //Action buttons
            backBtn = new ImageUI(this, Main.WIN_WIDTH-230, Main.WIN_HEIGHT-WindowBar.BAR_HEIGHT - 40, 105, 30, "back", true, true, true);
            startBtn = new ImageUI(this, Main.WIN_WIDTH-115, Main.WIN_HEIGHT-WindowBar.BAR_HEIGHT - 40, 105, 30, "start", true, true, true);

            //Listeners
            backBtn.setClickCallback(() -> {
                WizardPanel.ChangePanels(DownloadSOWPanel.this, Main.welcomePanel, SwipeSide.RIGHT);
            });

            startBtn.setClickCallback(() -> Main.confirmDLG.showDialog("Start installation", "State of War: Annihilation will be installed in:</br> " + downloadFolder.getText(), () -> {
                downloadTask = new DownloadSOWTask(Cached.gameVersions.get(versionSelect.getSelectedIndex()), downloadFolder.getText(), createDesktopShortcut.isSelected());
                downloadTask.start();

                WizardPanel.ChangePanels(DownloadSOWPanel.this, Main.installingSOWPanel, SwipeSide.LEFT);
            }));

            //Title
            headerTitle = new Text(this, 84, 10, Main.WIN_WIDTH, 64, "Download State of War: Annihilation");
            headerTitle.setFont(FontManager.exoLightFont, Font.BOLD, 25);
            headerTitle.setVerticalAlignment(SwingConstants.CENTER);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
