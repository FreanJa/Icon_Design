package com.example.homework_04;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private ListView listView;

    private Map<String, Bitmap> icons = new HashMap<>();
    private List<ItemBean> itemBeanList;
    private BitmapAdapter bitmapAdapter;


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkNeedPermissions();
        setContentView(R.layout.activity_library);
        initViewAndListener();
    }

    private void initViewAndListener() {
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        icons = BitmapUtil.getAllBitmapFromFile();
//        System.out.println(icons);

        itemBeanList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.list_item_1);

        listView.invalidateViews();
        icons = BitmapUtil.getAllBitmapFromFile();
        for (Map.Entry<String, Bitmap> entry: icons.entrySet()) {
            itemBeanList.add(new ItemBean(entry.getValue(), entry.getKey()));
        }
        bitmapAdapter = new BitmapAdapter(this, itemBeanList);
        bitmapAdapter.notifyDataSetChanged();

        listView.setAdapter(bitmapAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ItemBean itemBean = (ItemBean) adapterView.getItemAtPosition(i);
                String title = itemBean.itemTitle;

                String action = "com.homework_04.intent.dialog.ACTION_START";
                Intent intent = new Intent(action);
                intent.putExtra("title", title);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    private void reloadListView() {
        icons = BitmapUtil.getAllBitmapFromFile();
        itemBeanList.clear();
        for (Map.Entry<String, Bitmap> entry: icons.entrySet()) {
            itemBeanList.add(new ItemBean(entry.getValue(), entry.getKey()));
        }
        System.out.println("itemBeanList" + itemBeanList);

        bitmapAdapter = new BitmapAdapter(this, itemBeanList);
        System.out.println("bitmapAdapter" + bitmapAdapter);

        listView.setAdapter(bitmapAdapter);
        listView.refreshDrawableState();



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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(LibraryActivity.this, "DELETE SUCCESS!", Toast.LENGTH_SHORT).show();
                    reloadListView();
                }
                break;
        }
    }
}