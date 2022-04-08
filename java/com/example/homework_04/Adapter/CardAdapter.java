package com.example.homework_04.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.homework_04.R;
import com.example.homework_04.model.CardModel;

import java.util.ArrayList;

public class CardAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<CardModel> cardModelArrayList;

    private ImageView bannerIv;
    private TextView titleTv;
    private TextView descriptionTv;
    private TextView dateTv;

    public CardAdapter(Context context, ArrayList<CardModel> cardModelArrayList) {
        this.context = context;
        this.cardModelArrayList = cardModelArrayList;
    }


    @Override
    public int getCount() {
        return cardModelArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_fm, container, false);
        init(view, position);
        container.addView(view, position);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private void init(View view, int position) {
        // init
        bannerIv = view.findViewById(R.id.bannerIv);
        titleTv = view.findViewById(R.id.titleTv);
        descriptionTv = view.findViewById(R.id.descriptionTv);
        dateTv = view.findViewById(R.id.dateTv);

        // get data
        CardModel cardModel = cardModelArrayList.get(position);
        String title = cardModel.getTitle();
        String description = cardModel.getDescription();
        String date = cardModel.getDate();
        Bitmap image = cardModel.getBitmap();

        // set data
        bannerIv.setImageBitmap(image);
        titleTv.setText(title);
        descriptionTv.setText(description);
        dateTv.setText(date);

        // handle card click
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, title + "\n" + description + "\n" + date, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
