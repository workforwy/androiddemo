package com.ceshi.ha.fragment;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.arch.QMUIFragment;

public class Fragment_My extends QMUIFragment {

    @Override
    protected View onCreateView() {
        TextView textView = new TextView(getActivity());
        textView.setText("My");
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
