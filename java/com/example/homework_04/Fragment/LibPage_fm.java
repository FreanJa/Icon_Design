package com.example.homework_04.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.homework_04.Activities.MainActivity;
import com.example.homework_04.Adapter.BitmapAdapter;
import com.example.homework_04.R;
import com.example.homework_04.Utils.BitmapUtil;
import com.example.homework_04.model.ItemBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibPage_fm extends Fragment {

    private static final int REQUEST_CODE = 1;
    private ListView listView;

    private Map<String, Bitmap> icons = new HashMap<>();
    private List<ItemBean> itemBeanList;
    private BitmapAdapter bitmapAdapter;

    private static final String ARGS_PAGE = "args_page";
    private int mPage;

    public static LibPage_fm newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        LibPage_fm fm = new LibPage_fm();
        fm.setArguments(args);
        return fm;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        System.out.println("toggle hidden state");
        if (!hidden){
            reloadListView();
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mPage = getArguments().getInt(ARGS_PAGE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library_page_fm, container, false);
        initViewAndListener(view);
        return view;
    }

    private void initViewAndListener(View view) {
        icons = BitmapUtil.getAllBitmapFromFile();

        itemBeanList = new ArrayList<>();

        listView = view.findViewById(R.id.list_item_1);

        listView.invalidateViews();
        icons = BitmapUtil.getAllBitmapFromFile();
        for (Map.Entry<String, Bitmap> entry: icons.entrySet()) {
            itemBeanList.add(new ItemBean(entry.getValue(), entry.getKey()));
        }
        bitmapAdapter = new BitmapAdapter(getContext(), itemBeanList);
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

    public void reloadListView() {
        icons = BitmapUtil.getAllBitmapFromFile();
        itemBeanList.clear();
        for (Map.Entry<String, Bitmap> entry: icons.entrySet()) {
            itemBeanList.add(new ItemBean(entry.getValue(), entry.getKey()));
        }
        System.out.println("==== reload =====");
        System.out.println("itemBeanList" + itemBeanList);

        bitmapAdapter = new BitmapAdapter(getContext(), itemBeanList);
        System.out.println("bitmapAdapter" + bitmapAdapter);
        System.out.println("=========");

        listView.setAdapter(bitmapAdapter);
        listView.refreshDrawableState();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == -1) {
                    Toast.makeText(getContext(), "DELETE SUCCESS!", Toast.LENGTH_SHORT).show();
                    reloadListView();
                }
                break;
        }
    }

}
