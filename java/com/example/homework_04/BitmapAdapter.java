package com.example.homework_04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BitmapAdapter extends BaseAdapter {
    private List<ItemBean> itemBeanList;
    private LayoutInflater inflater;

    public BitmapAdapter(Context context, List<ItemBean> list) {
        itemBeanList = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itemBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item, null);
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.icon);
        TextView textView = (TextView) view.findViewById(R.id.list_icon_name);

        ItemBean bean = itemBeanList.get(i);

        imageView.setImageBitmap(bean.itemBitmap);
        textView.setText(bean.itemTitle);

        return view;
    }

    public List<ItemBean> getData() {
        return itemBeanList;
    }
}
