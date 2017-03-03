package com.example.mrye.newsgettogether.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Mr.Ye on 2016/11/20.
 */

public abstract class BackHandledFragment extends Fragment {
    //继承该类的子类Fragment是要监听返回键的，有些子类不需要监听就不需要继承该类了

    protected BackHandlerInterface backHandlerInterface;

    public abstract boolean onBackPressed();//继承该类的子类各自处理对back的消费逻辑

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof BackHandlerInterface)) {
            throw new ClassCastException("Hosting activity must implement BackHandlerInterface");
        }
        else{
            backHandlerInterface=(BackHandlerInterface)getActivity();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        //Mark this fragment as the selected Fragment,告诉FragmentActivity,当前Fragment在栈顶
        backHandlerInterface.setSelectedFragment(this);//该接口的作用是标记当前可见的Fragment
    }

    public interface BackHandlerInterface {
        //通过该方法标记当前Fragment作为被选择的Fragment,也就是当前显示的Fragment
        void setSelectedFragment(BackHandledFragment backHandledFragment);
    }
}
