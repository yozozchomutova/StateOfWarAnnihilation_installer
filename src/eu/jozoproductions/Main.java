package eu.jozoproductions;

import eu.jozoproductions.audio.AudioManager;
import eu.jozoproductions.dialogwindow.ConfirmDLG;
import eu.jozoproductions.firebase.FirebaseManager;
import eu.jozoproductions.panels.*;
import eu.jozoproductions.toppanels.BackgroundPanel;
import eu.jozoproductions.toppanels.LoadingPanel;
import eu.jozoproductions.ui.AnimatedImage;
import eu.jozoproductions.ui.FontManager;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main implements MouseListener {

    public static void main(String[] args) {
        new Main(); //Launch
    }

    public static final String GAME_NAME = "StateOfWarAnnihilation";
    public static final String COMPANY_NAME = "JozoProductions";

    public static final String APP_NAME = "SOW: Annihilation Installer";
    public static final int VERSION_CODE = 3;
    public static final String TITLE = APP_NAME + " " + VERSION_CODE;

    public static final String CONTENT_INFO_URL = "https://raw.githubusercontent.com/yozozchomutova/StateOfWarAnnihilation_installer/main/content_info.txt";
    public static final String INSTALLERS_INFO_URL = "https://raw.githubusercontent.com/yozozchomutova/StateOfWarAnnihilation_installer/main/installers_info";

    public static final int WIN_WIDTH = 600;
    public static final int WIN_HEIGHT = 600;

    public static JFrame frame;
    public static BackgroundPanel bcgPanel;
    public static LoadingPanel loadingPanel;

    //Switching panels
    public static ArrayList<WizardPanel> allPanels = new ArrayList<>();
    public static WizardPanel currentPanel;

    public static WelcomePanel welcomePanel;
    public static LicensePanel licensePanel;
    public static DownloadSOWPanel downloadSOWPanel;
    public static UninstallPanel uninstallPanel;
    public static InstallingSOWPanel installingSOWPanel;
    public static UpdateInstallerPanel updateInstallerPanel;

    //Dialogs
    public static ConfirmDLG confirmDLG;

    public static int SCREEN_WIDTH, SCREEN_HEIGHT, TASKBAR_HEIGHT;

    //Mouse
    public static int lastMouseX;
    public static int lastMouseY;

    Main() {
        //Get screen size & taskbar size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        SCREEN_WIDTH = screenSize.width;
        SCREEN_HEIGHT = screenSize.height;

        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        TASKBAR_HEIGHT = screenSize.height - winSize.height;

        //Init audio
        AudioManager.init(this);
        AnimatedImage.AnimationSource.init();
        FontManager.init(this);

        //Init panel
        frame = new JFrame(TITLE);
        bcgPanel = new BackgroundPanel();
        loadingPanel = new LoadingPanel();

        //Init panels
        welcomePanel = new WelcomePanel();
        licensePanel = new LicensePanel();
        downloadSOWPanel = new DownloadSOWPanel();
        uninstallPanel = new UninstallPanel();
        installingSOWPanel = new InstallingSOWPanel();
        updateInstallerPanel = new UpdateInstallerPanel();

        //Init dialogs
        confirmDLG = new ConfirmDLG(frame);

        //Load first panel
        Main.currentPanel = Main.welcomePanel;

        //Init frame
        frame.setSize(WIN_WIDTH, WIN_HEIGHT);
        frame.setLocation((int) (SCREEN_WIDTH/2f - frame.getWidth()/2f), (int) (SCREEN_HEIGHT/2f - frame.getHeight()/2f));
        frame.setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(bcgPanel);
        frame.setVisible(true);

        //Init endless thread
        EndlessThread et = new EndlessThread();
        et.start();

        //Listeners
        bcgPanel.addMouseListener(this);

        //Check for installer update
        try {
            String contentInfoText = InstallManager.readURLText(INSTALLERS_INFO_URL);
            String updateURL = FirebaseManager.scanInstallersInfo(contentInfoText);

            if (!updateURL.isEmpty()) {
                updateInstallerPanel.headerTitle.setText("Installer Update: " + Cached.insttalerVersion.versionCode);
                WizardPanel.ChangePanels(welcomePanel, updateInstallerPanel, WizardPanel.SwipeSide.LEFT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Finished
        LoadingPanel.loadingBackground.setVisible(false);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastMouseX = e.getX();
        lastMouseY = e.getY();
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    public static void shutdown() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
