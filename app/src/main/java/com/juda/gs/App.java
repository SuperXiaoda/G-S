package com.juda.gs;

import android.app.Application;

import com.youngfeng.snake.Snake;

import rxhttp.wrapper.param.RxHttp;

import androidx.core.content.ContextCompat;

/**
 * @description: 说明
 * @author: CodingDa
 * @date : 2019/12/3 10:33
 */
public class App extends Application {

    // App单例
    private static App mInstance;

    /**
     * 获取Application实例
     *
     * @return Application实例
     */
    public static App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        init();
    }

    // 初始化
    private void init() {
        initRxHttp();
        initSnake();
    }

    // 初始化网络请求
    private void initRxHttp() {
        RxHttp.init(null, true);
    }

    // 初始化侧滑退出
    private void initSnake() {
        Snake.init(this);
    }


}
