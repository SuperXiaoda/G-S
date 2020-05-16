package com.juda.gs.activity;

import android.view.KeyEvent;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.juda.gs.R;
import com.juda.gs.fragment.FunctionFragment;
import com.juda.gs.fragment.GameFragment;
import com.juda.gs.fragment.MineFragment;
import com.juda.gs.util.ToastUtil;
import com.juda.gs.view.FixViewPager;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    // viewPager
    @BindView(R.id.fix_view_pager)
    FixViewPager mMainViewPager;
    // 底部导航栏
    @BindView(R.id.navigation)
    AHBottomNavigation mBottomNavigation;

    // 退出计时
    private long mExitTime = 0;

    // 存储各个界面
    private List<Fragment> mTabs = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        // 设置底部导航布局
        mBottomNavigation.setForceTitlesDisplay(true);
        mBottomNavigation.setAccentColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        mBottomNavigation.setInactiveColor(ContextCompat.getColor(getApplicationContext(), R.color.colorInactive));

        mBottomNavigation.addItem(new AHBottomNavigationItem(R.string.function_string, R.drawable.ic_functions_black_24dp));
        mBottomNavigation.addItem(new AHBottomNavigationItem(R.string.game_string, R.drawable.ic_games_black_24dp));
        mBottomNavigation.addItem(new AHBottomNavigationItem(R.string.mine_string, R.drawable.ic_perm_identity_black_24dp));
        // 功能
        FunctionFragment functionFragment = new FunctionFragment();
        mTabs.add(functionFragment);
        // 游戏
        GameFragment gameFragment = new GameFragment();
        mTabs.add(gameFragment);
        // 我的
        MineFragment mineFragment = new MineFragment();
        mTabs.add(mineFragment);
        // 界面适配器
        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mTabs.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mTabs.get(arg0);
            }
        };

        mMainViewPager.setOffscreenPageLimit(mTabs.size());
        mMainViewPager.setAdapter(mAdapter);

        mMainViewPager.setCurrentItem(0);
        mBottomNavigation.setCurrentItem(0);
    }

    @Override
    protected void setListener() {
        mBottomNavigation.setOnTabSelectedListener((position, wasSelected) -> {
            if (!wasSelected) {
                mMainViewPager.setCurrentItem(position, false); // 切换ViewPager
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtil.showShort(R.string.exit_prompt);
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
