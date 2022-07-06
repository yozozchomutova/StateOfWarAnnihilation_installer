package eu.jozoproductions.panels;

import eu.jozoproductions.FileSystem;
import eu.jozoproductions.Main;
import eu.jozoproductions.ui.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.rmi.registry.Registry;
import java.util.prefs.Preferences;

public class UninstallPanel extends WizardPanel {

    public ImageUI headerIcon;
    public Text headerTitle, descriptionText;
    public ImageUI backBtn, uninstallBtn;

    public CheckBox removeRegistry, removeDataFiles;

    @Override
    public void OnUISetup() {
        ///Header image
        headerIcon = new ImageUI(this, 10, 10, 64, 64, "dump", true, true, false);

        //Title
        headerTitle = new Text(this, 84, 10, Main.WIN_WIDTH, 64, "Are ");
        headerTitle.setFont(FontManager.exoLightFont, Font.BOLD, 25);
        headerTitle.setVerticalAlignment(SwingConstants.CENTER);

        descriptionText = new Text(this, 10, 84, Main.WIN_WIDTH-20, 400, "<html></html>");
        descriptionText.setFont(FontManager.exoLightFont, Font.PLAIN, 15);

        //Options
        removeRegistry = new CheckBox(this, 10, 130, 250, 20, "Remove preferences");
        removeRegistry.setSelected(true);

        removeDataFiles = new CheckBox(this, 10, 160, 250, 20, "Remove data files (Levels, saves,...)");
        removeDataFiles.setSelected(false);

        //Agree button
        backBtn = new ImageUI(this, Main.WIN_WIDTH-230, Main.WIN_HEIGHT- WindowBar.BAR_HEIGHT - 40, 105, 30, "back", true, true, true);
        backBtn.setClickCallback(new ImageUI.ClickCallback() {
            @Override
            public void OnClick() {
                WizardPanel.ChangePanels(UninstallPanel.this, Main.welcomePanel, SwipeSide.RIGHT);
            }
        });

        //Exit button
        uninstallBtn = new ImageUI(this, Main.WIN_WIDTH-115, Main.WIN_HEIGHT- WindowBar.BAR_HEIGHT - 40, 105, 30, "uninstall", true, true, true);
    }

    public void set_uninstallGame() {
        headerTitle.setText("Uninstall State of War: Annihilation");
        descriptionText.setText("<html><b>Are you sure to uninstall game?</b></br> This action cannot be undone!</br>Here you can only clear registry/data folders. Rest of game can be deleted manually.</html>");
        uninstallBtn.setClickCallback(new ImageUI.ClickCallback() {
            @Override
            public void OnClick() {
                //TODO Remove registry
                try {
                    if (removeRegistry.isSelected()) {
                        Runtime.getRuntime().exec("reg delete HKCU\\Software\\" + Main.COMPANY_NAME + "\\" + Main.GAME_NAME + " /f");
                    }

                    //TODO Remove data files in %appdata%
                    if (removeDataFiles.isSelected()) {
                        File appDataFolder = new File(System.getenv("APPDATA"));

                        if (!appDataFolder.getName().toLowerCase().equals("appdata")) {
                            appDataFolder = appDataFolder.getParentFile();
                        }

                        if (appDataFolder.getName().toLowerCase().equals("appdata")) {
                            File gameDataFolder = new File(appDataFolder.getPath() + "/LocalLow/" + Main.COMPANY_NAME + "/" + Main.GAME_NAME + "/");

                            if (gameDataFolder.exists()) {
                                FileSystem.deleteDir(gameDataFolder);
                            } else {
                                JOptionPane.showMessageDialog(Main.frame, "Error", "Game data folder not found! " + (appDataFolder.getPath() + "/LocalLow/" + Main.COMPANY_NAME + "/" + Main.GAME_NAME + "/"), JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        } else {
                            JOptionPane.showMessageDialog(Main.frame, "Error", "AppData not found! " + appDataFolder.getAbsolutePath(), JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    WizardPanel.ChangePanels(Main.uninstallPanel, Main.welcomePanel, SwipeSide.RIGHT);
                    JOptionPane.showMessageDialog(Main.frame, "Success", "Success!", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(Main.frame, "Error", e.getMessage(), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
