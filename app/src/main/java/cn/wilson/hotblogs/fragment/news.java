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
public class news extends BaseFragment {


    public news() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initView();
        //initData();
    }

}
