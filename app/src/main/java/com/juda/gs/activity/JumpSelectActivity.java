package com.juda.gs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.juda.gs.R;


/**
 * @Description: 跳转选择页面
 * @author: CodingDa
 * @Date : 2020/5/9 9:48
 */
public class JumpSelectActivity extends AppCompatActivity {

    // 文字
    private TextView mShowText;
    // 选择列表
    private TextView mValueText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_select);
        mShowText = findViewById(R.id.show_text);
        mValueText = findViewById(R.id.value_text);
        String formItemPosition = getIntent().getStringExtra("ItemPosition");
        String getDataKey = getIntent().getStringExtra("GetDataKey");
        mShowText.setText("这是通用表单的第" + formItemPosition + "条");
        mValueText.setText("获取数据" + getDataKey + "Key");
        mValueText.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("JumpSelectValue", mValueText.getText().toString().trim());
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
