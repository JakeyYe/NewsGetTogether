package com.example.mrye.newsgettogether.easyrecyclerview;

import android.content.Context;
import android.view.ViewGroup;

import com.example.mrye.newsgettogether.bean.StoriesBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by Mr.Ye on 2016/12/4.
 */

public class ZhiHuAdapter extends RecyclerArrayAdapter<StoriesBean> {

    public ZhiHuAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ZhiHuViewHolder(parent);
    }
}
