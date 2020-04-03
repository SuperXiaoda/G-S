package com.juda.gs.activity;

import android.view.View;

import com.juda.gs.R;
import com.juda.gs.util.StringUtil;
import com.juda.gs.util.ToastUtil;
import com.juda.gs.view.DashboardView;
import com.youngfeng.snake.annotations.EnableDragToClose;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import butterknife.BindView;

/**
 * @description: 仪表盘页面
 * @author: CodingDa
 * @date : 2019/12/5 9:35
 */
@EnableDragToClose
public class DashboardActivity extends BaseActivity implements View.OnClickListener {

    // 返回按钮
    @BindView(R.id.back)
    AppCompatImageButton mBack;
    // 仪表盘控件
    @BindView(R.id.dashboard_view)
    DashboardView mDashboardView;
    // 最小值
    @BindView(R.id.min_value)
    AppCompatEditText mMinValue;
    // 最大值
    @BindView(R.id.max_value)
    AppCompatEditText mMaxValue;
    // 切分分数
    @BindView(R.id.number_of_copies)
    AppCompatEditText mNumberOfCopies;
    // 当前数据
    @BindView(R.id.real_time_value)
    AppCompatEditText mRealTimeValue;
    // 单位
    @BindView(R.id.unit_value)
    AppCompatEditText mUnitValue;
    // 确定按钮
    @BindView(R.id.sure)
    AppCompatButton mSure;

    @Override
    protected int getContentView() {
        return R.layout.activity_dashboard;
    }

    @Override
    protected void init() {
    }

    @Override
    protected void setListener() {
        mBack.setOnClickListener(this);
        mSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.sure:
                if (checkData()) {
                    mDashboardView.setMinValue(Integer.valueOf(mMinValue.getText().toString()));
                    mDashboardView.setMaxValue(Integer.valueOf(mMaxValue.getText().toString()));
                    mDashboardView.setSection(Integer.valueOf(mNumberOfCopies.getText().toString()));
                    mDashboardView.setUnit(mUnitValue.getText().toString());
                    mDashboardView.setRealTimeValue(Float.valueOf(StringUtil.isEmpty(mRealTimeValue.getText().toString()) ? mMinValue.getText().toString() : mRealTimeValue.getText().toString()),
                            StringUtil.isEmpty(mRealTimeValue.getText().toString()) ? mMinValue.getText().toString() : mRealTimeValue.getText().toString());
                    mDashboardView.setNewView();
                }
                break;
        }
    }

    // 检查数据
    private boolean checkData() {
        if (StringUtil.isEmpty(mMinValue.getText().toString())) {
            ToastUtil.showShort("请输入最小值");
            return false;
        } else if (StringUtil.isEmpty(mMaxValue.getText().toString())) {
            ToastUtil.showShort("请输入最大值");
            return false;
        } else if (Integer.valueOf(mMinValue.getText().toString()) >= Integer.valueOf(mMaxValue.getText().toString())) {
            ToastUtil.showShort("最小值不能大于等于最大值");
            return false;
        } else if (StringUtil.isEmpty(mNumberOfCopies.getText().toString())) {
            ToastUtil.showShort("请输入切分分数");
            return false;
        } else if (Integer.valueOf(mNumberOfCopies.getText().toString()) <= 0) {
            ToastUtil.showShort("切分分数不能小于等于0");
            return false;
        }
        return true;
    }
}
