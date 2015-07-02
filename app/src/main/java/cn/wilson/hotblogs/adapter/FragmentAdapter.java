package cn.wilson.hotblogs.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by KingFlyer on 2015/6/29.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> mTitles;

    public FragmentAdapter(FragmentManager fm,List<Fragment> fragmentList,List<String> titles) {
        super(fm);
        this.fragmentList = fragmentList;
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if(fragmentList == null || fragmentList.size() == 0){
            return null;
        }else{
            return fragmentList.get(position);
        }
    }

    @Override
    public int getCount() {

        return fragmentList == null ? 0 : fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mTitles == null || mTitles.size() == 0){
            return null;
        }else{
            return mTitles.get(position);
        }
    }
}
