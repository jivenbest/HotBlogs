package cn.wilson.hotblogs;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import cn.wilson.hotblogs.adapter.FragmentAdapter;
import cn.wilson.hotblogs.base.BaseActivity;
import cn.wilson.hotblogs.fragment.blogs;
import cn.wilson.hotblogs.fragment.news;
import cn.wilson.hotblogs.fragment.recommend;


public class AppMain extends BaseActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);

        InitView();
    }

    private void InitView(){
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) this.findViewById(R.id.navigation_view);
        tabLayout = (TabLayout)this.findViewById(R.id.tab_layout);
        viewPager = (ViewPager)this.findViewById(R.id.view_pager);

        initToolbar();
        initTableLayout();

        navigationView.setNavigationItemSelectedListener(naviListener);
        //drawerLayout.openDrawer(navigationView);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("推荐");
    }

    private void initTableLayout(){

        List<String> titles = new ArrayList<>();
        titles.add("推荐");
        titles.add("博客");
        titles.add("新闻");
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(2)));

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new recommend());
        fragments.add(new blogs());
        fragments.add(new news());

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);

//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                Toast.makeText(AppMain.this,String.valueOf(tab.getPosition()) + " " + tab.getText(),Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });


    }

    private NavigationView.OnNavigationItemSelectedListener naviListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menu_info_details:
                    //mViewPager.setCurrentItem(0);
                    break;
                case R.id.menu_share:
                    //mViewPager.setCurrentItem(1);
                    break;
                case R.id.menu_agenda:
                    //mViewPager.setCurrentItem(2);
                    break;
            }
            drawerLayout.closeDrawer(navigationView);
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_settings);
        item.setVisible(false); //隐藏右上角的3个小点

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(navigationView);
                break;
            case R.id.action_settings:
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
