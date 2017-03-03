package com.example.mrye.newsgettogether.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.mrye.newsgettogether.R;
import com.example.mrye.newsgettogether.fragment.BackHandledFragment;
import com.example.mrye.newsgettogether.fragment.BlogAndSearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtherFunctionsActivity extends AppCompatActivity
        implements BackHandledFragment.BackHandlerInterface {


    BlogAndSearchFragment blogAndSearchFragment;
    FragmentManager fragmentManager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private BackHandledFragment selectedFragment;//当前显示的Fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_functions);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//不显示Label
        //设置ToolBar上的返回图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //设置状态栏颜色
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        fragmentManager = getSupportFragmentManager();//获取FragmentManager
        Intent intent = getIntent();
        int key = intent.getIntExtra("key", 0);
        switchFragment(key);

    }

    private void switchFragment(int key) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (key) {
            case 0:
                if (blogAndSearchFragment == null) {
                    blogAndSearchFragment = new BlogAndSearchFragment();
                    transaction.add(R.id.frame_content, blogAndSearchFragment);
                } else {
                    transaction.show(blogAndSearchFragment);
                }
                toolbar.setTitle(R.string.blogAndSearch);
                break;
            case 1:
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (blogAndSearchFragment != null) {
            transaction.hide(blogAndSearchFragment);
        }
    }

    @Override
    public void onBackPressed() {
        //先判断
        if (selectedFragment == null || !selectedFragment.onBackPressed()) {
                super.onBackPressed();
        }
    }

    @Override
    public void setSelectedFragment(BackHandledFragment backHandledFragment) {
        this.selectedFragment = backHandledFragment;
    }
}
