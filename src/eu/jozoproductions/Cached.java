package eu.jozoproductions;

import eu.jozoproductions.firebase.DS_GameVersion;
import eu.jozoproductions.firebase.DS_InstallerVersion;

import java.util.ArrayList;
import java.util.HashMap;

public class Cached {
    public static HashMap<Integer, ArrayList<DS_GameVersion>> gameVersions = new HashMap<>();
    public static DS_InstallerVersion insttalerVersion = new DS_InstallerVersion();

    public static int incompatibleGameVersionsFound = 0;
    public static int incompatibleInstallerVersionsFound = 0;
}
