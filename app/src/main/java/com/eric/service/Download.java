package com.eric.service;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Eric on 2016/3/15.
 */
public class Download {
    String path = "Music";
    OutputStream output = null;
    String TAG = "Download";

    public void download(String musicurl) {
        try {
            URL url = new URL(musicurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String SDCard = Environment.getExternalStorageDirectory() + "";
            String pathName = SDCard + "/" + path + "/"+"2.mp3";

            File file = new File(pathName);
            InputStream input = conn.getInputStream();
            if (file.exists()) {
                Log.i(TAG, "exist");
                return;
            } else {
                String dir = SDCard + "/" + path;
                new File(dir).mkdir();
                file.createNewFile();
                output = new FileOutputStream(file);

                byte[] buffer = new byte[4 * 1024];
                while (input.read(buffer) != -1) {
                    output.write(buffer);
                }
                output.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
