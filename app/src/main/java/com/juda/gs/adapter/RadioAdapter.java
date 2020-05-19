package com.juda.gs.adapter;

import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.juda.gs.R;
import com.juda.gs.bean.KeyValueData;

import java.util.List;

/**
 * @Description: 单选按钮数据适配器
 * @author: CodingDa
 * @Date : 2020/5/7 17:12
 */
public class RadioAdapter extends BaseQuickAdapter<KeyValueData, BaseViewHolder> {


    public RadioAdapter(List<KeyValueData> data) {
        super(R.layout.item_radio_layout, data);
    }

    // 设置选中项
    public void changeCheck(int position) {
        for (int i = 0; i < getData().size(); i++) {
            if (position == i) {
                getData().get(i).setCheck(true);
            } else {
                getData().get(i).setCheck(false);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, KeyValueData keyValueData) {
        CheckBox checkBox = baseViewHolder.getView(R.id.check);
        checkBox.setChecked(keyValueData.isCheck());
        baseViewHolder.setText(R.id.value_text, keyValueData.getName());
    }
}
