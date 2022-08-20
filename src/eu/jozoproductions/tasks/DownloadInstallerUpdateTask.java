package eu.jozoproductions.tasks;

import eu.jozoproductions.Cached;
import eu.jozoproductions.InstallManager;
import eu.jozoproductions.Main;
import eu.jozoproductions.audio.AudioManager;
import eu.jozoproductions.firebase.DS_GameVersion;
import eu.jozoproductions.ui.TaskText;
import mslinks.ShellLink;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.zip.ZipInputStream;

public final class DownloadInstallerUpdateTask extends Thread {

    private String folderPath;

    public DownloadInstallerUpdateTask(String folderPath) {
        this.folderPath = folderPath;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(500); //*intense preparation*

            //1. Prepare installer location
            File newInstaller = new File(folderPath + "/" + "SOW-Anni-Installer " + Cached.insttalerVersion.versionCode + ".exe");

            //2. Download new installer
            InstallManager.copyURLToFile(new URL(Cached.insttalerVersion.downloadUrl), newInstaller);

            //Done... End!
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(Main.frame, "Error", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
}
