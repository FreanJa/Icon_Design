package com.example.homework_04;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EventListener;

@SuppressLint("HandlerLeak")
public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private LinearLayout icon_bg;
    private TextView icon_text;
    private GradientDrawable drawable;
    private RelativeLayout canvas;
    private ImageView testView;
    private RelativeLayout progressLayout;
    private RelativeLayout generatedLayout;
    private RelativeLayout progressArea;
    private ProgressBar progressBar;
    private TextView progress;
    private TextView icon_name;
    private Button save_btn;
    private Button to_library;

    private Boolean isShow = Boolean.FALSE;
    private String title;
    private String text = "";
    private Bitmap generated_icon;
    private String currentTime;
    private int pStatus = 0;
    private final Handler handler = new Handler();
    private static final int COMPLETED = 0;
    private static final int REQUEST_CODE = 1;

    private static final String[] foods = {"餐盘", "冰淇淋", "蛋糕", "饭团", "汉堡包", "火锅", "鸡腿", "咖啡", "水壶1", "水壶2", "面包", "面条", "奶酪", "牛奶", "牛排", "啤酒", "披萨", "千层", "烧烤", "薯条", "糖果", "甜甜圈", "吐司", "雪糕", "奶茶", "纸杯蛋糕"};
    private static final int[] figure = {R.drawable.shiwu, R.drawable.bingqilin, R.drawable.dangao, R.drawable.fantuan, R.drawable.hanbaobao, R.drawable.huoguo, R.drawable.jitui, R.drawable.kafei, R.drawable.kaishui1, R.drawable.kaishui2, R.drawable.mianbao, R.drawable.mianbao, R.drawable.nailao, R.drawable.niunai, R.drawable.niupai, R.drawable.pijiu, R.drawable.pisa, R.drawable.qianceng, R.drawable.shaokao, R.drawable.shutiao, R.drawable.tangguo, R.drawable.tiantianquan, R.drawable.tusi, R.drawable.xuegao, R.drawable.yinliao, R.drawable.zhibeidangao};
    private ArrayList<String> new_icons = new ArrayList<>();


    private final Handler mHandler;
    {
        mHandler = new Handler() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == COMPLETED) {
                    currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd-HH_mm_ss"));

                    progressArea.setVisibility(View.INVISIBLE);
                    generatedLayout.setVisibility(View.VISIBLE);

                    generated_icon = getBitIconFromView(canvas, 1.4f);
                    testView.setImageBitmap(generated_icon);
                    title = text + currentTime.toString();
                    icon_name.setText(title);
                }
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkNeedPermissions();
        initViewAndListener();
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void checkNeedPermissions() {
        boolean permission_storage = Environment.isExternalStorageManager();
        int permission_write = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission_read = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (!permission_storage) {
            Toast.makeText(this, "Requesting file management permission", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION), 0);
        }
        else {
            Toast.makeText(this, "Already get management all files permission", Toast.LENGTH_SHORT).show();
        }

        if (permission_write!=PackageManager.PERMISSION_GRANTED
        || permission_read!=PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Requesting media permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        else {
            Toast.makeText(this, "Already get media permission", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initViewAndListener() {
        icon_bg = (LinearLayout)findViewById(R.id.icon_bg);
        imageView = (ImageView)findViewById(R.id.figure);

        icon_text = (TextView)findViewById(R.id.icon_text);
        CheckBox showText = (CheckBox) findViewById(R.id.showTitle);
        showText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isShow = b;
                icon_text.setText(isShow?text:"");
            }
        });

        EditText editText = (EditText) findViewById(R.id.icon_name);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                text = charSequence.toString();
                icon_text.setTextSize(text.length()==1?108:54);
                if (isShow) {
                    icon_text.setText(text);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        SeekBar seekBar = (SeekBar) findViewById(R.id.corner);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                 drawable = (GradientDrawable) icon_bg.getBackground();
                 drawable.setCornerRadius((float)i*200/100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Spinner figure_selector = (Spinner) findViewById(R.id.figure_selector);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, foods);
        figure_selector.setAdapter(adapter);
        figure_selector.setOnItemSelectedListener(new SpinnerSelectedListener());
        figure_selector.setVisibility(View.VISIBLE);

        RadioGroup bg_color = (RadioGroup) findViewById(R.id.background_color);
        bg_color.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @SuppressLint("ResourceAsColor")
//            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton bc_btn = (RadioButton) findViewById(i);
                drawable = (GradientDrawable) icon_bg.getBackground();
                drawable.setColor(bc_btn.getTextColors());

                System.out.println(bc_btn.getTextColors().getDefaultColor());

                icon_text.setTextColor(getResources().getColor(getTextColor(bc_btn.getTextColors().getDefaultColor())));

//                System.out.println(getTextColor(bc_btn.getTextColors().getDefaultColor()));
            }
        });

        RadioGroup fg_color = (RadioGroup) findViewById(R.id.figure_color);
        fg_color.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton fc_btn = (RadioButton) findViewById(i);
                imageView.setImageTintList(fc_btn.getTextColors());

            }
        });

        progressLayout = (RelativeLayout) findViewById(R.id.progress_layout);
        progressLayout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                pStatus = 0;
                progressLayout.setVisibility(View.INVISIBLE);
            }
        });

        canvas = (RelativeLayout) findViewById(R.id.canvas);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progress = (TextView) findViewById(R.id.progress_text);
        testView = (ImageView) findViewById(R.id.generated_icon);
        icon_name = (TextView) findViewById(R.id.pic_name);
        progressArea = (RelativeLayout) findViewById(R.id.progress_area);
        generatedLayout = (RelativeLayout) findViewById(R.id.generated_layout);

        Button generateIcon = (Button) findViewById(R.id.generate_btn);
        generateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressLayout.setVisibility(View.VISIBLE);
                progressArea.setVisibility(View.VISIBLE);
                generatedLayout.setVisibility(View.INVISIBLE);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new LoadThread().start();
                    }
                }).start();

            }

        });

        save_btn = (Button) findViewById(R.id.save_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( generated_icon != null) {
//                    new_icons.add(generated_icon);
                    new_icons.add(title);
                    BitmapUtil.saveBitmap2file(generated_icon, title);

                    pStatus = 0;
                    progressLayout.setVisibility(View.INVISIBLE);

                    Toast.makeText(MainActivity.this, "Save icon success", Toast.LENGTH_SHORT).show();

                }
            }
        });

        to_library = (Button) findViewById(R.id.toLib);
        to_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JumpToLibrary();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                    System.out.println("get permission success");
                }
                else {
                    Toast.makeText(this, "The READ&WRITE PERMISSION of the SD card cannot be obtained", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void JumpToLibrary() {
        String action = "com.homework_04.intent.library.ACTION_START";
        Intent intent = new Intent(action);
//        intent.putExtra("new_icons", (Parcelable) new_icons);
        intent.putStringArrayListExtra("new_icons", new_icons);
        startActivityForResult(intent, REQUEST_CODE);
        overridePendingTransition(R.anim.slide_from_bottom, R.anim.slide_to_top);
    }

    @SuppressLint("NonConstantResourceId")
    private int getTextColor(int bg_color) {
        int font_color = 0;
        switch (bg_color){
            case -2686918:
                font_color = R.color.font_red;
                break;
            case -1148927:
                font_color = R.color.font_orange;
                break;
            case -9984:
                font_color = R.color.font_yellow;
                break;
            case -12668048:
                font_color = R.color.font_green;
                break;
            case -16738855:
                font_color = R.color.font_blue;
                break;
            case -15376765:
                font_color = R.color.font_dark_blue;
                break;
            case -7780200:
                font_color = R.color.font_purple;
                break;
            case -1381654:
                font_color = R.color.font_light_gray;
                break;
            case -4144949:
                font_color = R.color.font_gray;
                break;
            case -13421773:
                font_color = R.color.font_black;
                break;
            default:
                font_color = R.color.font_light_gray;
                break;
        }
        return font_color;


    }

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            imageView.setImageResource(figure[i]);
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    public static Bitmap getBitIconFromView(View view, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitIcon;
        if (drawingCache != null) {
            bitIcon = Bitmap.createBitmap(drawingCache, 0, 0, drawingCache.getWidth(), drawingCache.getHeight(), matrix, true);
            view.setDrawingCacheEnabled(false);
        }
        else {
            bitIcon = null;
        }

        return bitIcon;
    }


    private class LoadThread extends Thread {
        @Override
        public void run() {
            while (pStatus <= 100) {
                handler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        progressBar.setProgress(pStatus);
                        progress.setText(pStatus + " %");
                    }
                });
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (pStatus == 100){ break; }
                pStatus++;
            }
            try {
                Thread.sleep(100);

                Message msg = new Message();
                msg.what = COMPLETED;
                mHandler.sendMessage(msg);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

