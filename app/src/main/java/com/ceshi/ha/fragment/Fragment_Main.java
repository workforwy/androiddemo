package com.ceshi.ha.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.qmuiteam.qmui.arch.QMUIFragment;

public class Fragment_Main extends QMUIFragment {

    @Override
    protected View onCreateView() {
        TextView textView = new TextView(getActivity());
        textView.setText("Main");
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
