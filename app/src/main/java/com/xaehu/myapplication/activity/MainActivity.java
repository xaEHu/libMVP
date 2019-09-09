package com.xaehu.myapplication.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.xaehu.myapplication.R;
import com.xaehu.myapplication.base.BaseActivity;
import com.xaehu.myapplication.fragment.HomeFragment;
import com.xaehu.myapplication.fragment.PersonFragment;
import com.xaehu.myapplication.fragment.SearchDetailFragment;
import com.xaehu.myapplication.fragment.SearchFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private ViewPager viewpager;
    private SlidingTabLayout tab;
    private String []title = {"首页","搜索","详细搜索","个人中心"};

    @Override
    public void initView(View v) {
        viewpager = findViewById(R.id.viewpager);
        tab = findViewById(R.id.tab);
        super.initView(v);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        setTitle(title[0]);
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new SearchFragment());
        fragmentList.add(new SearchDetailFragment());
        fragmentList.add(new PersonFragment());
        tab.setViewPager(viewpager,title,this, fragmentList);
//        viewpager.setOffscreenPageLimit(4);

    }

    @Override
    public void initListener() {
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                setTitle(title[i]);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
