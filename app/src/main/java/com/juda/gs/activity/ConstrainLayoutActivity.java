package com.juda.gs.activity;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.juda.gs.R;
import com.youngfeng.snake.annotations.EnableDragToClose;

import butterknife.BindView;

/**
 * @Description: 约束性布局
 * @author: CodingDa
 * @Date : 2020/4/24 10:06
 */
@EnableDragToClose
public class ConstrainLayoutActivity extends BaseActivity implements View.OnClickListener {

    // 返回按钮
    @BindView(R.id.back)
    AppCompatImageButton mBack;
    // 标题
    @BindView(R.id.title)
    AppCompatTextView mTitle;

    @Override
    protected int getContentView() {
        return R.layout.activity_constrain_layout;
    }

    @Override
    protected void init() {
        mTitle.setText(R.string.constrain_layout);
    }

    @Override
    protected void setListener() {
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
