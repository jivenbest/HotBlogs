package cn.wilson.hotblogs.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.wilson.hotblogs.R;
import cn.wilson.hotblogs.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class test2 extends BaseFragment {


    public test2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test2, container, false);
    }

}
