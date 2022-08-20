package eu.jozoproductions.tasks;

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

public final class DownloadSOWTask extends Thread {

    private DS_GameVersion gameVersion;
    private String folderPath;
    private boolean createDesktopShortcut;

    public DownloadSOWTask(DS_GameVersion gameVersion, String folderPath, boolean createDesktopShortcut) {
        this.gameVersion = gameVersion;
        this.folderPath = folderPath;
        this.createDesktopShortcut = createDesktopShortcut;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1500); //*intense preparation*

            //1. Make folders
            goNextTask(null, Main.installingSOWPanel.task_prepareFiles);

            File gameFolder = new File(folderPath + "/State of War Annihilation " + gameVersion.title);
            File gameZip = new File(gameFolder.getPath() + "/_game.zip");

            gameFolder.mkdirs();

            //2. Download State of War: Annihilation zip file
            goNextTask(Main.installingSOWPanel.task_prepareFiles, Main.installingSOWPanel.task_downloadGame);
            InstallManager.copyURLToFile(new URL(gameVersion.download), gameZip);

            //3. Extract game zip file
            goNextTask(Main.installingSOWPanel.task_downloadGame, Main.installingSOWPanel.task_installGame);
            InstallManager.extract(new ZipInputStream(new FileInputStream(gameZip)), gameFolder);

            //4. Extract & install game
            goNextTask(Main.installingSOWPanel.task_installGame, Main.installingSOWPanel.task_signing);

            if (createDesktopShortcut) {
                File gameExecutable = new File(gameFolder.getPath() + "/" + Main.GAME_NAME + ".exe");
                ShellLink.createLink(gameExecutable.getPath(), FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/State of War Annihilation.lnk");
            }

            //5. Cleanup zip file
            gameZip.delete();

            //End
            goNextTask(Main.installingSOWPanel.task_signing, null);

            Main.installingSOWPanel.finishBtn.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(Main.frame, "Error", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goNextTask(TaskText previousTask, TaskText nextTask) throws Exception {
        Thread.sleep(500); //*intense preparation*
        if (previousTask!= null) previousTask.completed();
        AudioManager.playAudio("task_done");
        if (nextTask!= null) nextTask.inProgress();
        Thread.sleep(500); //*intense preparation*
    }
}
