package com.example.homework_04.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homework_04.R;
import com.example.homework_04.Utils.BitmapUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class Dialog extends AppCompatActivity {

    private Button download_btn;
    private Button delete_btn;
    private TextView textView;
    private ImageView imageView;

    private String title;
    private Bitmap bitmap;
    private String path;
    private Boolean tag = false;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        checkNeedPermissions();

        initData();
        initViewAndListener();
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");

        path = String.valueOf(new File(Environment.getExternalStorageDirectory().getPath()) + "/" + title);
        bitmap = BitmapUtil.getBitmapFromFile(path);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void checkNeedPermissions() {
        int permission_write = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission_read = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int permission_storage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE);
        if (permission_write!= PackageManager.PERMISSION_GRANTED
                || permission_read!=PackageManager.PERMISSION_GRANTED
                || permission_storage!=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE}, 1);
        }
    }

    private void initViewAndListener() {
        textView = (TextView) findViewById(R.id.dialog_icon_name);
        textView.setText(title);

        imageView = (ImageView) findViewById(R.id.dialog_icon);

        imageView.setImageBitmap(bitmap);

        download_btn = (Button) findViewById(R.id.save2media);
        download_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SaveImage(title);
                saveImage(bitmap);

                Toast.makeText(Dialog.this, "Save this icon in you Album", Toast.LENGTH_SHORT).show();
            }
        });

        delete_btn = (Button) findViewById(R.id.trash);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BitmapUtil.deleteBitmapFromFile(path)){
                    tag = Boolean.TRUE;
                    onBackPressed();
                }
                else {
                    Toast.makeText(Dialog.this, "DELETE ERROR!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void SaveImage(String path) {
        File file = new File(path);

        MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String s, Uri uri) {
                        Log.i("ExternalStorage", "Scanned" + s + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }

    private void saveImage(Bitmap bitmap) {
        String root = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();

        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".png";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }

    private void backPreActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        setResult(tag?RESULT_OK:RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        backPreActivity();
    }
}