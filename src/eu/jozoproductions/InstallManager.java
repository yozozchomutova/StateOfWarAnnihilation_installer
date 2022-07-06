package eu.jozoproductions;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class InstallManager {

    public static volatile float downloadedPercentage = 0f;
    public static volatile int downloadedContentInMb = 0;
    public static volatile int contentSizeInMb = 0;

    public static void copyURLToFile(URL url, File file) {
        try {
            InputStream input = url.openStream();
            if (file.exists()) {
                if (file.isDirectory())
                    throw new IOException("File '" + file + "' is a directory");

                if (!file.canWrite())
                    throw new IOException("File '" + file + "' cannot be written");
            } else {
                File parent = file.getParentFile();
                if ((parent != null) && (!parent.exists()) && (!parent.mkdirs())) {
                    throw new IOException("File '" + file + "' could not be created");
                }
            }

            FileOutputStream output = new FileOutputStream(file);

            byte[] buffer = new byte[4096];
            int n = 0;

            double byteId = 0;
            long inputSize = getFileSize(url);

            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);

                //Update progress
                byteId += n;
                downloadedPercentage = (float)(byteId / inputSize * 100f);
                downloadedContentInMb = (int)(byteId / 1048576);
                contentSizeInMb = (int)(inputSize / 1048576);
            }

            input.close();
            output.close();
        }
        catch(IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    public static long getFileSize(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            return conn.getContentLengthLong();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static void extract(ZipInputStream zip, File target) throws Exception {
        try {
            ZipEntry entry;

            while ((entry = zip.getNextEntry()) != null) {
                File file = new File(target, entry.getName());

                if (!file.toPath().normalize().startsWith(target.toPath())) {
                    throw new IOException("Bad zip entry");
                }

                if (entry.isDirectory()) {
                    file.mkdirs();
                    continue;
                }

                byte[] buffer = new byte[4096];
                file.getParentFile().mkdirs();
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
                int count;

                while ((count = zip.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }

                out.close();
            }
        } finally {
            zip.close();
        }
    }

    public static String readURLText(String urlS) throws Exception {
        String text = "";

        URL url = new URL(urlS);

        // read text returned by server
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        String line;
        while ((line = in.readLine()) != null) {
            text += line + "\n";
        }
        in.close();

        return text;
    }
}
