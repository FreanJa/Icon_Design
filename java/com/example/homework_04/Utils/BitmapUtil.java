package com.example.homework_04.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BitmapUtil {
    public static void saveBitmap2file(Bitmap bmp, String filename) {
        Bitmap.CompressFormat format = Bitmap.CompressFormat.PNG;
        int quality = 100;
        OutputStream stream = null;
        try {
            String path = Environment
                    .getExternalStorageDirectory().getPath()
                    + "/"
                    + filename
                    + ".png";
            stream = new FileOutputStream(path);
            System.out.println(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bmp.compress(format, quality, stream);
        try {
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Boolean deleteBitmapFromFile(String path) {
        try {
            File file = new File(path);
            return file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static Bitmap getBitmapFromFile(String path) {
        try {
            return BitmapFactory.decodeStream(new FileInputStream(path));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static Map<String, Bitmap> getAllBitmapFromFile() {
        Map<String, Bitmap> icon_list = new HashMap<>();
        Map<String, String> icon_path = new HashMap<>();

        File file = new File(Environment.getExternalStorageDirectory().getPath());
        System.out.println(file);
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.getName().endsWith(".png")) {
                icon_path.put(file1.getName(), file1.getAbsolutePath());
//                icon_path.add(file1.getAbsolutePath());
//                icon_title.add(file1.getName());
            }
        }

        System.out.println("icon_path" + icon_path);
        for (Map.Entry<String, String> entry: icon_path.entrySet()) {
            icon_list.put(entry.getKey(), getBitmapFromFile(entry.getValue()));
        }
        System.out.println("icon_list" + icon_list);


//        for (String path, title : icon_path) {
////            icon_list.add(getBitmapFromFile(path));
//            icon_list.put()
//        }

        return icon_list;
    }
}

