package com.example.mrye.newsgettogether.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.EditText;

import com.example.mrye.newsgettogether.R;
import com.example.mrye.newsgettogether.widget.ProgressWebView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BlogAndSearchFragment extends BackHandledFragment {


    @BindView(R.id.webView)
    ProgressWebView mWebView;
    @BindView(R.id.fab_search)
    FloatingActionButton fAB;
    private AlertDialog.Builder builder = null;
    private View DialogView;
    private EditText editText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_blog_and_search, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebSettings settings=mWebView.getSettings();
        settings.setJavaScriptEnabled(true);//设置网页可以调用JS
        settings.setLoadWithOverviewMode(true);   //自适应屏幕
        mWebView.loadUrl("http://www.cnblogs.com/feng-ye/");
        fABinit();
    }

    private void fABinit() {
        fAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new AlterDialog
                if (builder == null) {//如果不存在就创建
                    builder = new AlertDialog.Builder(getActivity());
                    DialogBuilder();
                    builder.show();
                } else {
                    DialogBuilder();
                    builder.show();
                }
            }


        });
    }

    public void DialogBuilder() {//创建Dialog
        DialogView = View.inflate(getActivity(), R.layout.layout_dialogview, null);
        editText = (EditText) DialogView.findViewById(R.id.search);
        //EditText的事件监听器
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //输入前
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //这里判断输入操作
            }

            @Override
            public void afterTextChanged(Editable s) {
                //输入后
            }
        });
        builder.setTitle(R.string.search);
        builder.setView(DialogView);
        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mWebView.loadUrl(editText.getText().toString().trim());
            }
        });


    }

    @Override
    public boolean onBackPressed() {//该Fragment自己处理则返回true，否则返回false

        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return false;
    }
}

