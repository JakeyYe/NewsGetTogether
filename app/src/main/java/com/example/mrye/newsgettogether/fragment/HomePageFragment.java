package com.example.mrye.newsgettogether.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mrye.newsgettogether.MainActivity;
import com.example.mrye.newsgettogether.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Ye on 2016/12/11.
 */

public class HomePageFragment extends Fragment {
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    MyFragmentPagerAdapter adapter;
    MainActivity ma;
    TabLayout tabLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        ma=(MainActivity) getActivity();
        adapter=new MyFragmentPagerAdapter(getFragmentManager());
        tabLayout=ma.getTabLayout();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
        //这里好像不需要处理上面事情
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
////                ToastUtil.showToast(MainActivity.this,""+tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }


}
