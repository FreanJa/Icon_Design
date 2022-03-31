package com.example.homework_04;

import android.graphics.Bitmap;

public class ItemBean {
    public Bitmap itemBitmap;
    public String itemTitle;
//    public String itemContext;


    public ItemBean(Bitmap itemBitmap, String itemTitle) {
//        , String itemContext
        this.itemBitmap = itemBitmap;
        this.itemTitle = itemTitle;
//        this.itemContext = itemContext;
    }
}
