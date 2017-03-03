package com.example.mrye.newsgettogether;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mrye.newsgettogether.activity.OtherFunctionsActivity;
import com.example.mrye.newsgettogether.fragment.HomePageFragment;
import com.example.mrye.newsgettogether.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/*TODO 重构一下本程序，将API最低的版本设定为21，将为了兼容低版本的包全部去掉，用API21以上的控件*/


//TODO 返回主页按钮,将主界面也写成Fragment，menu上的日夜模式上设置
//ViewPager+AppLayout+Fragment
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private boolean flag = true;
    private MenuItem getMenuItem = null;

    FragmentManager fragmentManager;

    HomePageFragment homePageFragment;

    public TabLayout getTabLayout() {
        return tabLayout;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        fragmentManager = getSupportFragmentManager();//获取FragmentManager
        //每进行一次事务操作，都要创建一个事务FragmentTransaction
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //初始化默认显示HomePageFragment
        homePageFragment = new HomePageFragment();
        transaction.add(R.id.frame_content, homePageFragment);
//        transaction.addToBackStack(null);//将主页添加到回退栈中，最后总会返回主页再退出App
        transaction.commit();
        toolbar.setTitle(R.string.homePage);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);//setDrawerListener已弃用，已经被addDrawerListener代替了
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);


    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        //先判断
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {//Android的Menu状态动态设置方法
        getMenuItem = menu.findItem(R.id.day_or_night_mode);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.day_or_night_mode) {
            if (flag) { //日间模式
                //TODO 日夜间切换模式
                //这里不能用Android自带的那个日夜间切换模式的方法，因为会重启Activity
                getMenuItem.setTitle("夜间模式");
            } else {    //夜间模式
                getMenuItem.setTitle("日间模式");
            }
            flag = !flag;
        }
        if (id == R.id.action_settings) {
            ToastUtil.showToast(MainActivity.this, "设置");
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean onNavigationItemSelected(MenuItem item) {
        //注意： 放在MainActivity的Fragment都要是那种滑不到底端的，因为该Activity的布局底端超出了屏幕的可见区域
        int id = item.getItemId();
        //Fragment采用的是将Fragment全加到Fragment栈中，需要那个就隐藏其他的Fragment,显现这个
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (id) {
            case R.id.homePage:
                if (homePageFragment == null) {
                    homePageFragment = new HomePageFragment();
                    transaction.add(R.id.frame_content, homePageFragment);
                } else {
                    transaction.show(homePageFragment);
                }
                tabLayout.setVisibility(View.VISIBLE);
                toolbar.setTitle(R.string.homePage);
                break;
            case R.id.blogAndSearch:
                //TODO 改，将这几个Fragment用单独的Activity承载
                Intent intent = new Intent(this, OtherFunctionsActivity.class);
                intent.putExtra("key", 0);
                startActivity(intent);
                break;
            case R.id.lookPhoto:
                tabLayout.setVisibility(View.GONE);
                toolbar.setTitle(R.string.lookPhoto);
                ToastUtil.showToast(MainActivity.this, "这里还没有！");
                /*这里后面应该改为用和上一个一样的方式*/
                break;
        }
        transaction.commit();//提交事务
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void hideFragments(FragmentTransaction transaction) {
        //hide()和show()方法来隐藏和显示Fragment，这就不会让Fragment的生命周期重走一遍
        if (homePageFragment != null) {
            transaction.hide(homePageFragment);
        }
    }

    @Override
    protected void onPostResume() {//该方法是在onResume()方法后执行的
        super.onPostResume();
        if (homePageFragment.isHidden()) {//判断homePageFragment是否隐藏了
            //重新启动首页Fragment
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            if (homePageFragment == null) {
                homePageFragment = new HomePageFragment();
                transaction.add(R.id.frame_content, homePageFragment);
            } else {
                transaction.show(homePageFragment);
            }
            tabLayout.setVisibility(View.VISIBLE);
            toolbar.setTitle(R.string.homePage);
            transaction.commit();//提交事务
            //这里将首页MenuItem设置为true
            navigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
