package eu.jozoproductions;

import eu.jozoproductions.firebase.DS_GameVersion;
import eu.jozoproductions.firebase.DS_InstallerVersion;

import java.util.ArrayList;

public class Cached {
    public static boolean servicesEnabled = true; //TODO Disable in future
    public static ArrayList<DS_GameVersion> gameVersions = new ArrayList<>();
    public static ArrayList<DS_InstallerVersion> insttalerVersions = new ArrayList<>();

    public static int incompatibleGameVersionsFound = 0;
    public static int incompatibleInstallerVersionsFound = 0;
}
