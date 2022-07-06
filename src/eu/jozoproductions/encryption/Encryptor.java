package eu.jozoproductions.encryption;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Encryptor {

    private static Random r = new Random();

    public static void encrypt() {
        try {
            Path path = new File("src/fb_key.json").toPath();

            byte[] bytes = Files.readAllBytes(path);

            ArrayList<Byte> finalByteList = new ArrayList<>();

            encryptLayer(bytes, finalByteList); //Encrypt layer #1
            bytes = convertByteListToArray(finalByteList); //Convert
            encryptLayer(bytes, finalByteList); //Encrypt layer #2
            bytes = convertByteListToArray(finalByteList); //Convert

            //Write
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void encryptLayer(byte[] bytes, List<Byte> byteList) {
        for (byte b : bytes) {
            if (b % 2 == 0) {
                byteList.add(0, (byte) (b+26));
                byteList.add(1, (byte) r.nextInt());
            } else {
                byteList.add(0, (byte) (b-58));
            }
        }
    }

    private static byte[] convertByteListToArray(List<Byte> byteList) {
        byte[] bytes = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            bytes[i] = byteList.get(i);
        }

        byteList.clear(); //clear
        return bytes;
    }

    public static String decrypt(Class referenceClass) {
        try {
            String result = "";

            byte[] bytes = Files.readAllBytes(Paths.get(referenceClass.getResource("/fb_key.json").toURI()));

            ArrayList<Byte> finalByteList = new ArrayList<>();

            decryptLayer(bytes, finalByteList);
            bytes = convertByteListToArray(finalByteList);
            decryptLayer(bytes, finalByteList);

            //Convert
            for (int i = 0; i < finalByteList.size(); i++) {
                result = result + ((char)finalByteList.get(i).byteValue());
            }

            //Write
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static void decryptLayer(byte[] bytes, List<Byte> byteList) {
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];

            if (b % 2 == 0) {
                byteList.add(0, (byte) (b-26));
                i++;
            } else {
                byteList.add(0, (byte) (b+58));
            }
        }
    }
}
