package eu.jozoproductions.panels;

import eu.jozoproductions.InstallManager;
import eu.jozoproductions.Main;
import eu.jozoproductions.ui.*;

import javax.swing.*;
import java.awt.*;

public class InstallingSOWPanel extends WizardPanel {

    //UI
    public Text headerTitle;

    public Text textPercentages;

    public TaskText task_prepareFiles;
    public TaskText task_downloadGame;
    public TaskText task_installGame;
    public TaskText task_signing;

    public ImageUI rightTab_bcg;
    public ImageUI bottomTab_bcg;

    public ImageUI cancelBtn, pauseDownload, resumeDownload, finishBtn;

    @Override
    public void OnUISetup() {
        try {
            setLayout(null);
            setBackground(Color.DARK_GRAY);

            //-<UI>-
            textPercentages = new Text(this, Main.WIN_WIDTH-203+10, 10, 203, 203, "100%");
            textPercentages.setFont(FontManager.alfphabetIV);
            textPercentages.setFontSize(50);
            textPercentages.setVerticalAlignment(SwingConstants.CENTER); textPercentages.setHorizontalAlignment(SwingConstants.CENTER);

            task_prepareFiles = new TaskText(this, 405, 205, 190, 50, "Preparation");
            task_downloadGame = new TaskText(this, 405, 255, 190, 50, "Download State of War: Anni");
            task_installGame = new TaskText(this, 405, 305, 190, 50, "Install State of War: Anni");
            task_signing = new TaskText(this, 405, 355, 190, 50, "Signing");

            //Action buttons
            cancelBtn = new ImageUI(this, 10, Main.WIN_HEIGHT-WindowBar.BAR_HEIGHT - 40, 105, 30, "cancel", true, true, true);
            cancelBtn.setClickCallback(() -> {
                Main.confirmDLG.showDialog("Cancel installation", "Are you sure to cancel installation? This action cannot be reverted!", new ImageUI.ClickCallback() {
                    @Override
                    public void OnClick() {
                        Main.confirmDLG.showDialog("Cancel installation", "Are you sure to cancel and terminate installation?", new ImageUI.ClickCallback() {
                            @Override
                            public void OnClick() {
                                Main.downloadSOWPanel.downloadTask.interrupt();
                                WizardPanel.ChangePanels(InstallingSOWPanel.this, Main.downloadSOWPanel, SwipeSide.RIGHT);
                            }
                        });
                    }
                });
            });

            pauseDownload = new ImageUI(this, Main.WIN_WIDTH-230-203, Main.WIN_HEIGHT-WindowBar.BAR_HEIGHT - 40, 105, 30, "pause", true, true, true);
            pauseDownload.setClickCallback(() -> {
                pauseDownload.setVisible(false);
                resumeDownload.setVisible(true);
                Main.downloadSOWPanel.downloadTask.suspend();
            });

            resumeDownload = new ImageUI(this, Main.WIN_WIDTH-230-203, Main.WIN_HEIGHT-WindowBar.BAR_HEIGHT - 40, 105, 30, "resume", true, false, true);
            resumeDownload.setClickCallback(() -> {
                pauseDownload.setVisible(true);
                resumeDownload.setVisible(false);
                Main.downloadSOWPanel.downloadTask.resume();
            });

            finishBtn = new ImageUI(this, Main.WIN_WIDTH-115-203, Main.WIN_HEIGHT-WindowBar.BAR_HEIGHT - 40, 105, 30, "finish", true, true, true);
            finishBtn.setEnabled(false);
            finishBtn.setClickCallback(() -> {
                WizardPanel.ChangePanels(InstallingSOWPanel.this, Main.welcomePanel, SwipeSide.LEFT);
            });

            //Right tab
            rightTab_bcg = new ImageUI(this, Main.WIN_WIDTH-203, 0, 203, Main.WIN_HEIGHT, "bcg3", true, true, false);
            rightTab_bcg.setDrawCallback(this::OnRightDrawRender);

            //Bottom tab
            bottomTab_bcg = new ImageUI(this, 0, Main.WIN_HEIGHT-WindowBar.BAR_HEIGHT-60, Main.WIN_WIDTH, 60, "bcg4", true, true, false);

            //Title
            headerTitle = new Text(this, 10, 10, Main.WIN_WIDTH, 64, "Installing...");
            headerTitle.setFont(FontManager.exoLightFont, Font.BOLD, 25);
            headerTitle.setVerticalAlignment(SwingConstants.CENTER);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Task progress bar

    private void OnRightDrawRender(Graphics2D g) {
        textPercentages.setText((int)InstallManager.downloadedPercentage + "%");

        g.setStroke(new BasicStroke(10));

        g.setColor(Color.DARK_GRAY);
        g.drawArc(15, 15, 180, 180, 90, 360);

        g.setColor(Color.CYAN);
        g.drawArc(15, 15, 180, 180, 90, (int)(-InstallManager.downloadedPercentage * 3.6f));
    }
}
