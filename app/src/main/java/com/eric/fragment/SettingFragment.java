package com.eric.fragment;

import com.eric.mutiplayer.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View copyrightView = inflater.inflate(layout.copyright, container, false);

        return copyrightView;
    }


}

