package com.example.mrye.newsgettogether.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Ye on 2016/12/7.
 */

public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

    ZhiHuFragment zhiHuFragment;
    TestBlankFragment blankFragment;
    String msg1 = "这里是推荐，可是没什么好推荐的:(";
    String msg2 = "这里是网易新闻，暂时没有消息哟！";
    String msg3 = "这里是凤凰新闻，暂时没有消息哟！";
    String msg4 = "这里是搜狐新闻，暂时没有消息哟！";
    List<String> tabList = new ArrayList<>();

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        tabList.add("知乎日报");
        tabList.add("推荐");
        tabList.add("网易新闻");
        tabList.add("凤凰新闻");
        tabList.add("搜狐新闻");
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle;
        switch (position) {
            case 0:
                zhiHuFragment = new ZhiHuFragment();//ViewPager中的一个Fragment
                return zhiHuFragment;
            case 1:
                blankFragment = new TestBlankFragment();
                bundle = new Bundle();
                bundle.putString("msg", msg1);
                blankFragment.setArguments(bundle);
                return blankFragment;
            case 2:
                blankFragment = new TestBlankFragment();
                bundle = new Bundle();
                bundle.putString("msg", msg2);
                blankFragment.setArguments(bundle);
                return blankFragment;
            case 3:
                blankFragment = new TestBlankFragment();
                bundle = new Bundle();
                bundle.putString("msg", msg3);
                blankFragment.setArguments(bundle);
                return blankFragment;
            case 4:
                blankFragment = new TestBlankFragment();
                bundle = new Bundle();
                bundle.putString("msg", msg4);
                blankFragment.setArguments(bundle);
                return blankFragment;
            default:
                zhiHuFragment = new ZhiHuFragment();
                return zhiHuFragment;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
