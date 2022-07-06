package eu.jozoproductions.firebase;

import eu.jozoproductions.Cached;
import eu.jozoproductions.InstallManager;
import eu.jozoproductions.Main;

import java.util.ArrayList;
import java.util.List;

public class FirebaseManager {

    public static void initGameVersions(FetchCallback fc) {
        Thread thread = new Thread(() -> {
            try {
                Main.loadingPanel.showLoading();

                if (!Cached.gameVersions.isEmpty()) { //Redundant loading
                    Main.loadingPanel.hideLoading();
                    return;
                }

                String contentInfoText = InstallManager.readURLText(Main.CONTENT_INFO_URL);
                scanContentInfo(contentInfoText);

                Main.loadingPanel.hideLoading();
                fc.success();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public interface FetchCallback {
        public void success();
    }

    private static void scanContentInfo(String contentInfoText) throws Exception {
        //List<String> list = contentInfoText.lines().toList();
        List<String> list = new ArrayList<>();
        String[] lines = contentInfoText.split("\n");

        //String to list
        for (int i = 0; i < lines.length; i++) {
            list.add(lines[i]);
        }

        for (int i = 0; i < list.size(); i++) {
            String line = list.get(i);

            if (line.startsWith("GR")) { //Game release
                if (checkInstallerCode(Integer.parseInt(list.get(i+1)))) {
                    DS_GameVersion gv = new DS_GameVersion();
                    gv.download = list.get(i+2);
                    gv.ver = list.get(i+3);
                    gv.tag = list.get(i+4);
                    Cached.gameVersions.add(gv);
                }
            }
        }
    }

    private static boolean checkInstallerCode(int minimumCode) throws Exception {
        if (minimumCode > Main.VERSION_CODE) {
            Cached.incompatibleGameVersionsFound++;
            return false;
        } else {
            return true;
        }
    }
}
