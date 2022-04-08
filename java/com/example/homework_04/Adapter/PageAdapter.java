package com.example.homework_04.Adapter;

import android.app.AppComponentFactory;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.example.homework_04.Fragment.ComPage_fm;
import com.example.homework_04.Fragment.HomePage_fm;
import com.example.homework_04.Fragment.LibPage_fm;

public class PageAdapter extends FragmentPagerAdapter {
    public final int COUNT = 3;
    private String[] titles = new String[]{"Home", "Library", "Community"};
    private Context context;

    public PageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        System.out.println("============");
        System.out.println(position);
        System.out.println("============");
        switch (position) {
            case 0:
                return HomePage_fm.newInstance(position + 1);
            case 1:
                return LibPage_fm.newInstance(position + 1);
            case 2:
                return ComPage_fm.newInstance(position + 1);
        }
        return null;
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

    }


}
