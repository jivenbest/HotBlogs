package cn.wilson.hotblogs.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.wilson.hotblogs.R;
import cn.wilson.hotblogs.bean.blogBean;

/**
 * Created by KingFlyer on 2015/6/30.
 */
public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {
    private List<blogBean> mLists;

    public BlogAdapter(List<blogBean> mLists) {

        this.mLists = mLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_blogs_item,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        blogBean blog = mLists.get(position);
        viewHolder.title.setText(blog.getTitle());
        viewHolder.descp.setText(blog.getSummary());
    }

    @Override
    public int getItemCount() {

        return mLists==null ? 0 : mLists.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView descp;

        public ViewHolder(View view){
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            descp = (TextView) view.findViewById(R.id.descp);
        }
    }
}
