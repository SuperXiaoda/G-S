package com.juda.gs.adapter;

import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.juda.gs.R;
import com.juda.gs.bean.KeyValueData;

import java.util.List;

/**
 * @Description: 多选按钮数据适配器
 * @author: CodingDa
 * @Date : 2020/5/7 17:12
 */
public class CheckBoxAdapter extends BaseQuickAdapter<KeyValueData, BaseViewHolder> {


    public CheckBoxAdapter(List<KeyValueData> data) {
        super(R.layout.item_check_layout, data);
    }

    // 设置选中项
    public void changeCheck(int position) {
        getData().get(position).setCheck(!getData().get(position).isCheck());
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, KeyValueData keyValueData) {
        CheckBox checkBox = baseViewHolder.getView(R.id.check);
        checkBox.setChecked(keyValueData.isCheck());
        baseViewHolder.setText(R.id.value_text, keyValueData.getName());
    }
}
