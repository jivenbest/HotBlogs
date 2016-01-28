package cn.wilson.hotblogs.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

import cn.wilson.hotblogs.AppContext;
import cn.wilson.hotblogs.R;
import cn.wilson.hotblogs.adapter.BlogAdapter;
import cn.wilson.hotblogs.base.BaseFragment;
import cn.wilson.hotblogs.bean.blogBean;
import cn.wilson.hotblogs.dao.XMLParse;
import cn.wilson.hotblogs.dao.XMLVolleyRequest;

public class cnblogs extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private List<blogBean> mLists;
    private List<blogBean> newList;
    private BlogAdapter blogAdapter;
    private int pageIndex = 1;

    public cnblogs() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_cnblogs, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        GetListData();
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        GetListData();
    }

    private void initView(){
        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipeRefreshBlogs);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.rvBlogs);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        blogAdapter = new BlogAdapter(mLists);
        recyclerView.setAdapter(blogAdapter);

    }

    private void GetListData(){
        mLists = new ArrayList<blogBean>();
        swipeRefreshLayout.setRefreshing(true);
        String path =  "http://wcf.open.cnblogs.com/blog/sitehome/paged/" + pageIndex + "/18";

        XMLVolleyRequest request = new XMLVolleyRequest(Request.Method.GET, path, new Response.Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser result) {

                if(pageIndex == 1){ //inital or refresh
                    mLists.clear();
                    mLists.addAll(XMLParse.getInfos(result));
                }else{
                    mLists.addAll(XMLParse.getInfos(result));
                }

                if(mLists == null) {
                    Toast.makeText(getActivity(), "当前没有新内容", Toast.LENGTH_SHORT).show();
                }

                swipeRefreshLayout.setRefreshing(false);
                blogAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        AppContext.getInstance().addToRequestQueue(request, "blogs");
    }

    @Override
    public void onStop(){
        super.onStop();

        AppContext.getInstance().cancelPendingRequests("blogs");
    }
}
