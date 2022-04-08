package com.example.homework_04.Fragment;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.homework_04.Adapter.CardAdapter;
import com.example.homework_04.R;
import com.example.homework_04.model.CardModel;

import java.util.ArrayList;

public class ComPage_fm extends Fragment {
    private ActionBar actionBar;
    private ViewPager viewPager;

    private ArrayList<CardModel> cardModelArrayList;
    private CardAdapter cardAdapter;

    private static final String ARGS_PAGE = "args_page";
    private int mPage;

    public static ComPage_fm newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        ComPage_fm fm = new ComPage_fm();
        fm.setArguments(args);
        return fm;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mPage = getArguments().getInt(ARGS_PAGE);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_page_fm, container, false);
        initViewAndListener(view);
        return view;
    }

    private void initViewAndListener(View view) {
//        actionBar = getSupportActionBar();
        viewPager = view.findViewById(R.id.viewPager);
        loadCards();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                String title = cardModelArrayList.get(position).getTitle();
//                actionBar.setTitle(title);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void loadCards() {
        cardModelArrayList = new ArrayList<>();

        cardModelArrayList. add(new CardModel(
                "PIXEL VISTAS",
                "8 bit gif by 1041uuu load in tumblr",
                "2018/02/22",
                "https://pic.freanja.cn/images/2022/02/18/tumblr_fd1f4b85160a4cdd83c0a23d17ca6029_8b2a21fc_500.gif"
        ));
        cardModelArrayList. add(new CardModel(
                "ピクセル百景",
                "8 bit gif by 1041uuu load in tumblr",
                "2018/12/23",
                "https://pic.freanja.cn/images/2022/02/18/20181228x2.gif"
        ));
        cardModelArrayList. add(new CardModel(
                "寄",
                "test3",
                "2022/03/31",
                "https://pic.freanja.cn/images/2022/04/08/2022_03_31-14_23_53.png"
        ));
        cardModelArrayList. add(new CardModel(
                "甜",
                "test4",
                "2022/03/31",
                "https://pic.freanja.cn/images/2022/04/08/2022_03_31-14_23_10.png"
        ));


        cardAdapter = new CardAdapter(getContext(), cardModelArrayList);
        viewPager.setAdapter(cardAdapter);
        viewPager.setPadding(100, 0, 100, 0);

    }
}

/*
https://pic.freanja.cn/images/2022/04/08/2022_04_08-19_36_33.png
https://pic.freanja.cn/image/7cIe
https://pic.freanja.cn/image/7izL
https://pic.freanja.cn/image/7AWW
https://pic.freanja.cn/image/7SjE
https://pic.freanja.cn/image/7Utj
 */