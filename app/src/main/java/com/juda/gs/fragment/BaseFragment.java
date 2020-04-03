package com.juda.gs.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description: 基础Fragment
 * author: CodingHornet
 * Date: 2018/7/4 11:36
 */
public abstract class BaseFragment extends Fragment {

    // 页面TAG
    final String TAG = getClass().getSimpleName();
    // Activity
    protected Activity mActivity;
    // 跟布局
    protected View mRootView;
    // ButterKnife解绑
    private Unbinder mUnbinder;
    // 是否已经初始化
    private boolean mIsCreated;
    // 是否已经加载完
    private boolean mIsLoaded;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            if (getContentView() != 0) {
                mRootView = inflater.inflate(getContentView(), container, false); // set view

                mUnbinder = ButterKnife.bind(this, mRootView);

                init(mRootView);

                setListener();
            }
        } else {
            //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        }
        mIsCreated = true;
        lazyLoad();
        return mRootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyLoad();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null)
            mUnbinder.unbind();
    }

    /**
     * Description: 获取布局Layout
     * author: CodingHornet
     * Date: 2018/7/4 11:37
     */
    protected abstract int getContentView();

    /**
     * Description: 初始化
     * author: CodingHornet
     * Date: 2018/7/4 11:38
     */
    protected abstract void init(View rootView);

    /**
     * Description: 添加事件监听器
     * author: CodingHornet
     * Date: 2018/7/4 11:38
     */
    protected void setListener() {
    }

    /**
     * Description: 统计处理懒加载
     * author: CodingHornet
     * Date: 2018/7/4 11:38
     */
    private void lazyLoad() {
        if (getUserVisibleHint() && mIsCreated && !mIsLoaded) {
            mIsLoaded = true;   // 避免重复加载
            loadData();         // 异步初始化，在初始化后显示正常UI
        }
    }

    /**
     * Description: 加载数据
     * author: CodingHornet
     * Date: 2018/7/4 11:38
     */
    protected void loadData() {
    }

}
