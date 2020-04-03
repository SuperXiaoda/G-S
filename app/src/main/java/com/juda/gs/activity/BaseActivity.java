package com.juda.gs.activity;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * @description: 主页基础Activity
 * @author: CodingDa
 * @date : 2019/5/9 10:39
 */
public abstract class BaseActivity extends AppCompatActivity {
    // 页面TAG
    final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());


        ButterKnife.bind(this);

        init();

        setListener();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        // overridePendingTransition(R.anim.activity_in, 0);
    }

    @Override
    protected void onResume() {
        // 检测程序是否回到前段
        super.onResume();

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);

    }

    @Override
    public void finish() {
        super.finish();
    }

    /**
     * Description: 获取布局Layout
     * author: CodingDa
     * Date: 2019-2-18 10:25:39
     */
    protected abstract int getContentView();

    /**
     * Description: 初始化
     * author: CodingDa
     * Date: 2019-2-18 10:25:03
     */
    protected abstract void init();

    /**
     * Description: 添加事件监听器
     * author: CodingDa
     * Date: 2019-2-18 10:25:08
     */
    protected abstract void setListener();
}
