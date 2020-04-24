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

import org.w3c.dom.Text;

public class Fragment_Home extends QMUIFragment {

    @Nullable
    @Override
    public View onCreateView() {
        TextView textView = new TextView(getActivity());
        textView.setText("Home");
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
