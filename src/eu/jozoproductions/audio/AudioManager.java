package eu.jozoproductions.audio;

import eu.jozoproductions.Main;

import javax.sound.sampled.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class AudioManager {

    public static HashMap<String, Clip> clips = new HashMap<>();

    public static void init(Main main)  {
        try {
            loadSound(main, "click", "/click1.wav");
            loadSound(main, "highlight", "/highlight1.wav");
            loadSound(main, "task_done", "/taskDone.wav");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void loadSound(Main main, String key, String filePath) throws FileNotFoundException {
        try {
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            Clip clip;

            stream = AudioSystem.getAudioInputStream(main.getClass().getResource(filePath));
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);

            //Add to list
            clips.put(key, clip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playAudio(String key) {
        Clip clip = clips.get(key);
        clip.setMicrosecondPosition(0);
        clip.start();

        /*if (clip.is()) {
            clip.setMicrosecondPosition(0);
            System.out.println("REVERSE");
        } else {
            clip.start();
            System.out.println("S");
        }*/
    }
}
