package eu.jozoproductions.panels;

import eu.jozoproductions.Cached;
import eu.jozoproductions.FileSystem;
import eu.jozoproductions.Main;
import eu.jozoproductions.audio.AudioManager;
import eu.jozoproductions.firebase.DS_GameVersion;
import eu.jozoproductions.tasks.DownloadSOWTask;
import eu.jozoproductions.ui.*;
import eu.jozoproductions.ui.TextField;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DownloadSOWPanel extends WizardPanel {

    //UI
    public ImageUI headerImage;
    public Text headerTitle;

    public SelectBox majorVersionSelect, minorVersionSelect;
    public TextField downloadFolder;
    public ImageUI browseDownloadFolder;
    public CheckBox createDesktopShortcut;

    public Text selectedVersion, changelog;

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
            majorVersionSelect = new SelectBox(this, new String[]{}, 10, 84, 100, 20, "Major");
            minorVersionSelect = new SelectBox(this, new String[]{}, 120, 84, 100, 20, "Minor");

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

            //Texts
            selectedVersion = new Text(this, 10, 220, Main.WIN_WIDTH, 40, "");
            selectedVersion.setFontProps(17, Font.BOLD);
            changelog = new Text(this, 10, 270, Main.WIN_WIDTH-20, 400, "");

            //Action buttons
            backBtn = new ImageUI(this, Main.WIN_WIDTH-230, Main.WIN_HEIGHT-WindowBar.BAR_HEIGHT - 40, 105, 30, "back", true, true, true);
            startBtn = new ImageUI(this, Main.WIN_WIDTH-115, Main.WIN_HEIGHT-WindowBar.BAR_HEIGHT - 40, 105, 30, "start", true, true, true);

            //Listeners
            backBtn.setClickCallback(() -> {
                WizardPanel.ChangePanels(DownloadSOWPanel.this, Main.welcomePanel, SwipeSide.RIGHT);
            });

            startBtn.setClickCallback(() -> Main.confirmDLG.showDialog("Start installation", "State of War: Annihilation will be installed in:</br> " + downloadFolder.getText(), () -> {
                int selectedKey = getMajorSelectedKey();
                downloadTask = new DownloadSOWTask(Cached.gameVersions.get(selectedKey).get(minorVersionSelect.getSelectedIndex()), downloadFolder.getText(), createDesktopShortcut.isSelected());
                downloadTask.start();

                WizardPanel.ChangePanels(DownloadSOWPanel.this, Main.installingSOWPanel, SwipeSide.LEFT);
            }));

            majorVersionSelect.addActionListener(e -> {
                //Update version select box model
                int selectedKey = getMajorSelectedKey();
                String[] items = new String[Cached.gameVersions.get(selectedKey).size()];
                for (int i = 0; i < items.length; i++) {
                    items[i] = Cached.gameVersions.get(selectedKey).get(i).minor + "";
                }
                minorVersionSelect.setModel(new DefaultComboBoxModel(items));
                minorVersionSelect.setSelectedIndex(0);
            });

            minorVersionSelect.addActionListener(e -> {
                //Update texts
                DS_GameVersion dg = Cached.gameVersions.get(getMajorSelectedKey()).get(minorVersionSelect.getSelectedIndex());
                selectedVersion.setText("Selected: " + dg.title + " (" + dg.build + ")");
                dg.changelog = dg.changelog.replaceAll("\\\\n", "<br>");
                changelog.setText("<html>" + dg.changelog + "</html>");
            });

            //Title
            headerTitle = new Text(this, 84, 10, Main.WIN_WIDTH, 64, "Download State of War: Annihilation");
            headerTitle.setFont(FontManager.exoLightFont, Font.BOLD, 25);
            headerTitle.setVerticalAlignment(SwingConstants.CENTER);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Integer getMajorSelectedKey() {
        int keysize = Cached.gameVersions.keySet().size();
        return (Integer) Cached.gameVersions.keySet().toArray()[keysize - 1 - majorVersionSelect.getSelectedIndex()];
    }
}
