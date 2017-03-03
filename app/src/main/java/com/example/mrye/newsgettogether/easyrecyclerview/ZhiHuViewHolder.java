package com.example.mrye.newsgettogether.easyrecyclerview;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mrye.newsgettogether.R;
import com.example.mrye.newsgettogether.bean.StoriesBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by Mr.Ye on 2016/12/4.
 */

public class ZhiHuViewHolder extends BaseViewHolder<StoriesBean> {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_icon)
    ImageView imgIcon;

    public ZhiHuViewHolder(ViewGroup parent) {
        super(parent, R.layout.zhihu_recycler_item);
        tvTitle=$(R.id.tv_title);
        imgIcon=$(R.id.img_icon);
    }

    @Override
    public void setData(StoriesBean data) {
        tvTitle.setText(data.getTitle());
        Glide.with(getContext())
                .load(data.getImages().get(0))
                .placeholder(R.drawable.ic_favorite)
                .centerCrop()
                .into(imgIcon);
    }
}
