package com.example.mrye.newsgettogether.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mrye.newsgettogether.R;


public class TestBlankFragment extends Fragment {
    private String msg;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        Bundle bundle=getArguments();
        this.msg=bundle.getString("msg");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_test_blank, container, false);
        TextView textView=(TextView)view.findViewById(R.id.tv);
        textView.setText(msg);
        return view;
    }






}
