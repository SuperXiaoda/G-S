package com.juda.gs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * description：不可滑动ViewPager
 * author：CodingHornet
 * date：2015/10/26 0023 10:25
 */
public class FixViewPager extends ViewPager {

    public FixViewPager(Context context) {
        super(context);
    }

    public FixViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //拦截 TouchEvent     返回false 拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }

    //处理 TouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }


    //因为这个执行的顺序是  父布局先得到 action_down的事件
    /**
     * onInterceptTouchEvent(MotionEvent ev)方法，这个方法只有ViewGroup类有
     * 如LinearLayout，RelativeLayout等    可以包含子View的容器的
     *
     * 用来分发 TouchEvent
     * 此方法    返回true    就交给本 View的 onTouchEvent处理
     * 此方法    返回false   就交给本View的 onInterceptTouchEvent 处理
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //让父类不拦截触摸事件就可以了。
        this.getParent().requestDisallowInterceptTouchEvent(false);
        return super.dispatchTouchEvent(ev);
    }

}