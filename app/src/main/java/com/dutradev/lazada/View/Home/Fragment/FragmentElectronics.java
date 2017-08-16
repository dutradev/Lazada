package com.dutradev.lazada.View.Home.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dutradev.lazada.R;

/**
 * Created by LXers on 7/31/2017.
 */

public class FragmentElectronics extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_electronics,container,false);
        return view;
    }
}
